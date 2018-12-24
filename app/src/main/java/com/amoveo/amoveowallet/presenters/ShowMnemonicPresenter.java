package com.amoveo.amoveowallet.presenters;

import com.amoveo.amoveowallet.fragments.CreateWalletFragment;
import com.amoveo.amoveowallet.view.IBaseView;

public class ShowMnemonicPresenter extends BasePresenter<IBaseView> {

    private String mMnemonic;

    public ShowMnemonicPresenter(String mnemonic) {
        mMnemonic = mnemonic;
    }

    public void onContinue() {
        mActivity.show(CreateWalletFragment.newInstance(mMnemonic));
    }
}