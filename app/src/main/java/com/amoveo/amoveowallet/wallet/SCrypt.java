package com.amoveo.amoveowallet.wallet;

import java.security.GeneralSecurityException;

public class SCrypt {
    private static final boolean native_library_loaded;

    static {
        native_library_loaded = load("scrypt");
    }

    /**
     * Implementation of the <a href="http://www.tarsnap.com/scrypt/scrypt.pdf"/>scrypt KDF</a>.
     * Calls the native implementation {@link #scryptN} when the native library was successfully
     * loaded, otherwise.
     *
     * @param passwd Password.
     * @param salt   Salt.
     * @param N      CPU cost parameter.
     * @param r      Memory cost parameter.
     * @param p      Parallelization parameter.
     * @param dkLen  Intended length of the derived key.
     * @return The derived key.
     * @throws GeneralSecurityException when HMAC_SHA256 is not available.
     */
    public static byte[] scrypt(byte[] passwd, byte[] salt, int N, int r, int p, int dkLen) throws GeneralSecurityException {
        if (native_library_loaded) {
            return scryptN(passwd, salt, N, r, p, dkLen);
        } else {
            throw new GeneralSecurityException("The SCrypt library are not loaded");
        }
    }

    private static boolean load(String name) {
        try {
            System.loadLibrary(name);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Native C implementation of the <a href="http://www.tarsnap.com/scrypt/scrypt.pdf"/>scrypt KDF</a> using
     * the code from <a href="http://www.tarsnap.com/scrypt.html">http://www.tarsnap.com/scrypt.html<a>.
     *
     * @param passwd Password.
     * @param salt   Salt.
     * @param N      CPU cost parameter.
     * @param r      Memory cost parameter.
     * @param p      Parallelization parameter.
     * @param dkLen  Intended length of the derived key.
     * @return The derived key.
     */
    public static native byte[] scryptN(byte[] passwd, byte[] salt, int N, int r, int p, int dkLen);
}
