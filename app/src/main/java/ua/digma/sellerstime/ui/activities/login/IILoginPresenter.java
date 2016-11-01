package ua.digma.sellerstime.ui.activities.login;


public interface IILoginPresenter {
    void loginSeller(String inn);

    void syncDataWithServer();

    void getSellersTracking();

    void syncSellers();

}
