package com.amoveo.amoveowallet.presenters;

import android.widget.TextView;
import com.amoveo.amoveowallet.fragments.CreateWalletFragment;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.wallet.BIP39Utils.validateMnemonic;

public class RestoreMnemonicPresenter extends BasePresenter<IBaseView> {
    public void onRestore(TextView text) {
        String mnemonic = text.getText().toString().replaceAll("\\s\\s+", " ").trim().toLowerCase();
        if (validateMnemonic(mnemonic)) {
            mActivity.show(CreateWalletFragment.newInstance(mnemonic));
        } else {
            mView.showErrorDialog("Error", "Invalid mnemonic");
        }
    }
}