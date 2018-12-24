package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.AccessWalletFragment;

public class RestoreWalletToolbar extends BaseHomeToolbar {
    public RestoreWalletToolbar(View view, RootActivity activity) {
        super(view, R.string.action_restore_wallet, activity);
    }

    @Override
    public void onNavigationClick() {
        mActivity.show(AccessWalletFragment.newInstance());
    }
}