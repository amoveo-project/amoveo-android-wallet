package com.amoveo.amoveowallet.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.AccessWalletPresenter;
import com.amoveo.amoveowallet.view.IBaseView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.amoveo.amoveowallet.utils.Utils.selectLogo;

public class AccessWalletFragment extends BaseFragment<AccessWalletPresenter, IBaseView> implements IBaseView {
    @BindView(R.id.logo)
    View mLogo;

    @BindView(R.id.logo_compat)
    View mLogoCompat;

    @BindView(R.id.create_wallet_button)
    View mCreateWalletButton;

    @BindView(R.id.restore_wallet_button)
    View mRestoreWalletButton;

    @BindView(R.id.about)
    View mAbout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectLogo(mLogo, mLogoCompat);

        mCreateWalletButton.setOnClickListener(v -> mPresenter.onCreateWallet());
        mRestoreWalletButton.setOnClickListener(v -> mPresenter.onRestoreWallet());
        mAbout.setOnClickListener(v -> mPresenter.onAbout());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_access_wallet;
    }

    @Override
    protected AccessWalletPresenter createPresenter() {
        return new AccessWalletPresenter();
    }

    public static AccessWalletFragment newInstance() {
        return new AccessWalletFragment();
    }
}
