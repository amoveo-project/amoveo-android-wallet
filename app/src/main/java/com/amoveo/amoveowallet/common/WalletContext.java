package com.amoveo.amoveowallet.common;

import android.graphics.Bitmap;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.presenters.results.QRCodeResult;
import com.amoveo.amoveowallet.presenters.results.WalletContextUpdate;
import com.amoveo.amoveowallet.wallet.Credentials;
import com.amoveo.amoveowallet.wallet.ECKeyPair;
import com.amoveo.amoveowallet.wallet.Wallet;
import org.greenrobot.eventbus.EventBus;

import java.util.*;

import static java.util.Collections.sort;

public enum WalletContext {
    WALLET;
    private static final String TAG = "com.amoveo.amoveowallet.common.WalletContext";

    private Wallet mWallet;
    private ECKeyPair mEcKeyPair;
    private Bitmap mQRCodeAddress;

    private Map<String, TransactionInfo> mTransactionInfos = new HashMap<>();
    private List<TransactionInfo> mPendingTransactionInfo = new LinkedList<>();
    private String mBalance;

    private final EventBus mEventBus = EventBus.getDefault();

    private String mMnemonic;
    private boolean isEmptyPassword;
    private int mHeight;

    public void setCredentials(Credentials credentials) {
        mEcKeyPair = credentials.getEcKeyPair();
        mMnemonic = credentials.getMnemonic();
    }

    public String getAddress() {
        return null == mWallet ? "" : mWallet.getAddress();
    }

    public ECKeyPair getEcKeyPair() {
        return mEcKeyPair;
    }

    public String getPrivateKey() {
        return mEcKeyPair.getPrivateKey().toString(16);
    }

    public String getMnemonic() {
        return mMnemonic;
    }

    public void setQRCodeAddress(QRCodeResult result) {
        mQRCodeAddress = result.getResult();
        post(result);
    }

    public Bitmap getQRCodeAddress() {
        return mQRCodeAddress;
    }

    public void dropWallet() {
        mWallet = null;
        mPendingTransactionInfo.clear();
        mTransactionInfos.clear();
        mBalance = null;
    }

    public void setPendingTransactionInfo(List<TransactionInfo> transactionInfos) {
        this.mPendingTransactionInfo = transactionInfos;

        updateBalance();
    }

    public List<TransactionInfo> getPendingTransactionInfo() {
        return mPendingTransactionInfo;
    }

    public void setTransactionHistory(List<TransactionInfo> transactionInfos) {
        for (TransactionInfo transactionInfo : transactionInfos) {
            this.mTransactionInfos.put(transactionInfo.getHash(), transactionInfo);
        }

        updateBalance();
    }

    public List<TransactionInfo> getTransactionInfos() {
        List<TransactionInfo> transactionInfos = new ArrayList<>(mTransactionInfos.values());
        transactionInfos.addAll(mPendingTransactionInfo);

        sort(transactionInfos, (transactionInfo0, transactionInfo1) -> Long.compare(transactionInfo1.getTimeStamp(), transactionInfo0.getTimeStamp()));

        return transactionInfos;
    }

    public void setBalance(String balance) {
        this.mBalance = balance;
        updateBalance();
    }

    public String getBalance() {
        return null == mBalance ? "0" : mBalance;
    }

    public void updateBalance() {
        post(new WalletContextUpdate());
    }

    private void post(IEvent event) {
        mEventBus.post(event);
    }

    public void clearCredentials() {
        mEcKeyPair = null;
    }

    public void setWallet(Wallet wallet) {
        this.mWallet = wallet;
    }

    public Wallet getWallet() {
        return mWallet;
    }

    public boolean isEmptyPassword() {
        return isEmptyPassword;
    }

    public void setEmptyPassword(boolean emptyPassword) {
        isEmptyPassword = emptyPassword;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getHeight() {
        return mHeight;
    }
}