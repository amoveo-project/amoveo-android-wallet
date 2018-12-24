package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.TransactionsRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.presenters.listeners.StartedOperationListener;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.utils.HLog;
import org.json.JSONArray;
import org.json.JSONException;

public class SendTransactionOperation extends JsonArrayAPIOperation<JSONArray, JSONArray> {
    private final StartedOperationListener<String> mOperationListener;

    private SendTransactionOperation(TransactionsRequest request, StartedOperationListener<String> operationListener) {
        super(request);
        this.mOperationListener = operationListener;
    }

    @Override
    protected void onSuccess(APIResult<JSONArray> result) {
        HLog.debug(TAG, "onSuccess " + result.getResult());

        try {
            mOperationListener.onSuccess( "Transaction sent! Tx-id: " + result.getResult().getString(1));
        } catch (JSONException e) {
            mOperationListener.onFailure(new ConnectionResult(e));
        }
    }

    @Override
    protected void onFailure(APIResult<JSONArray> result) {
        HLog.error(TAG, "onFailure", result.getException());
        mOperationListener.onFailure(result);
    }

    protected final APIResult<JSONArray> buildResult(JSONArray response) {
        try {
            return new APIResult<>(response);
        } catch (Exception e) {
            return new APIResult<>(e);
        }
    }

    public static void sendTransaction(TransactionsRequest request, StartedOperationListener<String> listener) {
        putRequest(new SendTransactionOperation(request, listener).buildRequest());
    }
}