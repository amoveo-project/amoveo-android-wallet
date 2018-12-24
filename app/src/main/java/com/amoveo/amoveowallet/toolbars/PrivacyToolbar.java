package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.RestoreWalletFragment;
import com.amoveo.amoveowallet.presenters.operations.MnemonicOperation;

public class PrivacyToolbar extends BaseHomeToolbar {
    private final int mScreen;

    public PrivacyToolbar(View view, RootActivity activity, int screen) {
        super(view, R.string.action_privacy, activity);
        this.mScreen = screen;
    }

    @Override
    public void onNavigationClick() {
        switch (mScreen) {
            case 0: {
                MnemonicOperation.execute(mActivity);
                break;
            }
            case 1: {
                mActivity.show(RestoreWalletFragment.newInstance());
                break;
            }
        }
    }
}