package com.amoveo.amoveowallet.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.amoveo.amoveowallet.utils.PasswordWatcher;
import com.amoveo.amoveowallet.utils.SimpleTextWatcher;
import com.amoveo.amoveowallet.view.ICreateWalletView;
import com.amoveo.amoveowallet.view.IPasswordView;

import java.math.BigInteger;

import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.presenters.operations.CreateNewWalletFileOperation.createNewWalletFile;
import static com.amoveo.amoveowallet.presenters.operations.RestoreWalletFileOperation.restoreWalletFile;
import static com.amoveo.amoveowallet.wallet.Wallet.EMPTY_PASSWORD;

public class CreateWalletPresenter extends BasePresenter<ICreateWalletView> {
    public void onCreateWallet(EditText passwordET, EditText confirmET, BigInteger privateKey, String mnemonic, String mnemonicPassword) {
        Editable password = passwordET.getText();
        Editable confirm = confirmET.getText();

        if (String.valueOf(password).equals(String.valueOf(confirm))) {
            WALLET.setEmptyPassword(isEmpty(password));
            if (null == privateKey) {
                createNewWalletFile(isEmpty(password) ? EMPTY_PASSWORD : String.valueOf(password), mnemonic, mnemonicPassword, mActivity);
            } else {
                restoreWalletFile(isEmpty(password) ? EMPTY_PASSWORD : String.valueOf(password), privateKey, mActivity);
            }
        }
    }

    public TextWatcher getPasswordWatcher(IPasswordView view) {
        return new PasswordWatcher(view);
    }

    public TextWatcher getConfirmWatcher() {
        return new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                mView.checkButton();
            }
        };
    }
}