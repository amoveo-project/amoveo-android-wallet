package com.amoveo.amoveowallet.wallet;

import com.amoveo.amoveowallet.models.PrivacyLevel;

import java.math.BigInteger;
import java.security.GeneralSecurityException;

import static com.amoveo.amoveowallet.wallet.BIP39Utils.*;
import static com.amoveo.amoveowallet.wallet.BIP44Utils.deriveKey;
import static com.amoveo.amoveowallet.wallet.Credentials.createCredentials;
import static com.amoveo.amoveowallet.wallet.ECKeyPair.createPrivateKey;
import static com.amoveo.amoveowallet.wallet.WalletCryptoUtils.createWallet;
import static com.amoveo.amoveowallet.wallet.WalletCryptoUtils.decrypt;

public class WalletUtils {
    public static Wallet generateBip44Wallet(String password, BigInteger privateKey, PrivacyLevel privacyLevel) throws GeneralSecurityException {
        return generateBip44Wallet(password, createCredentials(createPrivateKey(privateKey), new byte[0]), privacyLevel);
    }

    public static Wallet generateBip44Wallet(String password, String mnemonic, String mnemonicPassword, PrivacyLevel privacyLevel, String derivationPath) throws GeneralSecurityException {
        return generateBip44Wallet(password, createCredentials(deriveKey(generateSeed(mnemonic, generateSalt(mnemonic, mnemonicPassword)), derivationPath), mnemonicToInitialEntropy(mnemonic)), privacyLevel);
    }

    public static Wallet generateBip44Wallet(String password, Credentials credentials, PrivacyLevel privacyLevel) throws GeneralSecurityException {
        return createWallet(password, credentials, privacyLevel);
    }

    public static Credentials loadCredentials(String password, Wallet wallet) throws GeneralSecurityException {
        return decrypt(password, wallet);
    }
}
