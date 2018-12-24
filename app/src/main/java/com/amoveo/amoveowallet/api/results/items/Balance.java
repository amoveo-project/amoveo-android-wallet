package com.amoveo.amoveowallet.api.results.items;

public class Balance {
    private final String mBalance;
    private final boolean isEmpty;

    public Balance() {
        mBalance = "0";
        isEmpty = true;
    }

    public Balance(String[] result) {
        mBalance = getBalance(result);
        isEmpty = false;
    }

    public String getBalance() {
        return mBalance;
    }

    public Boolean isEmpty() {
        return isEmpty;
    }

    private static String getBalance(String[] result) {
        return result[1];
    }
}