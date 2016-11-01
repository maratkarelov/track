package ua.digma.sellerstime.ui.activities.login;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ua.digma.sellerstime.R;
import ua.digma.sellerstime.SellersTimeApp;
import ua.digma.sellerstime.network.SoapHelper;
import ua.digma.sellerstime.network.UserApi;
import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.network.tasks.SyncSellers;
import ua.digma.sellerstime.storage.db.dao.SellerDao;
import ua.digma.sellerstime.storage.db.dao.TimeTrackingDao;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.tools.StaticMethods;
import ua.digma.sellerstime.ui.activities.timetracking.TimeTrackingActivity;
import ua.digma.sellerstime.ui.simplecore.BaseActivity;
import ua.digma.sellerstime.ui.simplecore.Presenter;

public class LoginPresenter extends Presenter<ILoginView> implements IILoginPresenter {
    private Context context;
    private TimeTrackingDao timeTrackingDao = new TimeTrackingDao();
    private SellerDao sellerDao = new SellerDao();

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void loginSeller(String inn) {
        if(!TextUtils.isEmpty(inn)) {
            Seller seller = new Seller();
            seller.setINN(inn);
            seller = sellerDao.readEntity(seller, SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
            if(seller != null) {
                PreferenceUser.setSellerInn(context, inn);
                PreferenceUser.setSellerFio(context, seller.getFIO());
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
                PreferenceUser.setDateLogin(context, sdf.format(Calendar.getInstance().getTime()));
                PreferenceUser.setDateStart(context, sdf.format(Calendar.getInstance().getTime()));
                ((BaseActivity) view()).startActivity(new Intent(context, TimeTrackingActivity.class));
            } else {
                view().showDialog(context.getString(R.string.user_not_found), DialogInterface.BUTTON_POSITIVE);
            }
        }
    }

    @Override
    public void syncDataWithServer() {
        List<TimeTrackingEntity> timeTrackingList = timeTrackingDao.fetchAllEntities(SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
        if(timeTrackingList.size() > 0) {
            if(StaticMethods.checkInternetConnectionWithToast(context, true)) {
                view().showProgress();
                SoapObject request = new SoapObject(UserApi.NAMESPACE, UserApi.SSW);
                SoapObject informationList = new SoapObject(UserApi.NAMESPACE, UserApi.INFORMATION_LIST);
                request.addProperty(UserApi.INFORMATION_LIST, informationList);
                for (TimeTrackingEntity timeTracking : timeTrackingList) {
                    if(!TextUtils.isEmpty(timeTracking.getDateStart())) {
                        SoapObject table = new SoapObject(UserApi.NAMESPACE, UserApi.TABLE);
                        informationList.addProperty(UserApi.TABLE, table);
                        table.addProperty(UserApi.KOD_SKLADA, timeTracking.getWarehouseCode());
                        table.addProperty(UserApi.INN, timeTracking.getInn());
                        table.addProperty(UserApi.DATA, timeTracking.getDateStart().substring(0, 10));
                        table.addProperty(UserApi.HOURS, timeTracking.getWorkedOut());
                    }
                }
                final SoapSerializationEnvelope envelope = SoapHelper.getSoapSerializationEnvelope(request);
                final HttpTransportSE ht = SoapHelper.getHttpTransportSE();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SoapHelper.sendRequest(ht, envelope, handler, handlerError);
                    }
                }).start();
            }
        }
    }

    @Override
    public void syncSellers() {
        if(StaticMethods.checkInternetConnectionWithToast(context, false)) {
            SyncSellers syncSellers = new SyncSellers(context);
            syncSellers.execute();
        }
    }

    @Override
    public void getSellersTracking() {
        List<TimeTrackingEntity> list = timeTrackingDao.fetchAllEntities(SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
        view().updateSellersTrackingList(list);
    }

    Handler handler = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            view().dismissProgress();
            timeTrackingDao.clearAllTableData(SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase());
            view().updateSellersTrackingList(new ArrayList<TimeTrackingEntity>());
            view().showDialog(context.getString(R.string.sync_success), DialogInterface.BUTTON_POSITIVE);
        }
    };

    Handler handlerError = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            view().dismissProgress();
            view().showDialog(msg.getData().getString(Constants.INFO_MESSAGE), DialogInterface.BUTTON_POSITIVE);
        }
    };


}
