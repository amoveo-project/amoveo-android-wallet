package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;
import com.amoveo.amoveowallet.presenters.results.DecryptResult;
import com.amoveo.amoveowallet.utils.HLog;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.wallet.WalletUtils.loadCredentials;

public class DecryptOperations extends EngineOperation<DecryptResult> {
    private OperationListener<DecryptResult> mStateManager;
    private final String mPassword;

    private DecryptOperations(String mPassword, OperationListener stateManager) {
        this.mPassword = mPassword;
        mStateManager = stateManager;
    }

    @Override
    protected DecryptResult execute() {
        try {
            return new DecryptResult(loadCredentials(mPassword, WALLET.getWallet()));
        } catch (Exception e) {
            return new DecryptResult(e);
        }
    }

    @Override
    protected void onStart() {
        mStateManager.onStart();
    }

    @Override
    protected void onSuccess(DecryptResult result) {
        HLog.debug(TAG, "onSuccess");
        WALLET.setCredentials(result.getResult());
        mStateManager.onSuccess(result);
    }

    @Override
    protected void onFailure(DecryptResult result) {
        HLog.error(TAG, "onFailure", result.getException());
        mStateManager.onFailure(result);
    }

    public static void decrypt(String password, OperationListener stateManager) {
        ENGINE.submit(new DecryptOperations(password, stateManager));
    }
}