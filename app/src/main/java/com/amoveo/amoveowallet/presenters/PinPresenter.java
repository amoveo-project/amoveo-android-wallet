package com.amoveo.amoveowallet.presenters;

import android.os.Bundle;
import android.os.CountDownTimer;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.view.IPinView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public abstract class PinPresenter<V extends IPinView> extends BasePresenter<V> {
    static final int MAX_PIN = 4;
    int mIndex = 0;
    byte[] mPin = new byte[MAX_PIN];
    private CountDownTimer mCountDownTimer;

    public void onButton0Click() {
        onButtonClick((byte) 0);
    }

    public void onButton1Click() {
        onButtonClick((byte) 1);
    }

    public void onButton2Click() {
        onButtonClick((byte) 2);
    }

    public void onButton3Click() {
        onButtonClick((byte) 3);
    }

    public void onButton4Click() {
        onButtonClick((byte) 4);
    }

    public void onButton5Click() {
        onButtonClick((byte) 5);
    }

    public void onButton6Click() {
        onButtonClick((byte) 6);
    }

    public void onButton7Click() {
        onButtonClick((byte) 7);
    }

    public void onButton8Click() {
        onButtonClick((byte) 8);
    }

    public void onButton9Click() {
        onButtonClick((byte) 9);
    }

    public void onButtonBackClick() {
        if (0 > --mIndex) {
            mIndex = 0;
        }
        viewPin(R.drawable.oval_yellow_shadow);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (0 < SETTINGS.getPinTime()) {
            mCountDownTimer = new CountDownTimer(SETTINGS.getPinTime(), 1000) {
                public void onTick(long millisUntilFinished) {
                    mView.showWaitTitle(SETTINGS.getPinTime());
                }

                public void onFinish() {
                    mView.showDefaultTitle();
                }
            };

            mCountDownTimer.start();
        } else {
            mView.showDefaultTitle();
        }
    }

    private synchronized void onButtonClick(byte i) {
        if (0 >= SETTINGS.getPinTime() && mIndex < MAX_PIN) {
            mPin[mIndex] = i;
            viewPin(R.drawable.oval_yellow);
            if (MAX_PIN == ++mIndex) {
                onPinEntered();
            }
        }
    }

    private void viewPin(int resourceId) {
        switch (mIndex) {
            case 0: {
                mView.setPin0Background(resourceId);
                break;
            }
            case 1: {
                mView.setPin1Background(resourceId);
                break;
            }
            case 2: {
                mView.setPin2Background(resourceId);
                break;
            }
            case 3: {
                mView.setPin3Background(resourceId);
                break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        cancelCountDownTimer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cancelCountDownTimer();
    }

    private void cancelCountDownTimer() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
    }

    protected abstract void onPinEntered();

    public abstract void onButtonSkipClick();
}
