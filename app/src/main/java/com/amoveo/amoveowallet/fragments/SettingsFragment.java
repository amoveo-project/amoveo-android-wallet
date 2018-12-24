package com.amoveo.amoveowallet.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.dialogs.ProgressDialog;
import com.amoveo.amoveowallet.presenters.SettingsPresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.ISettingsView;

public class SettingsFragment extends BaseToolbarFragment<SettingsPresenter, ISettingsView, BaseToolbar> implements ISettingsView {
    @BindView(R.id.setup_pin)
    protected View mSetupPin;

    @BindView(R.id.new_wallet)
    protected View mNewWallet;

    @BindView(R.id.change_node)
    protected View mChangeNode;

    @BindView(R.id.show_mnemonic)
    protected View mShowMnemonic;

    @BindView(R.id.about)
    protected View mAbout;

    @BindView(R.id.export_private_key)
    protected View mExportPrivateKey;

    Dialog mProgress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mProgress = new ProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSetupPin.setOnClickListener(view1 -> mPresenter.onSetupPin());
        mNewWallet.setOnClickListener(view1 -> mPresenter.onNewWallet());
        mShowMnemonic.setOnClickListener(view1 -> mPresenter.onShowMnemonic());
        mChangeNode.setOnClickListener(view1 -> mPresenter.onChangeNode());
        mAbout.setOnClickListener(view1 -> mPresenter.onAbout());
        mExportPrivateKey.setOnClickListener(view1 -> mPresenter.onExportPrivateKey());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected BaseToolbar createToolbar(View view) {
        return new BaseToolbar(view, R.string.action_settings, (RootActivity) getActivity());
    }

    @Override
    protected SettingsPresenter createPresenter() {
        return new SettingsPresenter();
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void showProgress() {
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }
}