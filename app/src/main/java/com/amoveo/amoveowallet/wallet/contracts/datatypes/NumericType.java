package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.math.BigInteger;

public abstract class NumericType implements Type<BigInteger> {
    private String type;
    private BigInteger value;

    NumericType(String type, BigInteger value) {
        this.type = type;
        this.value = value;
    }

    public BigInteger getValue() {
        return this.value;
    }

    public String getTypeAsString() {
        return this.type;
    }
}
