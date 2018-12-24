package com.amoveo.amoveowallet.fragments.listeners;

import android.view.View;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.fragments.PendingTransactionInfoFragment;
import com.amoveo.amoveowallet.fragments.TransactionInfoFragment;

public class OnTransactionInfoClickListener implements View.OnClickListener {
    private final RootActivity mActivity;
    private final TransactionInfo mTransactionInfo;

    public OnTransactionInfoClickListener(RootActivity activity, TransactionInfo transactionInfo) {
        this.mActivity = activity;
        this.mTransactionInfo = transactionInfo;
    }

    @Override
    public void onClick(View view) {
        mActivity.show(mTransactionInfo.isPending() ? PendingTransactionInfoFragment.newInstance(mTransactionInfo) : TransactionInfoFragment.newInstance(mTransactionInfo));
    }
}