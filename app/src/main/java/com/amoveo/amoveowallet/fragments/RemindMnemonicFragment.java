package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.RemindMnemonicPresenter;
import com.amoveo.amoveowallet.toolbars.RemindMnemonicToolbar;
import com.amoveo.amoveowallet.view.IBaseView;

public class RemindMnemonicFragment extends BaseToolbarFragment<RemindMnemonicPresenter, IBaseView, RemindMnemonicToolbar> implements IBaseView {
    @BindView(R.id.mnemonic)
    protected TextView mMnemonicTextView;

    private String mMnemonic;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMnemonicTextView.setText(mMnemonic);
    }

    @Override
    protected RemindMnemonicToolbar createToolbar(View view) {
        return new RemindMnemonicToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_remind_mnemonic;
    }

    @Override
    protected RemindMnemonicPresenter createPresenter() {
        return new RemindMnemonicPresenter();
    }

    public RemindMnemonicFragment setMnemonic(String mnemonic) {
        this.mMnemonic = mnemonic;

        return this;
    }

    public static RemindMnemonicFragment newInstance(String mnemonic) {
        return new RemindMnemonicFragment().setMnemonic(mnemonic);
    }
}