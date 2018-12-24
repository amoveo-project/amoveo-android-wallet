package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.WalletFragment;

public class TransactionInfoToolbar extends BaseHomeToolbar {

    public TransactionInfoToolbar(View view, RootActivity activity) {
        super(view, R.string.action_transaction, activity);
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_arrow_back_yellow;
    }

    @Override
    public void onNavigationClick() {
        mActivity.show(WalletFragment.newInstance());
    }
}