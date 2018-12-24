package com.amoveo.amoveowallet.presenters.listeners;

import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public interface OperationListener<R> {
    void onStart();

    void onSuccess(R result);

    void onFailure(ExceptionResult result);
}