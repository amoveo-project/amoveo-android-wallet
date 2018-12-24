package com.amoveo.amoveowallet.presenters.operations;

import android.content.Context;
import android.os.Vibrator;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.common.ContentBean;
import com.amoveo.amoveowallet.common.EncodedBean;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.fragments.SplashFragment;
import com.amoveo.amoveowallet.presenters.results.ContentResult;

import java.io.FileReader;

import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.common.CryptoUtils.decode;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.utils.Utils.GSON;

public abstract class CustomContentOperation extends EngineOperation<ContentResult> {
    protected final RootActivity mActivity;

    private final boolean needNotify;

    CustomContentOperation(RootActivity activity, boolean needNotify) {
        mActivity = activity;
        this.needNotify = needNotify;
    }

    @Override
    protected ContentResult execute() {
        synchronized (SETTINGS) {
            try {
                String content = GSON.fromJson(new FileReader(SETTINGS.getContentFile()), EncodedBean.class).getContent();

                return isEmpty(content) ? new ContentResult(new Exception("The wallet has been deleted")) : new ContentResult(GSON.fromJson(decode(getPin(), content), ContentBean.class));
            } catch (Exception e) {
                return new ContentResult(e);
            }
        }
    }

    void notifyFail() {
        if (needNotify) {
            ((Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(400);
            SETTINGS.notifyPinAttempt(false);
        }
    }

    abstract byte[] getPin();

    @Override
    protected void onStart() {
        mActivity.show(SplashFragment.newInstance());
    }
}
