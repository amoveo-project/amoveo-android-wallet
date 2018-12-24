package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.common.IEvent;

public class ConnectionResult extends EmptyResult implements IEvent {
    public ConnectionResult() {
        this(null);
    }

    public ConnectionResult(Exception exception) {
        super(exception);
    }

    public boolean isSuccess() {
        return null == getException();
    }
}
