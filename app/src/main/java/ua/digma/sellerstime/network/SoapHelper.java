package ua.digma.sellerstime.network;


import android.os.Bundle;
import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.Proxy;

import ua.digma.sellerstime.network.response.StoreNameResponse;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.tools.StaticMethods;

public class SoapHelper {
    public static SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    public static HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, UserApi.MAIN_REQUEST_URL, 60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

    public static void sendRequest(HttpTransportSE ht, SoapSerializationEnvelope envelope, Handler handler, Handler handlerError) {
        try {
            TrustManagerManipulator.allowAllSSL();
            ht.call(UserApi.SOAP_ACTION, envelope);
            if(handler != null) {
                StaticMethods.sendMessageToHandler(handler, null);
            }
        } catch (IOException | XmlPullParserException e) {
            if(handlerError != null) {
                StaticMethods.sendMessageToHandler(handlerError, e.getMessage());
            }
        }
    }


}
