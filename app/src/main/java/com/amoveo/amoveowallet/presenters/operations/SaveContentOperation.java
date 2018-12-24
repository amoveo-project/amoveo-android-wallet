package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.common.ContentBean;
import com.amoveo.amoveowallet.common.EncodedBean;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.presenters.results.EmptyResult;
import com.amoveo.amoveowallet.utils.HLog;

import java.io.FileWriter;

import static com.amoveo.amoveowallet.common.CryptoUtils.encode;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.utils.Utils.GSON;

public class SaveContentOperation extends EngineOperation<EmptyResult> {
    private static final String UNREADABLE = "";

    @Override
    protected EmptyResult execute() {
        synchronized (SETTINGS) {
            try {
                FileWriter fileWriter = new FileWriter(SETTINGS.getContentFile());
                fileWriter.write(GSON.toJson(new EncodedBean(null != WALLET.getWallet() ? encode(SETTINGS.getPin(), GSON.toJson(new ContentBean())) : UNREADABLE)));
                fileWriter.close();
            } catch (Exception e) {
                return new EmptyResult(e);
            }

            return new EmptyResult();
        }
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onSuccess(EmptyResult result) {
        HLog.debug(TAG, "onSuccess");
    }

    @Override
    protected void onFailure(EmptyResult result) {
        HLog.error(TAG, "onFailure", result.getException());
    }

    public static void saveContent() {
        ENGINE.submit(new SaveContentOperation());
    }
}
