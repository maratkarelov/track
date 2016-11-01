package ua.digma.sellerstime.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.storage.db.entity.SellerEntity;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sellers_app.db";
    private static final int DATABASE_VERSION = 1;

    static {
        // register models here, please $)
        cupboard().register(SellerEntity.class);
        cupboard().register(TimeTrackingEntity.class);

    }

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
