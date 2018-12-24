package com.amoveo.amoveowallet.wallet;

import java.util.Arrays;

import static com.amoveo.amoveowallet.wallet.BIP39Utils.generateMnemonic;

public class Credentials {
    private final ECKeyPair mEcKeyPair;
    private final byte[] mMnemonicEntropy;

    private Credentials(ECKeyPair ecKeyPair, byte[] mnemonicEntropy) {
        this.mEcKeyPair = ecKeyPair;
        this.mMnemonicEntropy = mnemonicEntropy;
    }

    public ECKeyPair getEcKeyPair() {
        return mEcKeyPair;
    }

    byte[] getMnemonicEntropy() {
        return mMnemonicEntropy;
    }

    public String getMnemonic() {
        return null != mMnemonicEntropy && 1 < mMnemonicEntropy.length ? generateMnemonic(Arrays.copyOfRange(mMnemonicEntropy, 1, mMnemonicEntropy.length - 1), mMnemonicEntropy[0]) : "";
    }

    static Credentials createCredentials(ECKeyPair ecKeyPair, byte[] mnemonicEntropy) {
        return new Credentials(ecKeyPair, mnemonicEntropy);
    }
}
