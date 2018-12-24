package com.amoveo.amoveowallet.presenters;

import android.widget.TextView;
import com.amoveo.amoveowallet.fragments.CreateWalletFragment;
import com.amoveo.amoveowallet.view.IRestorePrivateKeyWalletView;

import java.math.BigInteger;

import static com.amoveo.amoveowallet.wallet.BIP39Utils.validatePrivateKey;

public class RestorePrivateKeyPresenter extends BasePresenter<IRestorePrivateKeyWalletView>{
    public void onRestore(TextView text) {
        String privateKey = text.getText().toString().replaceAll("\\s\\s+", " ").trim().toLowerCase();
        if (validatePrivateKey(privateKey)) {
            mActivity.show(CreateWalletFragment.newInstance(new BigInteger(privateKey, 16)));
        } else {
            mView.showErrorDialog("Error", "Invalid private key");
        }
    }
}
