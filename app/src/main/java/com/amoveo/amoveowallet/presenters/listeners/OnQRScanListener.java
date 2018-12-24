package com.amoveo.amoveowallet.presenters.listeners;

import android.content.Intent;

public interface OnQRScanListener {
    boolean onActivityResult(int requestCode, int resultCode, Intent data);
}
