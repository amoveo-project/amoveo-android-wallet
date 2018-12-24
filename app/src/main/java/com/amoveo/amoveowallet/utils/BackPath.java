package com.amoveo.amoveowallet.utils;

public enum BackPath {
    BACK_PATH;

    public static final int NAVIGATION_RECEIVE = 0;
    public static final int NAVIGATION_SEND = 1;
    public static final int NAVIGATION_DASHBOARD = 2;
    public static final int NAVIGATION_SETTING = 3;

    private int mScreen = NAVIGATION_DASHBOARD;
    private int mSubScreen = 0;
    private int mMessageText = -1;

    public int getScreen() {
        return mScreen;
    }

    public void setSreen(int screen) {
        mScreen = screen;
    }

    public int getSubScreen() {
        return mSubScreen;
    }

    public void setSubscreen(int subscreen) {
        this.mSubScreen = subscreen;
    }

    public int getMessageText() {
        return mMessageText;
    }

    public void setMessageText(int messageText) {
        mMessageText = messageText;
    }

    public boolean hasMessage() {
        return -1 < mMessageText;
    }

    public void dropMessage() {
        mMessageText = -1;
    }
}
