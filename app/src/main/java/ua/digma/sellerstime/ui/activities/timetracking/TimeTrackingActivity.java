package ua.digma.sellerstime.ui.activities.timetracking;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import ua.digma.sellerstime.R;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;
import ua.digma.sellerstime.storage.preferences.PreferenceUser;
import ua.digma.sellerstime.tools.TimeTrackingService;
import ua.digma.sellerstime.ui.adapters.WorkedOutAdapter;
import ua.digma.sellerstime.ui.simplecore.BaseActivity;

public class TimeTrackingActivity extends BaseActivity implements ITimeTrackingView {
    private TimeTrackingPresenter presenter;
    private WorkedOutAdapter adapter;

    @Bind(R.id.tv_fio)
    TextView tvFio;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_worked_out)
    TextView tvWorkedOut;
    @Bind(R.id.btn_start_stop)
    Button btnStartStop;
    @Bind(R.id.lv_worked_time)
    ListView lvWorkedTime;
    @Bind(R.id.l_worked_time)
    LinearLayout lWorkedTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_timetracking;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle(PreferenceUser.getWarehouseName(this));
        tvFio.setText(PreferenceUser.getSellerFio(this));
        if(TextUtils.isEmpty(PreferenceUser.getDateStart(this))) {
            resetTimerViews();
        } else {
            initTimerViews();
            btnStartStop.setPressed(true);
        }
        adapter = new WorkedOutAdapter(this);
        lvWorkedTime.setAdapter(adapter);
    }

    @Override
    protected void initializePresenter() {
        presenter = new TimeTrackingPresenter(this);
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferenceUser.getDateStart(this))) {
            resetTimerViews();
        } else {
            presenter.startTimer();
            initTimerViews();
        }
        presenter.getSellersTracking();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, TimeTrackingService.class));
    }

    @Override
    public void onBackPressed() {
        presenter.logoutSeller();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
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
    public void updateWorkedOut() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWorkedOut.setText(presenter.getStringWorkedOut());
            }
        });
    }

    @OnClick(R.id.btn_start_stop)
    protected void handleStartStop() {
        if(TextUtils.isEmpty(PreferenceUser.getDateStart(this))) {
            //pressed START
            presenter.startTimer();
            initTimerViews();
        } else {
            //pressed STOP
            presenter.stopTimer();
            resetTimerViews();
        }
    }

    private void initTimerViews() {
        tvStart.setText(PreferenceUser.getDateStart(this));
        btnStartStop.setText(getResources().getString(R.string.stop));
    }

    private void resetTimerViews() {
        tvStart.setText("");
        tvWorkedOut.setText("");
        btnStartStop.setText(getResources().getString(R.string.start));
    }

    @OnClick(R.id.btn_sign_out)
    protected void handleSignOut() {
        onBackPressed();
    }

}
