package com.amoveo.amoveowallet.wallet.contracts;

import com.amoveo.amoveowallet.wallet.Numeric;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Address;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Function;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Type;
import com.amoveo.amoveowallet.wallet.contracts.datatypes.Uint256;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.amoveo.amoveowallet.wallet.HDUtils.sha3;

public class ContractUtils {
    public static String getTransferData(String addressTo, BigInteger _value) {
        return encode(new Function("transfer", Arrays.asList(new Address(addressTo), new Uint256(_value))));
    }

    private static String encode(Function function) {
        List<Type> parameters = function.getInputParameters();
        StringBuilder result = new StringBuilder();
        result.append(buildMethodId(buildMethodSignature(function.getName(), parameters)));
        return encodeParameters(parameters, result);
    }

    private static String encodeParameters(List<Type> parameters, StringBuilder result) {
        for (Object parameter : parameters) {
            result.append(TypeEncoder.encode((Type) parameter));
        }

        return result.toString();
    }

    private static String buildMethodSignature(String methodName, List<Type> parameters) {
        StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        String params = "";
        for (Type param : parameters) {
            if (0 < params.length()) {
                params = params.concat(",");
            }
            params = params.concat(param.getTypeAsString());
        }

        result.append(params);
        result.append(")");
        return result.toString();
    }

    private static String buildMethodId(String methodSignature) {
        byte[] input = methodSignature.getBytes();
        byte[] hash = sha3(input);
        return Numeric.toHexString(hash).substring(0, 10);
    }
}
