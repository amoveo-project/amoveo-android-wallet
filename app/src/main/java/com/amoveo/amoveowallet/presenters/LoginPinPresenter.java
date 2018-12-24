package com.amoveo.amoveowallet.presenters;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.presenters.operations.LoadContentOperation.loadContent;

public class LoginPinPresenter extends AccessPinPresenter {
    @Override
    protected void onPinEntered() {
        SETTINGS.setPin(mPin);
        loadContent(mActivity, true);
    }
}