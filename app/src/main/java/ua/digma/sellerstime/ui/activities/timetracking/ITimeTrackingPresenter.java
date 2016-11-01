package ua.digma.sellerstime.ui.activities.timetracking;


public interface ITimeTrackingPresenter {
    void logoutSeller();

    void getSellersTracking();

    void startTimer();

    void stopTimer();
    void cancelTimer();

    String getStringWorkedOut();
}
