package com.amoveo.amoveowallet.wallet;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.wallet.HDUtils.sha256;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.copyOfRange;

public class BIP39Utils {
    private static final int SEED_ITERATIONS = 2048;
    private static final int SEED_KEY_SIZE = 512;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String MNEMONICS_FORMAT = "mnemonic%s";
    public static List<String> EN_WORD_LIST;
    public static List<String> FR_WORD_LIST;
    public static List<String> SP_WORD_LIST;
    public static List<String> IT_WORD_LIST;
    public static List<String> CZ_WORD_LIST;
    public static List<String> RU_WORD_LIST;
    public static List<String> UK_WORD_LIST;
    public static List<String> IN_WORD_LIST;
    public static List<String> JP_WORD_LIST;
    public static List<String> KO_WORD_LIST;
    public static List<String> CH_SM_WORD_LIST;
    public static List<String> CH_TR_WORD_LIST;

    static byte[] mnemonicToInitialEntropy(String mnemonic) {
        byte language = getLanguage(mnemonic);
        byte[] bytes = mnemonicToBytes(mnemonic, language);

        ByteWriter writer = new ByteWriter(bytes.length + 1);
        writer.put(language);
        writer.putBytes(bytes);

        return writer.toBytes();
    }

    private static byte[] mnemonicToBytes(String mnemonic, int language) {
        String[] wordList = mnemonic.split(" ");

        if (3 != wordList.length && 6 != wordList.length && 9 != wordList.length && 12 != wordList.length && 15 != wordList.length && 18 != wordList.length && 21 != wordList.length && 24 != wordList.length) {
            throw new IllegalArgumentException("Word list must be 12, 15, 18, 21, or 24 words and not " + wordList.length);
        }

        int bitLength = wordList.length * 11;
        byte[] buf = new byte[bitLength / 8 + ((bitLength % 8) > 0 ? 1 : 0)];
        for (int i = 0; i < wordList.length; i++) {
            String word = wordList[i];
            int wordIndex = getWordIndex(word, language);
            if (wordIndex == -1) {
                throw new IllegalArgumentException("The word '" + word + "' is not valid");
            }
            integerTo11Bits(buf, i * 11, wordIndex);
        }
        return buf;
    }

    private static void integerTo11Bits(byte[] buf, int bitIndex, int integer) {
        for (int i = 0; i < 11; i++) {
            if ((integer & 0x400) == 0x400) {
                setBit(buf, bitIndex + i);
            }
            integer = integer << 1;
        }
    }

    private static void setBit(byte[] buf, int bitIndex) {
        int value = ((int) buf[bitIndex / 8]) & 0xFF;
        value = value | (1 << (7 - (bitIndex % 8)));
        buf[bitIndex / 8] = (byte) value;
    }

    private static int getWordIndex(String word, int language) {
        for (int i = 0; i < getWordList(language).size(); i++) {
            if (getWordList(language).get(i).equals(word)) {
                return i;
            }
        }
        return -1;
    }

    private static List<String> getWordList(int language) {
        switch (language) {
            case 0: {
                return EN_WORD_LIST;
            }
            case 1: {
                return FR_WORD_LIST;
            }
            case 2: {
                return SP_WORD_LIST;
            }
            case 3: {
                return IT_WORD_LIST;
            }
            case 4: {
                return CZ_WORD_LIST;
            }
            case 5: {
                return RU_WORD_LIST;
            }
            case 6: {
                return UK_WORD_LIST;
            }
            case 7: {
                return IN_WORD_LIST;
            }
            case 8: {
                return JP_WORD_LIST;
            }
            case 9: {
                return KO_WORD_LIST;
            }
            case 10: {
                return CH_SM_WORD_LIST;
            }
            case 11: {
                return CH_TR_WORD_LIST;
            }
        }
        return EN_WORD_LIST;
    }

    /**
     * To createPrivateKey a binary seed from the mnemonic, we use the PBKDF2 function with a
     * mnemonic sentence (in UTF-8 NFKD) used as the password and the string "mnemonic"
     * + passphrase (again in UTF-8 NFKD) used as the salt. The iteration count is set
     * to 2048 and HMAC-SHA512 is used as the pseudo-random function. The length of the
     * derived key is 512 bits (= 64 bytes).
     *
     * @param mnemonic The input mnemonic which should be 128-160 bits in length containing
     *                 only valid words
     * @param salt
     * @return Byte array representation of the generated seed
     */
    static byte[] generateSeed(String mnemonic, byte[] salt) {
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA512Digest());
        gen.init(mnemonic.getBytes(UTF_8), salt, SEED_ITERATIONS);
        return ((KeyParameter) gen.generateDerivedParameters(SEED_KEY_SIZE)).getKey();
    }

    static byte[] generateSalt(String mnemonic, String passphrase) {
        validate(mnemonic);
        return format(MNEMONICS_FORMAT, isEmpty(passphrase) ? "" : passphrase).getBytes(UTF_8);
    }

    private static void validate(String mnemonic) {
        if (null == mnemonic || !validateMnemonic(mnemonic)) {
            throw new IllegalArgumentException("Mnemonic is required to generate a seed");
        }
    }

    private static byte getLanguage(String mnemonic) {
        boolean result = false;
        String[] mnemonicWords = mnemonic.split(" ");

        byte language;

        for (language = 0; !result && language < 12; language++) {
            result = true;

            for (int index = 0; result && index < mnemonicWords.length; index++) {
                boolean found = false;
                for (String memo : getWordList(language)) {
                    found |= memo.equals(mnemonicWords[index]);
                }

                result = found;
            }
        }
        return result ? --language : -1;
    }

    public static boolean validatePrivateKey(String privateKey) {
        try {
            new BigInteger(privateKey, 16);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateMnemonic(String mnemonic) {
        boolean result = false;
        String[] mnemonicWords = mnemonic.split(" ");

        for (int language = 0; !result && language < 12; language++) {
            result = true;

            for (int index = 0; result && index < mnemonicWords.length; index++) {
                boolean found = false;
                for (String memo : getWordList(language)) {
                    found |= memo.equals(mnemonicWords[index]);
                }

                result = found;
            }
        }

        return result && (3 == mnemonicWords.length || 6 == mnemonicWords.length || 9 == mnemonicWords.length || 12 == mnemonicWords.length || 15 == mnemonicWords.length || 18 == mnemonicWords.length || 21 == mnemonicWords.length || 24 == mnemonicWords.length);
    }

    public static String generateMnemonic(int language, int length) {
        int size = length * 4 / 3;
        byte[] initialEntropy = new byte[size];
        SECURE_RANDOM.nextBytes(initialEntropy);
        return generateMnemonic(initialEntropy, language);
    }

    static String generateMnemonic(byte[] initialEntropy, int language) {
        validateInitialEntropy(initialEntropy);

        int ent = initialEntropy.length * 8;
        int checksumLength = ent / 32;

        byte checksum = calculateChecksum(initialEntropy);
        boolean[] bits = convertToBits(initialEntropy, checksum);

        int iterations = (ent + checksumLength) / 11;
        StringBuilder mnemonicBuilder = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            int index = toInt(nextElevenBits(bits, i));
            mnemonicBuilder.append(getWordList(language).get(index));

            boolean notLastIteration = i < iterations - 1;
            if (notLastIteration) {
                mnemonicBuilder.append(" ");
            }
        }

        return mnemonicBuilder.toString();
    }

    private static boolean[] nextElevenBits(boolean[] bits, int i) {
        int from = i * 11;
        int to = from + 11;
        return copyOfRange(bits, from, to);
    }

    private static void validateInitialEntropy(byte[] initialEntropy) {
        if (null == initialEntropy) {
            throw new IllegalArgumentException("Initial entropy is required");
        }

        int ent = initialEntropy.length * 8;
        if (ent < 128 || ent > 256 || ent % 32 != 0) {
            throw new IllegalArgumentException("The allowed size of ENT is 128-256 bits of multiples of 32 " + ent);
        }
    }

    private static byte calculateChecksum(byte[] initialEntropy) {
        int ent = initialEntropy.length * 8;
        byte mask = (byte) (0xff << 8 - ent / 32);
        byte[] bytes = sha256(initialEntropy);

        return (byte) (bytes[0] & mask);
    }

    private static boolean[] convertToBits(byte[] initialEntropy, byte checksum) {
        int ent = initialEntropy.length * 8;
        int checksumLength = ent / 32;
        int totalLength = ent + checksumLength;
        boolean[] bits = new boolean[totalLength];

        for (int i = 0; i < initialEntropy.length; i++) {
            for (int j = 0; j < 8; j++) {
                byte b = initialEntropy[i];
                bits[8 * i + j] = toBit(b, j);
            }
        }

        for (int i = 0; i < checksumLength; i++) {
            bits[ent + i] = toBit(checksum, i);
        }

        return bits;
    }

    private static boolean toBit(byte value, int index) {
        return ((value >>> (7 - index)) & 1) > 0;
    }

    private static int toInt(boolean[] bits) {
        int value = 0;
        for (int i = 0; i < bits.length; i++) {
            boolean isSet = bits[i];
            if (isSet) {
                value += 1 << bits.length - i - 1;
            }
        }

        return value;
    }
}
