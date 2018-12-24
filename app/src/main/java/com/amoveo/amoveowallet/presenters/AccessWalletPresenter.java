package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.fragments.AboutFragment;
import com.amoveo.amoveowallet.fragments.RestoreWalletFragment;
import com.amoveo.amoveowallet.presenters.operations.MnemonicOperation;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.utils.BackPath.BACK_PATH;

public class AccessWalletPresenter extends BasePresenter<IBaseView> {
    public void onCreateWallet() {
        BACK_PATH.setMessageText(R.string.message_wallet_created);
        MnemonicOperation.execute(mActivity);
    }

    public void onRestoreWallet() {
        BACK_PATH.setMessageText(R.string.message_wallet_restored);
        mActivity.show(RestoreWalletFragment.newInstance());
    }

    public void onAbout() {
        mActivity.show(AboutFragment.newInstance(1));
    }
}
