package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.AccessWalletFragment;
import com.amoveo.amoveowallet.fragments.LoginPinFragment;
import com.amoveo.amoveowallet.fragments.WalletFragment;
import com.amoveo.amoveowallet.presenters.results.ContentResult;
import com.amoveo.amoveowallet.utils.HLog;

import java.security.GeneralSecurityException;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;

public class LoadContentOperation extends CustomContentOperation {
    private LoadContentOperation(RootActivity activity, boolean needNotify) {
        super(activity, needNotify);
    }

    @Override
    protected void onSuccess(ContentResult result) {
        result.getResult().resolve();

        HLog.debug(TAG, "onSuccess " + SETTINGS.getCurrency());

        QRCodeOperation.execute(mActivity);
        PollingOperation.executeImmediately();

        SETTINGS.notifyPinAttempt(true);
        mActivity.show(WalletFragment.newInstance());
    }

    @Override
    protected void onFailure(ContentResult result) {
        HLog.error(TAG, "onFailure", result.getException());
        if (result.getException() instanceof GeneralSecurityException) {
            notifyFail();
            mActivity.show(LoginPinFragment.newInstance());
        } else {
            mActivity.show(AccessWalletFragment.newInstance());
        }
    }

    @Override
    byte[] getPin() {
        return SETTINGS.getPin();
    }

    public static void loadContent(RootActivity activity, boolean needNotify) {
        ENGINE.submit(new LoadContentOperation(activity, needNotify));
    }
}
