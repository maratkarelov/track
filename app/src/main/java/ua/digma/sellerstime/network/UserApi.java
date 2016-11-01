package ua.digma.sellerstime.network;


public class UserApi {
    public static final String SOAP_ACTION = "http://www.digma.ua/Messages#MessageTK:";
    public static final String MAIN_REQUEST_URL = "https://order.digma.ua/MessageTK.1cws";
    public final static String NAMESPACE = "http://www.digma.ua/Messages";

    //methods
    public final static String SSW = "SSW";
    public final static String STORE_NAME = "StoreName";
    public final static String SELLERS_STALLS = "SellersStalls";

    //parameters
    public final static String KOD_SKLADA = "KodSklada";
    public final static String INFORMATION_LIST = "InformationList";
    public final static String TABLE = "Table";
    public final static String INN = "INN";
    public final static String DATA = "Data";
    public final static String HOURS = "hours";


}
