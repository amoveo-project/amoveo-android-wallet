package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.SplashPresenter;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.utils.Utils.selectLogo;

public class SplashFragment extends BaseFragment<SplashPresenter, IBaseView> implements IBaseView {
    @BindView(R.id.logo)
    View mLogo;

    @BindView(R.id.logo_compat)
    View mLogoCompat;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectLogo(mLogo, mLogoCompat);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }
}
