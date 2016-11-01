package ua.digma.sellerstime.tools;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import ua.digma.sellerstime.R;

public class StaticMethods {
    public static void sendMessageToHandler(Handler handler, String strMessage) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INFO_MESSAGE, strMessage);
        message.setData(bundle);
        handler.handleMessage(message);
    }

    public static boolean checkInternetConnectionWithToast(Context context, boolean showToast) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            if(showToast) {
                Toast.makeText(context, context.getString(R.string.no_network_connection_toast_error_msg), Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

}
