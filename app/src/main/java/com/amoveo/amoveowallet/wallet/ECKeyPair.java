package com.amoveo.amoveowallet.wallet;

import java.math.BigInteger;

import static com.amoveo.amoveowallet.wallet.Numeric.toBigInt;
import static com.amoveo.amoveowallet.wallet.Sign.publicKeyBytesFromPrivate;
import static com.amoveo.amoveowallet.wallet.Sign.publicKeyFromPrivate;

public class ECKeyPair {
    private final BigInteger privateKey;

    private ECKeyPair(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    byte[] getPublicKeyBytes(boolean compressed) {
        return publicKeyBytesFromPrivate(privateKey, compressed);
    }

    BigInteger getPublicKey() {
        return publicKeyFromPrivate(privateKey);
    }

    static ECKeyPair createPrivateKey(BigInteger privateKey) {
        return new ECKeyPair(privateKey);
    }

    static ECKeyPair createPrivateKey(byte[] privateKey) {
        return createPrivateKey(toBigInt(privateKey));
    }
}