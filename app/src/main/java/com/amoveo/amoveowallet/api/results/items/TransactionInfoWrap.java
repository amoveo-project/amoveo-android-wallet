package com.amoveo.amoveowallet.api.results.items;

import com.amoveo.amoveowallet.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;

public class TransactionInfoWrap {
    private final JSONArray mBody;
    private final TransactionInfo mTransactionInfo;

    public TransactionInfoWrap(JSONArray body) throws JSONException {
        this.mBody = body;
        this.mTransactionInfo = Utils.getTransactionInfo(mBody);
    }

    public String getType() throws JSONException {
        return mBody.getString(0);
    }

    public String getFrom() throws JSONException {
        return mBody.getString(1);
    }

    public long getNonce() throws JSONException {
        return mBody.getLong(2);
    }

    public long getFee() throws JSONException {
        return mBody.getLong(3);
    }

    public String getTo() throws JSONException {
        return mBody.getString(4);
    }

    public long getAmount() throws JSONException {
        return mBody.getLong(5);
    }

    public JSONArray getBody() {
        return mBody;
    }

    public TransactionInfo getTransactionInfo() {
        return mTransactionInfo;
    }
}