package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.fragments.SetupPinFragment;
import com.amoveo.amoveowallet.fragments.SplashFragment;
import com.amoveo.amoveowallet.presenters.results.CreateNewWalletFileResult;
import com.amoveo.amoveowallet.utils.HLog;
import com.amoveo.amoveowallet.wallet.Wallet;

import java.security.GeneralSecurityException;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.utils.BackPath.BACK_PATH;
import static com.amoveo.amoveowallet.utils.BackPath.NAVIGATION_DASHBOARD;

public abstract class CreateWalletFileOperation extends EngineOperation<CreateNewWalletFileResult> {
    final String mPassword;
    protected final RootActivity mActivity;

    CreateWalletFileOperation(String password, RootActivity activity) {
        this.mPassword = password;
        this.mActivity = activity;
    }

    @Override
    protected void onStart() {
        mActivity.show(SplashFragment.newInstance());
    }

    @Override
    protected CreateNewWalletFileResult execute() {
        try {
            return new CreateNewWalletFileResult(generateWallet());
        } catch (Exception e) {
            return new CreateNewWalletFileResult(e);
        }
    }

    protected abstract Wallet generateWallet() throws GeneralSecurityException;

    @Override
    protected void onSuccess(CreateNewWalletFileResult result) {
        HLog.debug(TAG, "onSuccess " + result.getResult().getAddress());
        try {
            Wallet wallet = result.getResult();
            HLog.debug(TAG, "onSuccess " + wallet.getAddress());
            WALLET.setWallet(wallet);
            QRCodeOperation.execute(mActivity);
            SETTINGS.init();
            PollingOperation.executeImmediately();

            BACK_PATH.setSreen(NAVIGATION_DASHBOARD);
            BACK_PATH.setSubscreen(0);
            mActivity.show(SetupPinFragment.newInstance());
        } catch (Exception e) {
            onFailure(new CreateNewWalletFileResult(e));
        }
    }

    @Override
    protected void onFailure(CreateNewWalletFileResult result) {
        HLog.error(TAG, "onFailure", result.getException());
    }
}
