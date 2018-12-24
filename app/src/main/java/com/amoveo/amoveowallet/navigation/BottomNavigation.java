package com.amoveo.amoveowallet.navigation;

import com.amoveo.amoveowallet.presenters.BaseNavigationPresenter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import static com.amoveo.amoveowallet.utils.BackPath.*;

public class BottomNavigation implements AHBottomNavigation.OnTabSelectedListener {
    private final BaseNavigationPresenter mNavigationPresenter;

    public BottomNavigation(BaseNavigationPresenter presenter) {
        mNavigationPresenter = presenter;
    }

    @Override
    public boolean onTabSelected(int position, boolean b) {
        BACK_PATH.setSreen(position);
        switch (position) {
            case NAVIGATION_RECEIVE: {
                mNavigationPresenter.onReceiveSelected();
                return true;
            }
            case NAVIGATION_SEND: {
                mNavigationPresenter.onSendSelected();
                return true;
            }
            case NAVIGATION_DASHBOARD: {
                mNavigationPresenter.onBalanceSelected();
                return true;
            }
            case NAVIGATION_SETTING: {
                mNavigationPresenter.onSettingSelected();
                return true;
            }
        }

        return false;
    }
}
