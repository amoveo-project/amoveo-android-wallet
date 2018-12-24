package com.amoveo.amoveowallet.wallet;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jcajce.provider.digest.SHA256;

import static com.amoveo.amoveowallet.wallet.Numeric.hexStringToByteArray;
import static com.amoveo.amoveowallet.wallet.Numeric.toHexString;

public class HDUtils {
    private static HMac sha512Digest(byte[] key) {
        HMac hMac = new HMac(new SHA512Digest());
        hMac.init(new KeyParameter(key));
        return hMac;
    }

    private static byte[] sha512(HMac hmacSha512, byte[] input) {
        hmacSha512.reset();
        hmacSha512.update(input, 0, input.length);
        byte[] out = new byte[64];
        hmacSha512.doFinal(out, 0);
        return out;
    }

    static byte[] sha512(byte[] key, byte[] input) {
        return sha512(sha512Digest(key), input);
    }

    static byte[] sha256(byte[] input) {
        return new SHA256.Digest().digest(input);
    }

    public static String sha3(String hexInput) {
        return toHexString(sha3(hexStringToByteArray(hexInput)));
    }

    public static byte[] sha3(byte[] input) {
        return new Keccak.Digest256().digest(input);
    }
}
