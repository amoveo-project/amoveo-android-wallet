package com.amoveo.amoveowallet.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import com.amoveo.amoveowallet.R;

public class ProgressDialog extends AppCompatDialog {
    public ProgressDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? R.layout.dialog_progress : R.layout.dialog_progress_compat);
    }
}