package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.math.BigInteger;

abstract class IntType extends NumericType {
    IntType(String typePrefix, int bitSize, BigInteger value) {
        super(typePrefix + bitSize, value);
        if (!this.valid(bitSize, value)) {
            throw new UnsupportedOperationException("Bitsize must be 8 bit aligned, and in range 0 < bitSize <= 256");
        }
    }

    boolean valid(int bitSize, BigInteger value) {
        return isValidBitSize(bitSize) && isValidBitCount(bitSize, value);
    }

    private static boolean isValidBitSize(int bitSize) {
        return bitSize % 8 == 0 && bitSize > 0 && bitSize <= 256;
    }

    private static boolean isValidBitCount(int bitSize, BigInteger value) {
        return value.bitLength() <= bitSize;
    }
}
