package ua.digma.sellerstime.ui.simplecore;

import android.support.annotation.NonNull;

public interface IBaseView {

    void showDialog(@NonNull String message, int button);

    void showProgress();

    void dismissProgress();

}
