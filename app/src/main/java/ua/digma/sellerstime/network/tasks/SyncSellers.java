package ua.digma.sellerstime.network.tasks;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ua.digma.sellerstime.R;
import ua.digma.sellerstime.SellersTimeApp;
import ua.digma.sellerstime.network.Deserilization;
import ua.digma.sellerstime.network.SoapHelper;
import ua.digma.sellerstime.network.TrustManagerManipulator;
import ua.digma.sellerstime.network.UserApi;
import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.storage.db.dao.SellerDao;

import static ua.digma.sellerstime.network.UserApi.SOAP_ACTION;


public class SyncSellers extends AsyncTask<Void, Void, ArrayList<Seller>> {
    private Context context;

    public SyncSellers(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<Seller> doInBackground(Void... params) {
        SoapObject request = new SoapObject(UserApi.NAMESPACE, UserApi.SELLERS_STALLS);
        SoapSerializationEnvelope envelope = SoapHelper.getSoapSerializationEnvelope(request);
        HttpTransportSE ht = SoapHelper.getHttpTransportSE();
        SoapHelper.sendRequest(ht, envelope, null, null);
        ArrayList<Seller> arrayList = new ArrayList<>();
        try {
            arrayList = new Deserilization().SoapDeserializeArray(Seller.class, (SoapObject) (envelope.getResponse()));
            return arrayList;
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(ArrayList<Seller> sellers) {
        super.onPostExecute(sellers);
        if(sellers != null && sellers.size() > 0) {
            SQLiteDatabase sqLiteDatabase = SellersTimeApp.getInstance().getDatabaseManager().getWritableDatabase();
            SellerDao sellerDao = new SellerDao();
            sellerDao.clearAllTableData(sqLiteDatabase);
            for (Seller seller : sellers) {
                sellerDao.createEntity(seller, sqLiteDatabase);
            }
            Toast.makeText(context, String.format(context.getString(R.string.sellers_updated), sellers.size()), Toast.LENGTH_SHORT).show();
        }
    }
}
