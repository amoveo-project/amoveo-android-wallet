package com.amoveo.amoveowallet.wallet;

class Strings {
    static String zeros(int n) {
        return repeat('0', n);
    }

    private static String repeat(char value, int n) {
        return new String(new char[n]).replace("\0", String.valueOf(value));
    }
}