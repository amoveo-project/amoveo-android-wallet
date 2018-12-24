package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.RestoreMnemonicPresenter;
import com.amoveo.amoveowallet.view.IBaseView;

public class RestoreMnemonicFragment extends BaseFragment<RestoreMnemonicPresenter, IBaseView> implements IBaseView {
    @BindView(R.id.mnemonic)
    protected TextView mMnemonic;

    @BindView(R.id.continue_button)
    protected View mContinueButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContinueButton.setOnClickListener(view1 -> mPresenter.onRestore(mMnemonic));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_restore_mnemonic;
    }

    @Override
    protected RestoreMnemonicPresenter createPresenter() {
        return new RestoreMnemonicPresenter();
    }

    public static RestoreMnemonicFragment newInstance() {
        return new RestoreMnemonicFragment();
    }
}