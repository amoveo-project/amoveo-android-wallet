package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.wallet.Wallet;

import java.security.GeneralSecurityException;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.models.PrivacyLevel.MEDIUM;
import static com.amoveo.amoveowallet.wallet.WalletUtils.generateBip44Wallet;

public class CreateNewWalletFileOperation extends CreateWalletFileOperation {
    private String mMnemonic;
    private final String mMnemonicPassword;

    private CreateNewWalletFileOperation(String password, String mnemonic, String mnemonicPassword, RootActivity activity) {
        super(password, activity);
        this.mMnemonic = mnemonic;
        this.mMnemonicPassword = mnemonicPassword;
    }

    @Override
    protected Wallet generateWallet() throws GeneralSecurityException {
        return generateBip44Wallet(mPassword, mMnemonic, mMnemonicPassword, MEDIUM, SETTINGS.getDerivationPath());
    }

    public static void createNewWalletFile(String password, String mnemonic, String mnemonicPassword, RootActivity activity) {
        ENGINE.submit(new CreateNewWalletFileOperation(password, mnemonic, mnemonicPassword, activity));
    }
}
