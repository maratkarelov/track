package ua.digma.sellerstime.ui.simplecore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initializePresenter();

    protected abstract void unbindPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initializePresenter();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        unbindPresenter();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager().popBackStack();
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected void showDialogInfo(@NonNull final String message, final DialogInterface.OnClickListener listener) {
        showDialogInfo(message, listener, DialogInterface.BUTTON_POSITIVE);
    }

    protected void showDialogInfo(@NonNull final String message,  final DialogInterface.OnClickListener listener, final int button) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setMessage(message);
                switch (button){
                    case DialogInterface.BUTTON_POSITIVE:
                        builder.setPositiveButton("Ok", listener);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        builder.setNegativeButton("Ok", listener);
                        break;
                    default:
                        break;
                }
                builder.show();
            }
        });
    }

}
