package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.math.BigInteger;

public class Uint extends IntType {
    private Uint(String typePrefix, int bitSize, BigInteger value) {
        super(typePrefix, bitSize, value);
    }

    Uint(int bitSize, BigInteger value) {
        this("uint", bitSize, value);
    }

    boolean valid(int bitSize, BigInteger value) {
        return super.valid(bitSize, value) && value.signum() != -1;
    }
}
