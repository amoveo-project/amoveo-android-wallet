package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.ProposalRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.api.results.items.TransactionInfoWrap;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;
import com.amoveo.amoveowallet.utils.HLog;
import org.json.JSONArray;

public class SendProposalOperation extends JsonArrayAPIOperation<TransactionInfoWrap, JSONArray> {
    private final OperationListener<TransactionInfoWrap> mOperationListener;

    private SendProposalOperation(ProposalRequest balanceRequest, OperationListener<TransactionInfoWrap> operationListener) {
        super(balanceRequest);
        this.mOperationListener = operationListener;
    }

    @Override
    protected void onSuccess(APIResult<TransactionInfoWrap> result) {
        HLog.debug(TAG, "onSuccess " + result.getResult());
        mOperationListener.onSuccess(result.getResult());
    }

    @Override
    protected void onFailure(APIResult<TransactionInfoWrap> result) {
        HLog.error(TAG, "onFailure", result.getException());
        mOperationListener.onFailure(new APIResult(result.getException()));
    }

    @Override
    protected APIResult<TransactionInfoWrap> buildResult(JSONArray response) {
        try {
            return new APIResult<>(null == response || 0 == response.length() || !"ok".equals(response.getString(0)) ? null : new TransactionInfoWrap((JSONArray) response.get(1)));
        } catch (Exception e) {
            return new APIResult<>(e);
        }
    }

    public static void sendProposal(boolean isNew, String amount, String address, OperationListener<TransactionInfoWrap> operationListener) {
        putRequest(new SendProposalOperation(new ProposalRequest(isNew, amount, address), operationListener).buildRequest());
    }
}