package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.engine.results.ExceptionResult;
import com.amoveo.amoveowallet.wallet.Credentials;

public class DecryptResult extends ExceptionResult<Credentials> {
    public DecryptResult(Credentials credentials) {
        super(credentials);
    }

    public DecryptResult(Exception exception) {
        super(exception);
    }
}