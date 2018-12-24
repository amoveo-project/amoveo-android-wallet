package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.CreateWalletPresenter;
import com.amoveo.amoveowallet.toolbars.CreateWalletToolbar;
import com.amoveo.amoveowallet.view.ICreateWalletView;

import java.math.BigInteger;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class CreateWalletFragment extends BaseToolbarFragment<CreateWalletPresenter, ICreateWalletView, CreateWalletToolbar> implements ICreateWalletView {
    @BindView(R.id.password_layout)
    protected TextInputLayout mPasswordLayout;

    @BindView(R.id.password)
    protected EditText mPassword;

    @BindView(R.id.notification)
    protected TextView mNotification;

    @BindView(R.id.confirm)
    protected EditText mConfirm;

    @BindView(R.id.security)
    protected View mSecurity;

    @BindView(R.id.skip_password_button)
    protected View mSkip;

    @BindView(R.id.create_wallet_button)
    protected View mCreateWallet;

    private String mMnemonic;
    private float mWidth;
    private String mMnemonicPassword;
    private BigInteger mPrivateKey;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth() - 2 * getResources().getDimension(R.dimen.size63);

        mPassword.addTextChangedListener(mPresenter.getPasswordWatcher(this));
        mConfirm.addTextChangedListener(mPresenter.getConfirmWatcher());
        mNotification.setText("");

        WALLET.setEmptyPassword(false);

        mCreateWallet.setOnClickListener(view1 -> mPresenter.onCreateWallet(mPassword, mConfirm, mPrivateKey, mMnemonic, mMnemonicPassword));
        mSkip.setOnClickListener(view1 -> {
            mPassword.setText(null);
            mConfirm.setText(null);
            WALLET.setEmptyPassword(true);
            mPresenter.onCreateWallet(mPassword, mConfirm, mPrivateKey, mMnemonic, mMnemonicPassword);
        });
    }

    public void setSecurity(double security) {
        if (0.01 > security) {
            mSecurity.setBackgroundResource(R.color.transparent);
            mCreateWallet.setEnabled(false);
            mNotification.setText(R.string.password_weak);
        } else if (0.4 > security) {
            mSecurity.setBackgroundResource(R.color.red);
            mCreateWallet.setEnabled(false);
            mNotification.setText(R.string.password_weak);
        } else if (0.7 > security) {
            mSecurity.setBackgroundResource(R.color.yellow);
            mNotification.setText(R.string.password_weak);
        } else {
            mSecurity.setBackgroundResource(R.color.green);
            mNotification.setText(null);
        }

        checkButton();

        ViewGroup.LayoutParams layoutParams = mSecurity.getLayoutParams();
        layoutParams.width = (int) (mWidth * security);
        mSecurity.setLayoutParams(layoutParams);
    }

    public void checkButton() {
        Editable password = mPassword.getText();
        Editable confirm = mConfirm.getText();
        mCreateWallet.setEnabled(String.valueOf(password).equals(String.valueOf(confirm)));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_create_wallet;
    }

    @Override
    protected CreateWalletToolbar createToolbar(View view) {
        return new CreateWalletToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected CreateWalletPresenter createPresenter() {
        return new CreateWalletPresenter();
    }

    private CreateWalletFragment setMnemonicAndPassword(String mnemonic) {
        this.mMnemonic = mnemonic;
        return this;
    }

    public static CreateWalletFragment newInstance(BigInteger privateKey) {
        return new CreateWalletFragment().setPrivateKey(privateKey);
    }

    private CreateWalletFragment setPrivateKey(BigInteger privateKey) {
        mPrivateKey = privateKey;
        return this;
    }

    public static CreateWalletFragment newInstance(String mnemonic) {
        return new CreateWalletFragment().setMnemonicAndPassword(mnemonic);
    }
}
