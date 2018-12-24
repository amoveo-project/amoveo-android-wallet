package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.APIRequest;
import com.amoveo.amoveowallet.api.requests.PendingTransactionsRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.api.results.PendingTransactionsResult;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.utils.HLog;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.utils.Utils.getTransactionInfo;

public class ObtainPendingTransactionsOperation extends JsonArrayAPIOperation<PendingTransactionsResult, JSONArray> {
    private ObtainPendingTransactionsOperation(APIRequest<JSONArray> request) {
        super(request);
    }

    @Override
    protected APIResult<PendingTransactionsResult> buildResult(JSONArray response) {
        try {
            return new APIResult<>(null == response || 0 == response.length() || !"ok".equals(response.getString(0)) ? null : new PendingTransactionsResult(getPendingTransactions((JSONArray) response.get(1))));
        } catch (Exception e) {
            return new APIResult<>(e);
        }
    }

    private List<TransactionInfo> getPendingTransactions(JSONArray json) throws JSONException {
        List<TransactionInfo> result = new LinkedList<>();

        for (int index = 1; index < json.length(); index++) {
            JSONArray wrap = json.getJSONArray(index);
            if (2 < wrap.length()) {
                JSONArray body = (JSONArray) wrap.get(1);
                if (("spend".equals(body.getString(0)) || "create_acc_tx".equals(body.getString(0)))
                        && 4 < body.length()
                        && (WALLET.getAddress().equals(body.getString(1)) || WALLET.getAddress().equals(body.getString(4)))) {
                    result.add(getTransactionInfo(body));
                }
            }
        }
        return result;
    }

    @Override
    protected void onSuccess(APIResult<PendingTransactionsResult> result) {
        WALLET.setPendingTransactionInfo(result.getResult().getResult());
    }

    @Override
    protected void onFailure(APIResult<PendingTransactionsResult> result) {
        HLog.error(TAG, "onFailure", result.getException());
        SETTINGS.notifyConnection(new ConnectionResult(result.getException()));
    }

    public static void obtainPendingTransactions() {
        putRequest(new ObtainPendingTransactionsOperation(new PendingTransactionsRequest()).buildRequest());
    }
}