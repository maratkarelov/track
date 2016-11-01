package ua.digma.sellerstime.ui.activities.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import ua.digma.sellerstime.R;
import ua.digma.sellerstime.SellersTimeApp;
import ua.digma.sellerstime.network.response.Seller;
import ua.digma.sellerstime.storage.db.dao.SellerDao;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.Constants;
import ua.digma.sellerstime.tools.StaticMethods;
import ua.digma.sellerstime.ui.activities.settings.SettingsActivity;
import ua.digma.sellerstime.ui.activities.timetracking.TimeTrackingActivity;
import ua.digma.sellerstime.ui.activities.timetracking.TimeTrackingPresenter;
import ua.digma.sellerstime.ui.adapters.WorkedOutAdapter;
import ua.digma.sellerstime.ui.dialogs.DialogEnterPassword;
import ua.digma.sellerstime.ui.simplecore.BaseActivity;
import ua.digma.sellerstime.ui.views.clearableedittext.ClearableEditText;


public class LoginActivity extends BaseActivity implements ILoginView, DialogInterface.OnClickListener {
    private static final String PASSWORD = "1";
    private WorkedOutAdapter adapter;
    private ProgressDialog progressDialog;
    private LoginPresenter presenter;

    @Bind(R.id.et_inn)
    ClearableEditText etInn;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.btn_sync)
    Button btnSync;
    @Bind(R.id.lv_worked_time)
    ListView lvWorkedTime;
    @Bind(R.id.l_worked_time)
    LinearLayout lWorkedTime;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_user;
    }

    @Override
    protected void initView() {
        adapter = new WorkedOutAdapter(this);
        lvWorkedTime.setAdapter(adapter);
    }

    @Override
    protected void initializePresenter() {
        presenter = new LoginPresenter(this);
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferenceUser.getSellerInn(this))) {
            if(TextUtils.isEmpty(PreferenceUser.getWarehouseName(this))) {
                getSupportActionBar().setTitle(getResources().getString(R.string.market_undefined));
                etInn.setEnabled(false);
                btnStart.setEnabled(false);
                btnSync.setEnabled(false);
            } else {
                getSupportActionBar().setTitle(PreferenceUser.getWarehouseName(this));
                etInn.setText("2715820382");
                etInn.setEnabled(true);
                btnStart.setEnabled(true);
                btnSync.setEnabled(true);
            }
            presenter.getSellersTracking();
            presenter.syncSellers();
        } else {
            startActivity(new Intent(this, TimeTrackingActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogEnterPassword dialogEnterPassword = new DialogEnterPassword();
        dialogEnterPassword.show(getSupportFragmentManager(), this.getClass().getSimpleName());
        showKeyboard();
        return true;
    }

    @Override
    public void updateSellersTrackingList(final List<TimeTrackingEntity> timeTrackingList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(timeTrackingList.size() > 0) {
                    lWorkedTime.setVisibility(View.VISIBLE);
                    adapter.setTimeTrackingList(timeTrackingList);
                    adapter.notifyDataSetChanged();
                } else {
                    lWorkedTime.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onEnteredPassword(String password) {
        if(PASSWORD.equals(password)) {
            startActivity(new Intent(this, SettingsActivity.class));
            hideKeyboard();
            overridePendingTransition(R.anim.slide_enter_b_t, R.anim.slide_exit_b_t);
        }
    }

    @Override
    public void onCancel() {
        hideKeyboard();
    }

    private void showKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }

    @Override
    public void showDialog(@NonNull String message, int button) {
        showDialogInfo(message, LoginActivity.this);
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(LoginActivity.this);
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

    @OnClick(R.id.btn_start)
    protected void startWork() {
        presenter.loginSeller(etInn.getText().toString());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @OnClick(R.id.btn_sync)
    protected void syncDataWithServer() {
        presenter.syncDataWithServer();
    }


}

