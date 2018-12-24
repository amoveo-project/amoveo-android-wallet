package com.amoveo.amoveowallet.presenters.results;

import android.graphics.Bitmap;
import com.amoveo.amoveowallet.common.IEvent;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class QRCodeResult extends ExceptionResult<Bitmap> implements IEvent {
    public QRCodeResult(Bitmap result) {
        super(result);
    }

    public QRCodeResult(Exception exception) {
        super(exception);
    }
}
