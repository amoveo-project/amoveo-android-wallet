package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.view.IRestoreWalletView;

import static com.amoveo.amoveowallet.utils.BackPath.BACK_PATH;

public class RestoreWalletPresenter extends BasePresenter<IRestoreWalletView> {
    public void onResume() {
        mView.setScrollPosition(BACK_PATH.getSubScreen());
    }
}