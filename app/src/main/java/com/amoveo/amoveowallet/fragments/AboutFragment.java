package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.BuildConfig;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.AboutPresenter;
import com.amoveo.amoveowallet.toolbars.AboutToolbar;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.utils.Utils.selectLogo;

public class AboutFragment extends BaseToolbarFragment<AboutPresenter, IBaseView, BaseToolbar> implements IBaseView {

    @BindView(R.id.version)
    TextView mVersion;

    @BindView(R.id.twitter_click)
    View mTwitter;

    @BindView(R.id.logo)
    View mLogo;

    @BindView(R.id.logo_compat)
    View mLogoCompat;

    private int mScreen;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVersion.setText(BuildConfig.VERSION_NAME);
        mTwitter.setOnClickListener(v -> mPresenter.onTwitter());

        selectLogo(mLogo, mLogoCompat);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter();
    }


    @Override
    protected BaseToolbar createToolbar(View view) {
        return new AboutToolbar(view, (RootActivity) getActivity(), mScreen);
    }

    private BaseFragment setScreen(int screen) {
        this.mScreen = screen;
        return this;
    }

    public static BaseFragment newInstance(int screen) {
        return new AboutFragment().setScreen(screen);
    }
}
