package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.BalanceRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.api.results.items.Balance;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;
import org.json.JSONArray;

import static com.amoveo.amoveowallet.utils.Utils.GSON;

public class ObtainBalanceOperation extends JsonArrayAPIOperation<Balance, JSONArray> {
    private OperationListener<Balance> mListener;

    private ObtainBalanceOperation(BalanceRequest balanceRequest, OperationListener<Balance> listener) {
        super(balanceRequest);
        mListener = listener;
    }

    @Override
    protected void onSuccess(APIResult<Balance> result) {
        mListener.onSuccess(result.getResult());
    }

    @Override
    protected void onFailure(APIResult<Balance> result) {
        mListener.onFailure(result);
    }

    protected final APIResult<Balance> buildResult(JSONArray response) {
        try {
            return new APIResult<>(null == response || 0 == response.length() ? null : "empty".equals(response.getString(1)) ? new Balance() : new Balance(GSON.fromJson(response.getString(1), String[].class)));
        } catch (Exception e) {
            return new APIResult<>(e);
        }
    }

    public static void obtainBalance(String address, OperationListener<Balance> listener) {
        putRequest(new ObtainBalanceOperation(new BalanceRequest(address), listener).buildRequest());
    }
}