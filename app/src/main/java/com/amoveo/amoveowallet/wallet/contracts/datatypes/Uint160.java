package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.math.BigInteger;

public class Uint160 extends Uint {
    private static final Uint160 DEFAULT;

    Uint160(BigInteger value) {
        super(160, value);
    }

    public Uint160(long value) {
        this(BigInteger.valueOf(value));
    }

    static {
        DEFAULT = new Uint160(BigInteger.ZERO);
    }
}
