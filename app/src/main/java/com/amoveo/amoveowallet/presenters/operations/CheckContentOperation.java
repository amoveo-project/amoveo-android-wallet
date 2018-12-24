package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.fragments.CheckPinFragment;
import com.amoveo.amoveowallet.fragments.SetupPinFragment;
import com.amoveo.amoveowallet.presenters.results.ContentResult;
import com.amoveo.amoveowallet.utils.HLog;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;

public class CheckContentOperation extends CustomContentOperation {
    private byte[] mPin;

    private CheckContentOperation(RootActivity activity, byte[] pin, boolean needNotify) {
        super(activity, needNotify);
        this.mPin = pin;
    }

    @Override
    protected void onSuccess(ContentResult result) {
        SETTINGS.notifyPinAttempt(true);
        mActivity.show(SetupPinFragment.newInstance());
    }

    @Override
    protected void onFailure(ContentResult result) {
        HLog.error(TAG, "onFailure", result.getException());
        notifyFail();
        mActivity.show(CheckPinFragment.newInstance());
    }

    @Override
    byte[] getPin() {
        return mPin;
    }

    public static void checkContent(RootActivity activity, byte[] pin, boolean needNotify) {
        ENGINE.submit(new CheckContentOperation(activity, pin, needNotify));
    }
}
