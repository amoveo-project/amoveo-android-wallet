package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.engine.results.Result;

public class MnemonicResult extends Result<String> {
    public MnemonicResult(String mnemonic) {
        super(mnemonic);
    }
}
