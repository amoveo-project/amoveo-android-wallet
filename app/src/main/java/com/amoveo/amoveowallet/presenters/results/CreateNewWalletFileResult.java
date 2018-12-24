package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.engine.results.ExceptionResult;
import com.amoveo.amoveowallet.wallet.Wallet;

public class CreateNewWalletFileResult extends ExceptionResult<Wallet> {
    public CreateNewWalletFileResult(Wallet wallet) {
        super(wallet);
    }

    public CreateNewWalletFileResult(Exception exception) {
        super(exception);
    }
}
