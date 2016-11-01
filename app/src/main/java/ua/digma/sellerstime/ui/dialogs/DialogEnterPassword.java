package ua.digma.sellerstime.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.digma.sellerstime.R;
import ua.digma.sellerstime.ui.activities.login.LoginActivity;
import ua.digma.sellerstime.ui.views.clearableedittext.ClearableEditText;

public class DialogEnterPassword extends DialogFragment {
    @Bind(R.id.et_password)
    ClearableEditText etPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View parent = inflater.inflate(R.layout.dialog_enter_password, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, parent);
        return parent;
    }

    @OnClick(R.id.btn_cancel)
    protected void pressCancel() {
        ((LoginActivity) getActivity()).onCancel();
        dismiss();
    }

    @OnClick(R.id.btn_ok)
    protected void pressOk() {
        String password = etPassword.getText().toString();
        if(TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.password_is_empty));
        } else {
            ((LoginActivity) getActivity()).onEnteredPassword(password);
            dismiss();
        }
    }
}
