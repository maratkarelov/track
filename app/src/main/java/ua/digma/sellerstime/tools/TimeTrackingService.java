package ua.digma.sellerstime.tools;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ua.digma.sellerstime.ui.activities.timetracking.TimeTrackingPresenter;

public class TimeTrackingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        TimeTrackingPresenter presenter = new TimeTrackingPresenter(getApplicationContext());
        presenter.logoutSeller();
        //do something you want
        //stop service
        this.stopSelf();
    }
}
