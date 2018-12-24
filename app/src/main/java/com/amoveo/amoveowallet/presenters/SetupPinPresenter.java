package com.amoveo.amoveowallet.presenters;

import android.content.Context;
import android.os.Vibrator;
import com.amoveo.amoveowallet.fragments.WalletFragment;
import com.amoveo.amoveowallet.view.ISetupPinView;

import java.util.Arrays;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.presenters.operations.SaveContentOperation.saveContent;
import static java.lang.System.arraycopy;

public class SetupPinPresenter extends PinPresenter<ISetupPinView> {
    private boolean isConfirmation = false;

    private byte[] mPin0 = new byte[MAX_PIN];

    @Override
    protected void onPinEntered() {
        if (isConfirmation) {
            if (Arrays.equals(mPin0, mPin)) {
                SETTINGS.setPin(mPin);
                done();
            } else {
                ((Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(400);
                confirmPin();
            }
        } else {
            arraycopy(mPin, 0, mPin0, 0, MAX_PIN);
            isConfirmation = true;
            confirmPin();
        }
    }

    @Override
    public void onButtonSkipClick() {
        done();
    }

    private void done() {
        saveContent();
        mActivity.show(WalletFragment.newInstance());
    }

    private void confirmPin() {
        mIndex = 0;
        mView.confirmPin();
    }
}