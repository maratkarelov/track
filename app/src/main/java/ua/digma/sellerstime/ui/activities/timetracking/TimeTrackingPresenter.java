package ua.digma.sellerstime.ui.activities.timetracking;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ua.digma.sellerstime.SellersTimeApp;
import ua.digma.sellerstime.storage.db.dao.TimeTrackingDao;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.tools.TimeTrackingService;
import ua.digma.sellerstime.ui.simplecore.Presenter;

public class TimeTrackingPresenter extends Presenter<ITimeTrackingView> implements ITimeTrackingPresenter {
    private Context context;
    private TimeTrackingDao timeTrackingDao = new TimeTrackingDao();
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
    private Timer timer;

    public TimeTrackingPresenter(Context context) {
        this.context = context;
        context.startService(new Intent(context, TimeTrackingService.class));
    }

    @Override
    public void startTimer() {
        if(TextUtils.isEmpty(PreferenceUser.getDateStart(context))) {
            PreferenceUser.setDateStart(context, sdf.format(Calendar.getInstance().getTime()));
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(view() != null) {
                    view().updateWorkedOut();
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void stopTimer() {
        cancelTimer();
        writeWorkedOut();
        PreferenceUser.setDateStart(context, "");
        PreferenceUser.setDateFinish(context, "");
        getSellersTracking();

    }

    @Override
    public void cancelTimer() {
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void logoutSeller() {
        if(timer != null) {
            timer.cancel();
        }
        writeWorkedOut();
        PreferenceUser.setDateLogin(context, "");
        PreferenceUser.setDateStart(context, "");
        PreferenceUser.setDateFinish(context, "");
        PreferenceUser.setSellerInn(context, "");
        PreferenceUser.setSellerFio(context, "");
    }

    @Override
    public void getSellersTracking() {
        TimeTrackingEntity timeTrackingEntity = new TimeTrackingEntity();
        timeTrackingEntity.setDateLogin(PreferenceUser.getDateLogin(context));
        List<TimeTrackingEntity> list = timeTrackingDao.readByParamList(timeTrackingEntity,
                SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
        view().updateSellersTrackingList(list);
    }

    @Override
    public String getStringWorkedOut() {
        String strWorkedOut = "";
        long milliseconds = calculateWorkedOut();
        if(milliseconds > 0) {
            int seconds = (int) (milliseconds / 1000) % 60;
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
            strWorkedOut = String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
        return strWorkedOut;
    }

    private void writeWorkedOut() {
        if(!TextUtils.isEmpty(PreferenceUser.getDateStart(context))) {
            TimeTrackingEntity timeTrackingEntity = new TimeTrackingEntity();
            timeTrackingEntity.setWarehouseCode(PreferenceUser.getWarehouseCode(context));
            timeTrackingEntity.setWarehouseName(PreferenceUser.getWarehouseName(context));
            timeTrackingEntity.setFio(PreferenceUser.getSellerFio(context));
            timeTrackingEntity.setInn(PreferenceUser.getSellerInn(context));
            timeTrackingEntity.setDateLogin(PreferenceUser.getDateLogin(context));
            timeTrackingEntity.setDateStart(PreferenceUser.getDateStart(context));
            timeTrackingEntity.setDateFinish(sdf.format(Calendar.getInstance().getTime()));
            timeTrackingEntity.setWorkedOut(getStringWorkedOut());
            timeTrackingDao.createEntity(timeTrackingEntity, SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
        }
    }

    private long calculateWorkedOut() {
        long milliseconds = 0;
        if(!TextUtils.isEmpty(PreferenceUser.getDateStart(context))) {
            Date startDate;
            try {
                startDate = sdf.parse(PreferenceUser.getDateStart(context));
                Date date = new Date();
                milliseconds = date.getTime() - startDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return milliseconds;
    }

}
