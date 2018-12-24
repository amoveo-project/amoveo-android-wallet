package com.amoveo.amoveowallet.api.results;

import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class APIResult<T> extends ExceptionResult<T> {
    public APIResult(Exception exception) {
        super(exception);
    }

    public APIResult(T result) {
        super(result);
    }

    @Override
    public boolean isSuccess() {
        return null == getException();
    }
}