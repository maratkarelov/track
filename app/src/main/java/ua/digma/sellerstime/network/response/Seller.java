package ua.digma.sellerstime.network.response;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

import ua.digma.sellerstime.network.Deserilization;
import ua.digma.sellerstime.storage.db.entity.SellerEntity;

public class Seller implements Serializable{
    public String INN;
    public String FIO;

    public Seller(SoapObject object) {
        new Deserilization().SoapDeserilize(this, object);
    }

    public Seller() {
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @Override
    public String toString() {
        return INN + ", " + FIO;
    }

    public Seller withSellerEntity(SellerEntity seller) {
        this.INN = seller.getInn();
        this.FIO = seller.getFio();
        return this;
    }


}
