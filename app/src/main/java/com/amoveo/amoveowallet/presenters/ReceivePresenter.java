package com.amoveo.amoveowallet.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.widget.Toast;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.results.QRCodeResult;
import com.amoveo.amoveowallet.view.IReceiveView;
import org.greenrobot.eventbus.Subscribe;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.widget.Toast.makeText;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class ReceivePresenter extends BasePresenter<IReceiveView> {
    @Subscribe
    protected void onEvent(final QRCodeResult event) {
        mView.showQRImage(WALLET.getQRCodeAddress());
    }

    @Override
    public void onResume() {
        mView.setAddress(WALLET.getAddress());
        mView.showQRImage(WALLET.getQRCodeAddress());
    }

    public void onCopyButtoon() {
        ((ClipboardManager) mActivity.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", WALLET.getAddress()));
        makeText(mActivity, R.string.wallet_clipboard, Toast.LENGTH_SHORT).show();
    }

    public void onReceive() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, WALLET.getAddress());
        intent.setType("text/plain");
        mActivity.startActivity(intent);
    }
}
