package com.amoveo.amoveowallet.wallet;

import static com.amoveo.amoveowallet.wallet.HDUtils.sha3;
import static com.amoveo.amoveowallet.wallet.Numeric.cleanHexPrefix;
import static com.amoveo.amoveowallet.wallet.Numeric.toHexStringWithPrefixZeroPadded;

public class Keys {
    static final int PRIVATE_KEY_SIZE = 32;
    static final int PUBLIC_KEY_SIZE = 64;
    static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;

    private static final int ADDRESS_SIZE = 160;
    private static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_SIZE >> 2;

    // Web3j implementations
    public static String getAddress(ECKeyPair ecKeyPair) {
        String publicKeyNoPrefix = cleanHexPrefix(toHexStringWithPrefixZeroPadded(ecKeyPair.getPublicKey(), PUBLIC_KEY_LENGTH_IN_HEX));

        if (publicKeyNoPrefix.length() < PUBLIC_KEY_LENGTH_IN_HEX) {
            publicKeyNoPrefix = Strings.zeros(PUBLIC_KEY_LENGTH_IN_HEX - publicKeyNoPrefix.length()) + publicKeyNoPrefix;
        }

        String hash = sha3(publicKeyNoPrefix);
        return hash.substring(hash.length() - ADDRESS_LENGTH_IN_HEX);  // right most 160 bits
    }
}
