package com.amoveo.amoveowallet.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;

import static com.amoveo.amoveowallet.presenters.operations.DecryptOperations.decrypt;

public class EnterPasswordDialog extends AppCompatDialog {
    private final OperationListener mStateManager;
    private EditText mPassword;
    private View mHidePassword;

    private boolean isHidePassword;

    public EnterPasswordDialog(Context context, OperationListener stateManager) {
        super(context);
        this.mStateManager = stateManager;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_enter_password);

        mHidePassword = findViewById(R.id.hide_password);
        mHidePassword.setOnClickListener(view -> {
            mHidePassword.setBackgroundResource(isHidePassword ? R.drawable.ic_visibility_off_gray : R.drawable.ic_visibility_gray);
            mPassword.setTransformationMethod(isHidePassword ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
            mPassword.setSelection(mPassword.getText().length());
            isHidePassword = !isHidePassword;
        });

        mPassword = findViewById(R.id.password);
        View button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            dismiss();
            decrypt(mPassword.getText().toString(), mStateManager);
        });
    }
}