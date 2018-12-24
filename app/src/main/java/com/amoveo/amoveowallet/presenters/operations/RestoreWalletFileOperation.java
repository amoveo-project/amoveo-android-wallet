package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.wallet.Wallet;

import java.math.BigInteger;
import java.security.GeneralSecurityException;

import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.models.PrivacyLevel.MEDIUM;
import static com.amoveo.amoveowallet.wallet.WalletUtils.generateBip44Wallet;

public class RestoreWalletFileOperation extends CreateWalletFileOperation {
    private BigInteger mPrivateKey;

    private RestoreWalletFileOperation(String password, BigInteger privateKey, RootActivity activity) {
        super(password, activity);
        this.mPrivateKey = privateKey;
    }

    @Override
    protected Wallet generateWallet() throws GeneralSecurityException {
        return generateBip44Wallet(mPassword, mPrivateKey, MEDIUM);
    }

    public static void restoreWalletFile(String password, BigInteger privateKey, RootActivity activity) {
        ENGINE.submit(new RestoreWalletFileOperation(password, privateKey, activity));
    }
}