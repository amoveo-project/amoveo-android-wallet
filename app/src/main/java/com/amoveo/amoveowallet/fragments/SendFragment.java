package com.amoveo.amoveowallet.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.dialogs.ProgressDialog;
import com.amoveo.amoveowallet.presenters.SendPresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.utils.SimpleTextWatcher;
import com.amoveo.amoveowallet.view.ISendView;

import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.amoveo.amoveowallet.utils.Utils.formatDouble;

public class SendFragment extends BaseToolbarFragment<SendPresenter, ISendView, BaseToolbar> implements ISendView {
    @BindView(R.id.connection_notify)
    View mConnectionView;

    @BindView(R.id.amount_layout)
    TextInputLayout mAmountLayout;

    @BindView(R.id.amount)
    EditText mAmount;

    @BindView(R.id.balance)
    TextView mBalance;

    @BindView(R.id.fee)
    TextView mFee;

    @BindView(R.id.address_layout)
    TextInputLayout mAddressLayout;

    @BindView(R.id.address)
    EditText mAddress;

    @BindView(R.id.clear_address)
    View mClearAddress;

    @BindView(R.id.qr_code)
    View mQRCode;

    @BindView(R.id.clear_amount)
    View mClearAmount;

    @BindView(R.id.send_max)
    View mSendMax;

    @BindView(R.id.send_button)
    View mButton;

    Dialog mProgress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mProgress = new ProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAddress.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.onAddressChanged(editable, mAddress.getSelectionStart(), mAddress.getSelectionEnd());
            }
        });
        mAmount.addTextChangedListener(mPresenter.getAmountWatcher());
        mQRCode.setOnClickListener(view12 -> mPresenter.scanQRCode());
        mClearAddress.setOnClickListener(v -> mPresenter.onClearAddress());
        mSendMax.setOnClickListener(v -> mPresenter.onSendMax());
        mClearAmount.setOnClickListener(v -> mPresenter.onClearAmount());

        mButton.setOnClickListener(view1 -> mPresenter.onSendButton());
    }

    @Override
    public void enableSendButton(boolean isEnable) {
        mButton.setEnabled(isEnable);
    }

    @Override
    public void setAmount(String amount) {
        mAmount.setText(amount);
        if (!isEmpty(amount)) {
            mAmount.setSelection(amount.length());
        }
    }

    @Override
    public void setFee(String fee) {
        mFee.setText(String.format(getString(R.string.fee_veo), formatDouble(fee)));
    }

    @Override
    public void showBalance(String balance) {
        mBalance.setText(formatDouble(balance));
    }

    @Override
    public void clearScreen() {
        mAddress.setText(null);
        mAmount.setText(null);
        mClearAddress.setVisibility(GONE);
        mClearAmount.setVisibility(GONE);
    }

    @Override
    public void enableClearAddressButton(boolean isEmpty) {
        mClearAddress.setVisibility(isEmpty ? GONE : VISIBLE);
    }

    @Override
    public void enableClearAmountButton(boolean isEmpty) {
        mClearAmount.setVisibility(isEmpty ? GONE : VISIBLE);
    }

    @Override
    public void setErrorAddressHint(String hint) {
        mAddressLayout.setError(hint);
    }

    @Override
    public void setErrorAmountHint(String hint) {
        mAmountLayout.setError(hint);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_send;
    }

    @Override
    protected BaseToolbar createToolbar(View view) {
        return new BaseToolbar(view, R.string.title_send, (RootActivity) getActivity());
    }

    @Override
    protected SendPresenter createPresenter() {
        return new SendPresenter();
    }

    public static SendFragment newInstance() {
        return new SendFragment();
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }

    @Override
    public void setAddress(String address, int selectionStart, int selectionEnd) {
        mAddress.requestFocus();
        mAddress.setText(address);

        if (-1 < selectionEnd) {
            mAddress.setSelection(Math.min(address.length(), selectionEnd));
        }
    }

    @Override
    public void notifyConnection(boolean isConnected) {
        mConnectionView.setVisibility(isConnected ? GONE : VISIBLE);
    }
}