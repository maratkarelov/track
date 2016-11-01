package ua.digma.sellerstime.network.response;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

import ua.digma.sellerstime.network.Deserilization;

public class StoreNameResponse implements Serializable{
    public String status;
    public String messageError;
    public String StoreName;

    public StoreNameResponse(SoapObject object) {
        new Deserilization().SoapDeserilize(this, object);
    }
}
