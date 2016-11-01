package ua.digma.sellerstime.ui.activities.settings;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import ua.digma.sellerstime.R;
import ua.digma.sellerstime.network.SoapHelper;
import ua.digma.sellerstime.network.UserApi;
import ua.digma.sellerstime.network.response.StoreNameResponse;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.ui.simplecore.Presenter;

public class SettingsPresenter extends Presenter<ISettingsView> implements ISettingsPresenter {
    private Context context;
    private String code;
    private SoapSerializationEnvelope envelope;

    @Override
    public void initWarehouse(Context context, String code) {
        this.context = context;
        this.code = code;
        view().showProgress();
        SoapObject request = new SoapObject(UserApi.NAMESPACE, UserApi.STORE_NAME);
        request.addProperty(UserApi.KOD_SKLADA, code);
        envelope = SoapHelper.getSoapSerializationEnvelope(request);
        final HttpTransportSE ht = SoapHelper.getHttpTransportSE();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapHelper.sendRequest(ht, envelope, handler, handlerError);
            }
        }).start();
    }

    Handler handler = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            view().dismissProgress();
            StoreNameResponse storeNameResponse = null;
            try {
                storeNameResponse = new StoreNameResponse((SoapObject) envelope.getResponse());
                if(Boolean.TRUE.toString().equals(storeNameResponse.status)) {
                    PreferenceUser.setWarehouseCode(context, code);
                    PreferenceUser.setWarehouseName(context, storeNameResponse.StoreName);
                    view().updateTitle();
                    view().showDialog(context.getResources().getString(R.string.market_identified), DialogInterface.BUTTON_POSITIVE);
                } else {
                    view().showDialog(storeNameResponse.messageError, DialogInterface.BUTTON_NEGATIVE);
                }
            } catch (SoapFault soapFault) {
                view().showDialog(soapFault.getMessage(), DialogInterface.BUTTON_NEGATIVE);
                soapFault.printStackTrace();
            }
        }
    };

    Handler handlerError = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            view().dismissProgress();
            view().showDialog(msg.getData().getString(Constants.INFO_MESSAGE), DialogInterface.BUTTON_NEGATIVE);
        }
    };

}
