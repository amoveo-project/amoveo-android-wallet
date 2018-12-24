package com.amoveo.amoveowallet.wallet.contracts;

import com.amoveo.amoveowallet.wallet.Numeric;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Address;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.NumericType;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Type;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Uint;

import java.math.BigInteger;

public class TypeEncoder {

    public static String encode(Type parameter) {
        if (parameter instanceof NumericType) {
            return encodeNumeric((NumericType) parameter);
        } else if (parameter instanceof Address) {
            return encodeAddress((Address) parameter);
        } else {
            throw new UnsupportedOperationException("Type cannot be encoded: " + parameter.getClass());
        }
    }

    private static String encodeAddress(Address address) {
        return encodeNumeric(address.toUint160());
    }

    private static String encodeNumeric(NumericType numericType) {
        byte[] rawValue = toByteArray(numericType);
        byte paddingValue = getPaddingValue(numericType);
        byte[] paddedRawValue = new byte[32];
        if (paddingValue != 0) {
            for (int i = 0; i < paddedRawValue.length; ++i) {
                paddedRawValue[i] = paddingValue;
            }
        }

        System.arraycopy(rawValue, 0, paddedRawValue, 32 - rawValue.length, rawValue.length);
        return Numeric.toHexStringNoPrefix(paddedRawValue);
    }

    private static byte getPaddingValue(NumericType numericType) {
        return (byte) (numericType.getValue().signum() == -1 ? -1 : 0);
    }

    private static byte[] toByteArray(NumericType numericType) {
        BigInteger value = numericType.getValue();
        if ((numericType instanceof Uint) && value.bitLength() == 256) {
            byte[] byteArray = new byte[32];
            System.arraycopy(value.toByteArray(), 1, byteArray, 0, 32);
            return byteArray;
        } else {
            return value.toByteArray();
        }
    }
}
