package com.amoveo.amoveowallet.common;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

public class CryptoUtils {
    private static final String AES = "AES";

    public static String encode(byte[] seed, String content) throws GeneralSecurityException {
        // Encode the original data with AES
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(seed));
        return Base64.encodeToString(c.doFinal(content.getBytes()), Base64.NO_WRAP);
    }

    public static String decode(byte[] seed, String content) throws GeneralSecurityException {
        // Decode the encoded data with AES
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, getSecretKeySpec(seed));
        return new String(c.doFinal(Base64.decode(content, Base64.NO_WRAP)));
    }

    private static SecretKeySpec getSecretKeySpec(byte[] seed) {
        byte[] pass = new byte[16];
        for (int i = 0; i < pass.length; i++) {
            pass[i] = seed[i % seed.length];
        }
        return new SecretKeySpec(pass, AES);
    }
}