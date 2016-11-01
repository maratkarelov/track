package ua.digma.sellerstime.storage.db.dao;


import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.storage.db.entity.SellerEntity;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TimeTrackingDao implements EntityDao<TimeTrackingEntity> {
    @Override
    public void createEntity(TimeTrackingEntity item, SQLiteDatabase database) {
        if(item != null) {
            cupboard().withDatabase(database).put(item);
        }

    }

    @Override
    public TimeTrackingEntity readEntity(TimeTrackingEntity item, SQLiteDatabase database) {
        return null;
    }

    @Override
    public TimeTrackingEntity readByParam(TimeTrackingEntity item, SQLiteDatabase database) {
        return null;
    }

    @Override
    public int updateEntity(TimeTrackingEntity item, SQLiteDatabase database) {
        return 0;
    }

    @Override
    public int deleteEntity(TimeTrackingEntity item, SQLiteDatabase database) {
        return 0;
    }

    @Override
    public List<TimeTrackingEntity> fetchAllEntities(SQLiteDatabase database) {
        List<TimeTrackingEntity> entityList = cupboard().withDatabase(database).query(TimeTrackingEntity.class).list();
        return entityList;
    }

    @Override
    public List<TimeTrackingEntity> readByParamList(TimeTrackingEntity item, SQLiteDatabase database) {
        List<TimeTrackingEntity> entityList = cupboard().withDatabase(database).query(TimeTrackingEntity.class).
                withSelection("dateLogin=?", new String[] {item.getDateLogin()}).list();
        return entityList;
    }

    @Override
    public void clearAllTableData(SQLiteDatabase database) {
        if(database != null) {
            String tableName = cupboard().getEntityConverter(TimeTrackingEntity.class).getTable();
            database.execSQL("delete from " + tableName + ";");
        }

    }
}
