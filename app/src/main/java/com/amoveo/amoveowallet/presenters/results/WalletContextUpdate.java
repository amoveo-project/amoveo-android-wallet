package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.common.IEvent;

public class WalletContextUpdate extends EmptyResult implements IEvent {
    public WalletContextUpdate() {
        this(null);
    }

    private WalletContextUpdate(Exception exception) {
        super(exception);
    }

    public boolean isSuccess() {
        return null == getException();
    }
}
