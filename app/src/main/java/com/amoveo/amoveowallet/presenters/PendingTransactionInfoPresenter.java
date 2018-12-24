package com.amoveo.amoveowallet.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.view.ITransactionInfoView;
import org.greenrobot.eventbus.Subscribe;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.widget.Toast.makeText;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class PendingTransactionInfoPresenter extends BasePresenter<ITransactionInfoView> {
    protected TransactionInfo mTransactionInfo;

    public PendingTransactionInfoPresenter(TransactionInfo transactionInfo) {
        this.mTransactionInfo = transactionInfo;
    }

    @Subscribe
    protected void onEvent(final ConnectionResult event) {
        mView.notifyConnection(event.isSuccess());
    }

    @Override
    public void onResume() {
        mView.showTransactionInfo(mTransactionInfo);
        mView.notifyConnection(SETTINGS.isConnected());
    }

    public void onCopyAddress(boolean send) {
        ((ClipboardManager) mActivity.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", send ? mTransactionInfo.getTo() : mTransactionInfo.getFrom()));
        makeText(mActivity, R.string.address_clipboard, Toast.LENGTH_SHORT).show();
    }
}
