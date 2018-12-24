package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.api.results.items.Balance;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;
import com.amoveo.amoveowallet.presenters.listeners.StartedOperationListener;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.presenters.results.EmptyResult;
import com.amoveo.amoveowallet.utils.HLog;

import static com.amoveo.amoveowallet.api.operations.ObtainBalanceOperation.obtainBalance;
import static com.amoveo.amoveowallet.api.operations.ObtainHeightOperation.obtainHeight;
import static com.amoveo.amoveowallet.api.operations.ObtainPendingTransactionsOperation.obtainPendingTransactions;
import static com.amoveo.amoveowallet.api.operations.ObtainTransactionHistoryOperation.obtainTransactionHistory;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.presenters.operations.SaveContentOperation.saveContent;

public class PollingOperation extends EngineOperation<EmptyResult> {
    @Override
    protected EmptyResult execute() {
        obtainBalance(WALLET.getAddress(), new StartedOperationListener<Balance>() {
            @Override
            public void onSuccess(Balance result) {
                HLog.debug(TAG, "onSuccess " + result);

                String balance = WALLET.getBalance();
                WALLET.setBalance(result.getBalance());

                if (!balance.equals(WALLET.getBalance())) {
                    saveContent();
                }

                SETTINGS.notifyConnection(new ConnectionResult());
            }

            @Override
            public void onFailure(ExceptionResult result) {
                HLog.error(TAG, "onFailure", result.getException());
                SETTINGS.notifyConnection(new ConnectionResult(result.getException()));
            }
        });
        obtainPendingTransactions();
        obtainTransactionHistory(WALLET.getAddress());
        obtainHeight();
        return new EmptyResult();
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onSuccess(EmptyResult result) {
        schedule(30);
    }

    @Override
    protected void onFailure(EmptyResult result) {
        HLog.error(TAG, "onFailure", result.getException());
    }

    static void executeImmediately() {
        ENGINE.submit(new PollingOperation());
    }

    private static void schedule(int timeout) {
        ENGINE.schedule(new PollingOperation(), timeout);
    }
}