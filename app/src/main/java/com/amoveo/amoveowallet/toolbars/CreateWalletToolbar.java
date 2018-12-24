package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.AccessWalletFragment;

public class CreateWalletToolbar extends BaseHomeToolbar {
    public CreateWalletToolbar(View view, RootActivity activity) {
        super(view, R.string.action_set_wallet_password, activity);
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_arrow_back_yellow;
    }

    @Override
    public void onNavigationClick() {
        mActivity.show(AccessWalletFragment.newInstance());
    }
}