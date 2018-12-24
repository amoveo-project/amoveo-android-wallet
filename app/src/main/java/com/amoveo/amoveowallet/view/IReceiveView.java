package com.amoveo.amoveowallet.view;

import android.graphics.Bitmap;

public interface IReceiveView extends IBaseNavigableView {
    void setAddress(String address);

    void showQRImage(Bitmap bitmap);
}
