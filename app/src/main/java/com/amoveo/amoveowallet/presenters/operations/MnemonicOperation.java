package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.fragments.ShowMnemonicFragment;
import com.amoveo.amoveowallet.fragments.SplashFragment;
import com.amoveo.amoveowallet.presenters.results.MnemonicResult;
import com.amoveo.amoveowallet.utils.HLog;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.wallet.BIP39Utils.generateMnemonic;

public class MnemonicOperation extends EngineOperation<MnemonicResult> {
    private final RootActivity mActivity;

    private MnemonicOperation(RootActivity activity) {
        this.mActivity = activity;
    }

    @Override
    protected MnemonicResult execute() {
        return new MnemonicResult(generateMnemonic(SETTINGS.getLanguage(), SETTINGS.getMnemonicLength()));
    }

    @Override
    protected void onStart() {
        mActivity.show(SplashFragment.newInstance());
    }

    @Override
    protected void onSuccess(MnemonicResult result) {
        HLog.debug(TAG, "onSuccess " + result.getResult());
        mActivity.show(ShowMnemonicFragment.newInstance(result.getResult()));
    }

    @Override
    protected void onFailure(MnemonicResult result) {
        HLog.error(TAG, "onFailure");
    }

    public static void execute(RootActivity activity) {
        ENGINE.submit(new MnemonicOperation(activity));
    }
}