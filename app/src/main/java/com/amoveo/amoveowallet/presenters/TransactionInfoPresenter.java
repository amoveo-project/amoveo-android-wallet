package com.amoveo.amoveowallet.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.widget.Toast.makeText;

public class TransactionInfoPresenter extends PendingTransactionInfoPresenter {

    public TransactionInfoPresenter(TransactionInfo transactionInfo) {
        super(transactionInfo);
    }

    public void onCopyHash() {
        ((ClipboardManager) mActivity.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", mTransactionInfo.getHash()));
        makeText(mActivity, R.string.txhash_clipboard, Toast.LENGTH_SHORT).show();
    }

    public void onGotoHash() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://explorer.veopool.pw/?input=" + mTransactionInfo.getHash()));
        mActivity.startActivity(intent);
    }
}