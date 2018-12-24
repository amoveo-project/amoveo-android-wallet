package com.amoveo.amoveowallet.api.results.items;

import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class TransactionInfo {
    int nonce;
    String type;
    long timestamp;
    int blocknumber;
    String from;
    String to;
    String amount;
    //                  "extra": null,
    int transactionindex;
    String hash;
    String fee;

    boolean isPending = false;

    TransactionInfo() {
    }

    public TransactionInfo(String type, String from, int nonce, String fee, String to, String amount) {
        this.type = type;
        this.from = from;
        this.nonce = nonce;
        this.fee = fee;
        this.to = to;
        this.amount = amount;

        this.timestamp = Long.MAX_VALUE;
        this.isPending = true;
    }

    public int getNonce() {
        return nonce;
    }

    public int getBlockNumber() {
        return blocknumber;
    }

    public String getHash() {
        return hash;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getFee() {
        return fee;
    }

    public String getFrom() {
        return from;
    }

    public CharSequence getTo() {
        return to;
    }

    public boolean isPending() {
        return isPending;
    }

    public boolean isSend() {
        return isEmpty(from) ? isEmpty(to) ? false : !to.equals(WALLET.getAddress()) : from.equals(WALLET.getAddress());
    }
}
