package com.amoveo.amoveowallet.models;

import android.support.annotation.Nullable;

public enum PrivacyLevel {
    LITE(0, 1 << 12, 6),
    ABOVE_LITE(0, 1 << 13, 5),
    BELOW_MEDIUM(0, 1 << 14, 4),
    MEDIUM(0, 1 << 15, 3),
    ABOVE_MEDIUM(0, 1 << 16, 2),
    BELOW_STANDARD(0, 1 << 17, 1),
    STANDARD(0, 1 << 18, 1),
    ABOVE_STANDARD(0, 1 << 19, 1),
    PARANOID(0, 1 << 20, 1);

    public static final PrivacyLevel PRIVACY_LEVEL_DEFAULT = MEDIUM;

    private final int mValue;
    private final int mN;
    private final int mP;

    PrivacyLevel(int value, int n, int p) {
        this.mValue = value;
        this.mN = n;
        this.mP = p;
    }

    public int getValue() {
        return mValue;
    }

    public int getN() {
        return mN;
    }

    public int getP() {
        return mP;
    }

    public static PrivacyLevel forValueNonNull(int value) {
        final PrivacyLevel privacyLevel = forValue(value);
        return null == privacyLevel ? PRIVACY_LEVEL_DEFAULT : privacyLevel;
    }

    @Nullable
    public static PrivacyLevel forValue(int value) {
        for (PrivacyLevel privacyLevel : values()) {
            if (privacyLevel.getValue() == value) {
                return privacyLevel;
            }
        }

        return null;
    }
}
