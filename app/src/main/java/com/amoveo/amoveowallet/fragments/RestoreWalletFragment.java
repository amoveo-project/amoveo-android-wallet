package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.adapters.RestoreFragmentsAdapter;
import com.amoveo.amoveowallet.presenters.RestoreWalletPresenter;
import com.amoveo.amoveowallet.toolbars.RestoreWalletToolbar;
import com.amoveo.amoveowallet.view.IRestoreWalletView;

public class RestoreWalletFragment extends BaseToolbarFragment<RestoreWalletPresenter, IRestoreWalletView, RestoreWalletToolbar> implements IRestoreWalletView {
    @BindView(R.id.tab_restore_methods)
    protected TabLayout mTabLayout;

    @BindView(R.id.pager_restore_methods)
    protected ViewPager mViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new RestoreFragmentsAdapter(getChildFragmentManager(), getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_restore_wallet;
    }

    @Override
    protected RestoreWalletToolbar createToolbar(View view) {
        return new RestoreWalletToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected RestoreWalletPresenter createPresenter() {
        return new RestoreWalletPresenter();
    }

    @Override
    public void setScrollPosition(int subScreen) {
        mTabLayout.getTabAt(subScreen);
    }

    public static RestoreWalletFragment newInstance() {
        return new RestoreWalletFragment();
    }
}