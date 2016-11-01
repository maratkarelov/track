package ua.digma.sellerstime;

import android.app.Application;

import ua.digma.sellerstime.storage.db.DatabaseManager;


public class SellersTimeApp extends Application {
    private static SellersTimeApp sInstance;
    private DatabaseManager databaseManager;

    public static SellersTimeApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        databaseManager = new DatabaseManager(this);
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
