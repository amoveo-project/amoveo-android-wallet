package com.amoveo.amoveowallet.fragments;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.BaseNavigationPresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IBaseNavigationView;

public class WalletFragment extends BaseNavigationFragment<BaseNavigationPresenter, IBaseNavigationView, BaseToolbar> {
    @Override
    protected int getLayout() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected BaseNavigationPresenter createPresenter() {
        return new BaseNavigationPresenter();
    }

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }
}