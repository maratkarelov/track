package ua.digma.sellerstime.storage.db.dao;


import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.storage.db.entity.SellerEntity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class SellerDao implements EntityDao<Seller> {
    @Override
    public void createEntity(Seller item, SQLiteDatabase database) {
        if(item != null) {
            SellerEntity sellerEntity = new SellerEntity();
            sellerEntity.withSeller(item);
            cupboard().withDatabase(database).put(sellerEntity);
        }

    }

    @Override
    public Seller readEntity(Seller item, SQLiteDatabase database) {
        SellerEntity sellerEntity = cupboard().withDatabase(database).query(SellerEntity.class).
                withSelection("inn=?", new String[]{item.getINN()}).get();
        Seller seller = new Seller();
        if(sellerEntity == null) {
            return  null;
        } else {
            seller.withSellerEntity(sellerEntity);
        }
        return seller;
    }

    @Override
    public Seller readByParam(Seller item, SQLiteDatabase database) {
        return null;
    }

    @Override
    public int updateEntity(Seller item, SQLiteDatabase database) {
        return 0;
    }

    @Override
    public int deleteEntity(Seller item, SQLiteDatabase database) {
        return 0;
    }

    @Override
    public List<Seller> fetchAllEntities(SQLiteDatabase database) {
        List<Seller> result = new LinkedList<>();
        List<SellerEntity> entityList = cupboard().withDatabase(database).query(SellerEntity.class).list();
        if(entityList != null && entityList.size() > 0) {
            for (SellerEntity sellerEntity : entityList) {
                Seller seller = new Seller();
                seller.withSellerEntity(sellerEntity);
                result.add(seller);
            }
        }
        return result;
    }

    @Override
    public List<Seller> readByParamList(Seller item, SQLiteDatabase database) {
        return null;
    }

    @Override
    public void clearAllTableData(SQLiteDatabase database) {
        if(database != null) {
            String tableName = cupboard().getEntityConverter(SellerEntity.class).getTable();
            database.execSQL("delete from " + tableName + ";");
        }
    }
}
