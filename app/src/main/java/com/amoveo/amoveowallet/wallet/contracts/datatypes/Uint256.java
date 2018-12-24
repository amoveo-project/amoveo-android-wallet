package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.math.BigInteger;

public class Uint256 extends Uint {
    private static final Uint256 DEFAULT;

    public Uint256(BigInteger value) {
        super(256, value);
    }

    static {
        DEFAULT = new Uint256(BigInteger.ZERO);
    }
}
