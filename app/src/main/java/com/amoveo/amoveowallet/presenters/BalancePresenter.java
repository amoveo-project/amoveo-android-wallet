package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.presenters.results.WalletContextUpdate;
import com.amoveo.amoveowallet.view.IBalanceView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class BalancePresenter extends BasePresenter<IBalanceView> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final WalletContextUpdate event) {
        if (event.isSuccess()) {
            mView.showBalance(WALLET.getBalance());
            mView.showTransactions(WALLET.getTransactionInfos());
        } else {
            mView.showErrorDialog(getString(R.string.error), event.getExceptionMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final ConnectionResult event) {
        mView.notifyConnection(event.isSuccess());
    }

    @Override
    public void onResume() {
        mView.showBalance(WALLET.getBalance());
        mView.showTransactions(WALLET.getTransactionInfos());
        mView.notifyConnection(SETTINGS.isConnected());
    }
}
