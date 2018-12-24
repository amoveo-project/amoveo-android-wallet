package com.amoveo.amoveowallet.wallet;

import android.util.Base64;
import com.amoveo.amoveowallet.models.PrivacyLevel;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.UUID;

import static com.amoveo.amoveowallet.wallet.Credentials.createCredentials;
import static com.amoveo.amoveowallet.wallet.ECKeyPair.createPrivateKey;
import static com.amoveo.amoveowallet.wallet.HDUtils.sha3;
import static com.amoveo.amoveowallet.wallet.Keys.PRIVATE_KEY_SIZE;
import static com.amoveo.amoveowallet.wallet.Numeric.*;
import static com.amoveo.amoveowallet.wallet.SCrypt.scrypt;
import static java.lang.System.arraycopy;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.copyOfRange;

class WalletCryptoUtils {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final int R = 8;
    private static final int DKLEN = 32;

    private static final String CIPHER = "aes-128-ctr";
    static final String AES_128_CTR = "pbkdf2";
    static final String SCRYPT = "scrypt";

    private static final int CURRENT_VERSION = 3;

    static Wallet createWallet(String password, Credentials credentials, PrivacyLevel privacyLevel) throws GeneralSecurityException {
        int n = privacyLevel.getN();
        int p = privacyLevel.getP();
        byte[] salt = generateRandomBytes(32);
        byte[] derivedKey = scrypt(password.getBytes(UTF_8), salt, n, R, p, DKLEN);
        byte[] iv = generateRandomBytes(16);

        byte[] mnemonicEntropy = credentials.getMnemonicEntropy();
        ByteWriter writer = new ByteWriter(PRIVATE_KEY_SIZE + mnemonicEntropy.length);
        writer.putBytes(toBytesPadded(credentials.getEcKeyPair().getPrivateKey(), PRIVATE_KEY_SIZE));
        writer.putBytes(mnemonicEntropy);

        byte[] cipherText = performCipherOperation(Cipher.ENCRYPT_MODE, iv, copyOfRange(derivedKey, 0, 16), writer.toBytes());

        return createWallet(credentials.getEcKeyPair(), cipherText, iv, salt, generateMac(derivedKey, cipherText), n, p);
    }

    private static byte[] performCipherOperation(int mode, byte[] iv, byte[] encryptKey, byte[] text) throws GeneralSecurityException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(mode, new SecretKeySpec(encryptKey, "AES"), new IvParameterSpec(iv));
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new GeneralSecurityException("Error performing cipher operation", e);
        }
    }

    private static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];

        arraycopy(derivedKey, 16, result, 0, 16);
        arraycopy(cipherText, 0, result, 16, cipherText.length);

        return sha3(result);
    }

    private static Wallet createWallet(ECKeyPair ecKeyPair, byte[] cipherText, byte[] iv, byte[] salt, byte[] mac, int n, int p) {
        Wallet wallet = new Wallet();
        wallet.setAddress(Base64.encodeToString(ecKeyPair.getPublicKeyBytes(false), Base64.NO_WRAP));

        Wallet.Crypto crypto = new Wallet.Crypto();
        crypto.setCipher(CIPHER);
        crypto.setCiphertext(toHexStringNoPrefix(cipherText));
        wallet.setCrypto(crypto);

        Wallet.CipherParams cipherParams = new Wallet.CipherParams();
        cipherParams.setIv(toHexStringNoPrefix(iv));
        crypto.setCipherparams(cipherParams);

        crypto.setKdf(SCRYPT);
        Wallet.KdfParams kdfParams = new Wallet.KdfParams();
        kdfParams.setDklen(DKLEN);
        kdfParams.setN(n);
        kdfParams.setP(p);
        kdfParams.setR(R);
        kdfParams.setSalt(toHexStringNoPrefix(salt));
        crypto.setKdfparams(kdfParams);

        crypto.setMac(toHexStringNoPrefix(mac));
        wallet.setCrypto(crypto);
        wallet.setId(UUID.randomUUID().toString());
        wallet.setVersion(CURRENT_VERSION);

        return wallet;
    }

    private static void validate(Wallet wallet) throws GeneralSecurityException {
        Wallet.Crypto crypto = wallet.getCrypto();

        if (wallet.getVersion() != CURRENT_VERSION) {
            throw new GeneralSecurityException("WalletCryptoUtils version is not supported");
        }

        if (!crypto.getCipher().equals(CIPHER)) {
            throw new GeneralSecurityException("WalletCryptoUtils cipher is not supported");
        }

        if (!crypto.getKdf().equals(AES_128_CTR) && !crypto.getKdf().equals(SCRYPT)) {
            throw new GeneralSecurityException("KDF type is not supported");
        }
    }

    static Credentials decrypt(String password, Wallet wallet) throws GeneralSecurityException {
        validate(wallet);

        Wallet.Crypto crypto = wallet.getCrypto();

        byte[] cipherText = hexStringToByteArray(crypto.getCiphertext());
        byte[] derivedKey;

        Wallet.KdfParams kdfParams = crypto.getKdfparams();
        if (kdfParams instanceof Wallet.KdfParams) {
            Wallet.KdfParams scryptKdfParams = crypto.getKdfparams();
            derivedKey = scrypt(password.getBytes(), hexStringToByteArray(scryptKdfParams.getSalt()), scryptKdfParams.getN(), scryptKdfParams.getR(), scryptKdfParams.getP(), scryptKdfParams.getDklen());
        } else {
            throw new GeneralSecurityException("Unable to deserialize params: " + crypto.getKdf());
        }

        byte[] derivedMac = generateMac(derivedKey, cipherText);

        if (!Arrays.equals(derivedMac, hexStringToByteArray(crypto.getMac()))) {
            throw new GeneralSecurityException("Invalid password provided");
        }

        byte[] original = performCipherOperation(Cipher.DECRYPT_MODE, hexStringToByteArray(crypto.getCipherparams().getIv()), copyOfRange(derivedKey, 0, 16), cipherText);
        return createCredentials(createPrivateKey(Arrays.copyOfRange(original, 0, PRIVATE_KEY_SIZE)), Arrays.copyOfRange(original, PRIVATE_KEY_SIZE, original.length));
    }

    private static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }
}