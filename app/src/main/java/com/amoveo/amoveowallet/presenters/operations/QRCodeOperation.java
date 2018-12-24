package com.amoveo.amoveowallet.presenters.operations;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.presenters.results.QRCodeResult;
import com.amoveo.amoveowallet.utils.HLog;
import com.google.zxing.WriterException;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;

public class QRCodeOperation extends EngineOperation<QRCodeResult> {
    private final RootActivity mActivity;

    private QRCodeOperation(RootActivity activity) {
        this.mActivity = activity;
    }

    @Override
    protected QRCodeResult execute() {
        try {
            return new QRCodeResult(new QRGEncoder(WALLET.getAddress(), null, QRGContents.Type.TEXT, (int) mActivity.getResources().getDimension(R.dimen.size560)).encodeAsBitmap());
        } catch (WriterException e) {
            return new QRCodeResult(e);
        }
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onSuccess(QRCodeResult result) {
        HLog.debug(TAG, "onSuccess");
        WALLET.setQRCodeAddress(result);
    }

    @Override
    protected void onFailure(QRCodeResult result) {
        HLog.error(TAG, "onFailure", result.getException());
    }

    public static void execute(RootActivity activity) {
        ENGINE.submit(new QRCodeOperation(activity));
    }
}
