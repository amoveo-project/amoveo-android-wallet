package com.amoveo.amoveowallet.api.results.items;

public class Height {
    private final int mHeight;

    public Height() {
        mHeight = 0;
    }

    public Height(int height) {
        mHeight = height;
    }

    public int getHeight() {
        return mHeight;
    }
}