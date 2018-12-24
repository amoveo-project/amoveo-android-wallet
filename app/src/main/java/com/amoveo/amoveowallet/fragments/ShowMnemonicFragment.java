package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.ShowMnemonicPresenter;
import com.amoveo.amoveowallet.toolbars.ShowMnemonicToolbar;
import com.amoveo.amoveowallet.view.IBaseView;

public class ShowMnemonicFragment extends BaseToolbarFragment<ShowMnemonicPresenter, IBaseView, ShowMnemonicToolbar> implements IBaseView {
    @BindView(R.id.mnemonic)
    protected TextView mMnemonicTextView;

    @BindView(R.id.continue_button)
    protected View mContinue;

    private String mMnemonic;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMnemonicTextView.setText(mMnemonic);

        mContinue.setOnClickListener(view1 -> mPresenter.onContinue());
    }

    @Override
    protected ShowMnemonicToolbar createToolbar(View view) {
        return new ShowMnemonicToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_show_mnemonic;
    }

    @Override
    protected ShowMnemonicPresenter createPresenter() {
        return new ShowMnemonicPresenter(mMnemonic);
    }

    public ShowMnemonicFragment setMnemonic(String mnemonic) {
        this.mMnemonic = mnemonic;

        return this;
    }

    public static ShowMnemonicFragment newInstance(String mnemonic) {
        return new ShowMnemonicFragment().setMnemonic(mnemonic);
    }
}