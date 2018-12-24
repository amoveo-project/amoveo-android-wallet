package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.dialogs.TwoButtonDialog;

public abstract class AccessPinPresenter extends PinPresenter {
    @Override
    public void onButtonSkipClick() {
        new TwoButtonDialog(mActivity, getString(R.string.warning), getString(R.string.new_wallet_notify)) {
            @Override
            protected void onButtonOkClick() {
                dismiss();
                mActivity.dropWallet();
            }
        }.show();
    }
}
