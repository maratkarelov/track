package ua.digma.sellerstime.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class PreferenceUser {
    private static final String PREFERENCES_NAME = "prefs";

    private static final String WAREHOUSE_CODE = "WarehouseCode";
    private static final String WAREHOUSE_NAME = "WarehouseName";
    private static final String SELLER_INN = "SellerINN";
    private static final String SELLER_FIO = "SellerFIO";
    private static final String DATE_LOGIN = "DateLogin";
    private static final String DATE_START = "DateStart";
    private static final String DATE_FINISH = "DateFinish";

    private static ObscuredSharedPreferences encryptedPreferences;

    public static void setWarehouseCode(Context context, String id) {
        getEditor(context).putString(WAREHOUSE_CODE, id).commit();
    }

    public static String getWarehouseCode(Context context) {
        return getSharedPreferences(context).getString(WAREHOUSE_CODE, null);
    }
    public static void setWarehouseName(Context context, String id) {
        getEditor(context).putString(WAREHOUSE_NAME, id).commit();
    }

    public static String getWarehouseName(Context context) {
        return getSharedPreferences(context).getString(WAREHOUSE_NAME, null);
    }
    public static void setSellerInn(Context context, String id) {
        getEditor(context).putString(SELLER_INN, id).commit();
    }

    public static String getSellerInn(Context context) {
        return getSharedPreferences(context).getString(SELLER_INN, null);
    }
    public static void setSellerFio(Context context, String id) {
        getEditor(context).putString(SELLER_FIO, id).commit();
    }

    public static String getSellerFio(Context context) {
        return getSharedPreferences(context).getString(SELLER_FIO, null);
    }
    public static void setDateLogin(Context context, String id) {
        getEditor(context).putString(DATE_LOGIN, id).commit();
    }

    public static String getDateLogin(Context context) {
        return getSharedPreferences(context).getString(DATE_LOGIN, null);
    }
    public static void setDateStart(Context context, String id) {
        getEditor(context).putString(DATE_START, id).commit();
    }

    public static String getDateStart(Context context) {
        return getSharedPreferences(context).getString(DATE_START, null);
    }
    public static void setDateFinish(Context context, String id) {
        getEditor(context).putString(DATE_FINISH, id).commit();
    }

    public static String getDateFinish(Context context) {
        return getSharedPreferences(context).getString(DATE_FINISH, null);
    }


    public static void clearAppUserData(Context context) {
        getEditor(context).clear().commit();
    }

    // ******************
    private static SharedPreferences.Editor getEditor(Context context) {
        if (encryptedPreferences == null) {
            encryptedPreferences = new ObscuredSharedPreferences(context,
                    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE));
        }
        return encryptedPreferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (encryptedPreferences == null) {
            encryptedPreferences = new ObscuredSharedPreferences(context,
                    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE));
        }
        return encryptedPreferences;
    }
}
