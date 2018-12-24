package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Spinner;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.PrivacyPresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.toolbars.PrivacyToolbar;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class PrivacyFragment extends BaseToolbarFragment<PrivacyPresenter, IBaseView, BaseToolbar> implements IBaseView {
    @BindView(R.id.language)
    protected Spinner mLanguage;

    @BindView(R.id.privacy)
    protected Spinner mPrivacy;

    @BindView(R.id.mnemonic_length)
    protected Spinner mMnemonicLength;
    private int mScreen;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLanguage.setSelection(SETTINGS.getLanguage());
        mPrivacy.setSelection(SETTINGS.getPrivacy());
        mMnemonicLength.setSelection(SETTINGS.getMnemonicSelection());

        mLanguage.setOnItemSelectedListener(mPresenter.OnSelectLanguage());
        mPrivacy.setOnItemSelectedListener(mPresenter.OnSelectPrivacy());
        mMnemonicLength.setOnItemSelectedListener(mPresenter.OnSelectMnemonicLength());
    }

    @Override
    protected BaseToolbar createToolbar(View view) {
        return new PrivacyToolbar(view, (RootActivity) getActivity(), mScreen);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_privacy;
    }

    @Override
    protected PrivacyPresenter createPresenter() {
        return new PrivacyPresenter();
    }

    private BaseFragment setScreen(int screen) {
        this.mScreen = screen;
        return this;
    }

    public static BaseFragment newInstance(int screen) {
        return new PrivacyFragment().setScreen(screen);
    }
}
