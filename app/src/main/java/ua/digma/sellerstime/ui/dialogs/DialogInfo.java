package ua.digma.sellerstime.ui.dialogs;


import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.digma.sellerstime.R;
import ua.digma.sellerstime.tools.Constants;

public class DialogInfo extends DialogFragment {
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.dialog_info, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, parent);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            String text = mBundle.getString(Constants.INFO_MESSAGE);
            tvContent.setText(text);
        }
        return parent;
    }
}
