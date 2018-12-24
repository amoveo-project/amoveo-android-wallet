package com.amoveo.amoveowallet.engine.operations;

import com.amoveo.amoveowallet.engine.results.Result;

public abstract class Operation<R extends Result> {
    protected static String TAG = "Operation";

    public Operation() {
        TAG = this.getClass().getSimpleName();
    }

    protected abstract void onStart();

    protected abstract void onSuccess(R result);

    protected abstract void onFailure(R result);
}