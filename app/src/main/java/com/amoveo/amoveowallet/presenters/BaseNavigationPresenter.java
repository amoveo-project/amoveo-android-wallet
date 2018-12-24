package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.fragments.BalanceFragment;
import com.amoveo.amoveowallet.fragments.ReceiveFragment;
import com.amoveo.amoveowallet.fragments.SendFragment;
import com.amoveo.amoveowallet.fragments.SettingsFragment;
import com.amoveo.amoveowallet.view.IBaseNavigationView;

import static com.amoveo.amoveowallet.utils.BackPath.BACK_PATH;

public class BaseNavigationPresenter<V extends IBaseNavigationView> extends BasePresenter<V> {
    @Override
    public void onResume() {
        mView.setSelectedItemPosition(BACK_PATH.getScreen());

        if (BACK_PATH.hasMessage()) {
            mView.showInfoDialog(getString(BACK_PATH.getMessageText()));
            BACK_PATH.dropMessage();
        }
    }

    public void onReceiveSelected() {
        mActivity.showNavigable(ReceiveFragment.newInstance());
    }

    public void onBalanceSelected() {
        mActivity.showNavigable(BalanceFragment.newInstance());
    }

    public void onSendSelected() {
        mActivity.showNavigable(SendFragment.newInstance());
    }

    public void onSettingSelected() {
        mActivity.showNavigable(SettingsFragment.newInstance());
    }
}