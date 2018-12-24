package com.amoveo.amoveowallet.common;

import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import org.greenrobot.eventbus.EventBus;

import java.io.File;

public enum Settings {
    SETTINGS;

    private static final String API_URL_DEFAULT = "http://amoveo.exan.tech:8080";
    private static final int MAX_PIN_ATTEMPT = 3;
    private static final int COLLAPSE_TIME_DELTA = 30000;
    private static final int PIN_DELTA_TIME = 30000;

    private final EventBus mEventBus = EventBus.getDefault();

    private byte[] mPin = new byte[]{0};
    private int mNetwork;
    private int mCurrency;
    private File mContentFile;

    private long mCollapseTime = 0;
    private long mPinTime = 0;
    private int mPinAttempt = MAX_PIN_ATTEMPT;
    private File mCacheFile;

    private int mPrivacy = 3;
    private int mLanguage = 0;
    private int mMnemonicSelection = 0;

    private String mDerivationPath = " m/44'/488'/0'/0/0";
    private boolean isConnected = true;

    private String mApiUrl = API_URL_DEFAULT;

    public int getNetwork() {
        return mNetwork;
    }

    public void setNetwork(int network) {
        this.mNetwork = network;
    }

    public int getPrivacy() {
        return mPrivacy;
    }

    public void setPrivacy(int privacy) {
        this.mPrivacy = privacy;
    }

    public int getCurrency() {
        return mCurrency;
    }

    public void setCurrency(int currency) {
        this.mCurrency = currency;
    }

    public void setContentFile(File contentFile) {
        this.mContentFile = contentFile;
    }

    public File getContentFile() {
        return mContentFile;
    }

    public byte[] getPin() {
        return mPin;
    }

    public void setPin(byte[] pin) {
        this.mPin = pin;
    }

    public void init() {
        mPin = new byte[]{0};
        mCurrency = 0;
    }

    private static final int N_PARANOID = 1 << 20;
    private static final int P_PARANOID = 1;

    private static final int N_ABOVE_STANDARD = 1 << 19;
    private static final int P_ABOVE_STANDARD = 1;

    private static final int N_STANDARD = 1 << 18;
    private static final int P_STANDARD = 1;

    private static final int N_BELOW_STANDARD = 1 << 17;
    private static final int P_BELOW_STANDARD = 1;

    private static final int N_ABOVE_MEDIUM = 1 << 16;
    private static final int P_ABOVE_MEDIUM = 2;

    private static final int N_MEDIUM = 1 << 15;
    private static final int P_MEDIUM = 3;

    private static final int N_BELOW_MEDIUM = 1 << 14;
    private static final int P_BELOW_MEDIUM = 4;

    private static final int N_ABOVE_LIGHT = 1 << 13;
    private static final int P_ABOVE_LIGHT = 5;

    private static final int N_LIGHT = 1 << 12;
    private static final int P_LIGHT = 6;

    public int getNParam() {
        switch (mPrivacy) {
            case 0: {
                return N_LIGHT;
            }
            case 1: {
                return N_ABOVE_LIGHT;
            }
            case 2: {
                return N_BELOW_MEDIUM;
            }
            case 3: {
                return N_MEDIUM;
            }
            case 4: {
                return N_ABOVE_MEDIUM;
            }
            case 5: {
                return N_BELOW_STANDARD;
            }
            case 6: {
                return N_STANDARD;
            }
            case 7: {
                return N_ABOVE_STANDARD;
            }
            default: {
                return N_PARANOID;
            }
        }
    }

    public int getPParam() {
        switch (mPrivacy) {
            case 0: {
                return P_LIGHT;
            }
            case 1: {
                return P_ABOVE_LIGHT;
            }
            case 2: {
                return P_BELOW_MEDIUM;
            }
            case 3: {
                return P_MEDIUM;
            }
            case 4: {
                return P_ABOVE_MEDIUM;
            }
            case 5: {
                return P_BELOW_STANDARD;
            }
            case 6: {
                return P_STANDARD;
            }
            case 7: {
                return P_ABOVE_STANDARD;
            }
            default: {
                return P_PARANOID;
            }
        }
    }

    public void setLanguage(int language) {
        mLanguage = language;
    }

    public int getLanguage() {
        return mLanguage;
    }

    public void setMnemonicSelection(int mnemonicLength) {
        mMnemonicSelection = mnemonicLength;
    }

    public int getMnemonicSelection() {
        return mMnemonicSelection;
    }

    public int getMnemonicLength() {
        switch (mMnemonicSelection) {
            case 0: {
                return 12;
            }
            case 1: {
                return 15;
            }
            case 2: {
                return 18;
            }
            case 3: {
                return 21;
            }
            case 4: {
                return 24;
            }
            default: {
                return 12;
            }
        }
    }

    public String getDerivationPath() {
        return mDerivationPath;
    }

    public File getCacheFile() {
        return mCacheFile;
    }

    public void setCacheFile(File mCacheFile) {
        this.mCacheFile = mCacheFile;
    }

    public int getPinAttempt() {
        return mPinAttempt;
    }

    public void notifyCollapseTime() {
        mCollapseTime = System.currentTimeMillis();
    }

    public void notifyPinAttempt(boolean attempt) {
        if (attempt) {
            mPinAttempt = MAX_PIN_ATTEMPT;
            mPinTime = 0;
        } else {
            mPinAttempt--;
            if (mPinAttempt < 1) {
                mPinTime = System.currentTimeMillis();
            }
        }
    }

    public long getPinTime() {
        return mPinAttempt < 1 ? mPinTime + PIN_DELTA_TIME - System.currentTimeMillis() : 0;
    }

    public void expand() {
        if (mCollapseTime + COLLAPSE_TIME_DELTA < System.currentTimeMillis()) {
            init();
        }
    }

    public void setPinAttempt(int pinAttempt) {
        mPinAttempt = pinAttempt;
    }

    public void setPinTime(long timeMillis) {
        mPinTime = timeMillis;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void notifyConnection(ConnectionResult connectionResult) {
        this.isConnected = connectionResult.isSuccess();
        mEventBus.post(connectionResult);
    }

    public String getApiUrl() {
        return mApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.mApiUrl = apiUrl;
    }

    public String getDefaultApiUrl() {
        return API_URL_DEFAULT;
    }
}