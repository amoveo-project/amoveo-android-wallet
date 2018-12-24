package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import com.amoveo.amoveowallet.wallet.Numeric;

import java.math.BigInteger;

public class Address implements Type<String> {
    private final Uint160 value;

    public Address(String hexValue) {
        this(Numeric.toBigInt(hexValue));
    }

    private Address(BigInteger value) {
        this(new Uint160(value));
    }

    private Address(Uint160 value) {
        this.value = value;
    }

    public Uint160 toUint160() {
        return this.value;
    }

    public String toString() {
        return Numeric.toHexStringWithPrefixZeroPadded(this.value.getValue(), 40);
    }

    public String getValue() {
        return this.toString();
    }

    public String getTypeAsString() {
        return "address";
    }
}
