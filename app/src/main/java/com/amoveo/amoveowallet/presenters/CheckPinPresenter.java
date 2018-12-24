package com.amoveo.amoveowallet.presenters;

import static com.amoveo.amoveowallet.presenters.operations.CheckContentOperation.checkContent;

public class CheckPinPresenter extends AccessPinPresenter {
    @Override
    protected void onPinEntered() {
        checkContent(mActivity, mPin, true);
    }
}
