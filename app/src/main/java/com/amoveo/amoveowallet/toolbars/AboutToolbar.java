package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.AccessWalletFragment;
import com.amoveo.amoveowallet.fragments.WalletFragment;

public class AboutToolbar extends BaseHomeToolbar {
    private final int mScreen;

    public AboutToolbar(View view, RootActivity activity, int screen) {
        super(view, R.string.action_about1, activity);

        this.mScreen = screen;
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_arrow_back_yellow;
    }

    @Override
    public void onNavigationClick() {
        switch (mScreen) {
            case 0: {
                mActivity.show(WalletFragment.newInstance());
                break;
            }
            case 1: {
                mActivity.show(AccessWalletFragment.newInstance());
                break;
            }
        }
    }
}