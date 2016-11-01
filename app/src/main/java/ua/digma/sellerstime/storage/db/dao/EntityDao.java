package ua.digma.sellerstime.storage.db.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface EntityDao<T> {
    void createEntity(T item, SQLiteDatabase database);
    T readEntity(T item, SQLiteDatabase database);
    T readByParam(T item, SQLiteDatabase database);
    int updateEntity(T item, SQLiteDatabase database);
    int deleteEntity(T item, SQLiteDatabase database);
    List<T> fetchAllEntities(SQLiteDatabase database);
    List<T> readByParamList(T item, SQLiteDatabase database);
    void clearAllTableData(SQLiteDatabase database);
}
