package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class EmptyResult<T> extends ExceptionResult<T> {
    public EmptyResult(Exception exception) {
        super(exception);
    }

    public EmptyResult() {
        super((T) null);
    }

    @Override
    public boolean isSuccess() {
        return null == getResult();
    }
}
