package com.amoveo.amoveowallet.engine.results;

public abstract class Result<T> {
    private T mResult;

    protected Result(T result) {
        mResult = result;
    }

    public T getResult() {
        return mResult;
    }

    public boolean isSuccess() {
        return null != mResult;
    }
}