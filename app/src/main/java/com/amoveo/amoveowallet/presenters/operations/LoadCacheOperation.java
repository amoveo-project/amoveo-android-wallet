package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.common.CacheBean;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.presenters.results.CacheResult;
import com.amoveo.amoveowallet.utils.HLog;

import java.io.FileReader;
import java.io.IOException;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.utils.Utils.GSON;

public class LoadCacheOperation extends EngineOperation<CacheResult> {
    private LoadCacheOperation(RootActivity activity) {
        super();
    }

    @Override
    protected CacheResult execute() {
        synchronized (SETTINGS) {
            try {
                return new CacheResult(SETTINGS.getCacheFile().exists() && SETTINGS.getCacheFile().canRead() ? GSON.fromJson(new FileReader(SETTINGS.getCacheFile()), CacheBean.class) : new CacheBean());
            } catch (IOException e) {
                HLog.error(TAG, "cacheBean", e);
                return new CacheResult(e);
            }
        }
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onSuccess(CacheResult result) {
        result.getResult().resolve();
    }

    @Override
    protected void onFailure(CacheResult result) {
        HLog.error(TAG, "onFailure");
    }

    public static void loadCache(RootActivity activity) {
        ENGINE.submit(new LoadCacheOperation(activity));
    }
}
