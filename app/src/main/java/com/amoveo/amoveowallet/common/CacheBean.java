package com.amoveo.amoveowallet.common;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.utils.BackPath.BACK_PATH;

public class CacheBean {
    private int pinAttempt;
    private int screen;
    private int subScreen;

    public CacheBean() {
        pinAttempt = SETTINGS.getPinAttempt();
        screen = BACK_PATH.getScreen();
        subScreen = BACK_PATH.getSubScreen();
    }

    public void resolve(){
        SETTINGS.setPinAttempt(pinAttempt);
        SETTINGS.setPinTime(System.currentTimeMillis());

        BACK_PATH.setSreen(screen);
        BACK_PATH.setSubscreen(subScreen);
    }
}