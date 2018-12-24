package com.amoveo.amoveowallet.wallet.contracts.datatypes;

public interface Type<T> {
    int MAX_BIT_LENGTH = 256;
    int MAX_BYTE_LENGTH = 32;

    T getValue();

    String getTypeAsString();
}
