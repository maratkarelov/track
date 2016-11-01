package ua.digma.sellerstime.ui.activities.settings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.digma.sellerstime.R;
import ua.digma.sellerstime.network.SoapHelper;
import ua.digma.sellerstime.network.TrustManagerManipulator;
import ua.digma.sellerstime.network.UserApi;
import ua.digma.sellerstime.network.response.StoreNameResponse;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.tools.StaticMethods;
import ua.digma.sellerstime.ui.simplecore.BaseActivity;
import ua.digma.sellerstime.ui.views.clearableedittext.ClearableEditText;

public class SettingsActivity extends BaseActivity implements DialogInterface.OnClickListener, ISettingsView {
    private ProgressDialog progressDialog;
    private SettingsPresenter presenter;

    @Bind(R.id.et_warehouse_code)
    ClearableEditText etWarehouseCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        if(!TextUtils.isEmpty(PreferenceUser.getWarehouseName(this))) {
            etWarehouseCode.setText(PreferenceUser.getWarehouseCode(this));
            getSupportActionBar().setTitle(PreferenceUser.getWarehouseName(this));
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new SettingsPresenter();
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.alpha_0, R.anim.alpha_100);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if(which == DialogInterface.BUTTON_POSITIVE) {
            onBackPressed();
        }
    }

    @Override
    public void showDialog(@NonNull String message, int button) {
        showDialogInfo(message, SettingsActivity.this, button);
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(SettingsActivity.this);
                progressDialog.show();
            }
        });
    }

    @Override
    public void dismissProgress() {
        if(progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void updateTitle() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setTitle(PreferenceUser.getWarehouseName(SettingsActivity.this));
            }
        });
    }

    @OnClick(R.id.btn_init_warehouse)
    protected void initWarehouse() {
        final String code = etWarehouseCode.getText().toString();
        if(!TextUtils.isEmpty(code)) {
            if(StaticMethods.checkInternetConnectionWithToast(this, true)) {
                presenter.initWarehouse(this, code);
            }
        }
    }

    @OnClick(R.id.btn_exit)
    protected void exit() {
        onBackPressed();
    }

}
