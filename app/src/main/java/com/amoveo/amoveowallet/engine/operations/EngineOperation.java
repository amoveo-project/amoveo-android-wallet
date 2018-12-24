package com.amoveo.amoveowallet.engine.operations;

import com.amoveo.amoveowallet.engine.results.Result;

public abstract class EngineOperation<R extends Result> extends Operation<R> implements Runnable {
    @Override
    public final void run() {
        onStart();
        R response = execute();
        if (response.isSuccess()) {
            onSuccess(response);
        } else {
            onFailure(response);
        }
    }

    protected abstract R execute();
}