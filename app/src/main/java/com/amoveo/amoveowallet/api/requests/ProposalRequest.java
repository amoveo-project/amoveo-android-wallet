package com.amoveo.amoveowallet.api.requests;

import org.json.JSONArray;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class ProposalRequest extends APIRequest<JSONArray> {
    private final JSONArray mProposal;

    public ProposalRequest(boolean isNew, String amount, String address) {
        mProposal = new JSONArray();

        mProposal.put(isNew ? "create_account_tx" : "spend_tx");
        mProposal.put(Integer.valueOf(amount));
        mProposal.put(isNew ? 152050 : 61707);
        mProposal.put(WALLET.getAddress());
        mProposal.put(address);
    }

    @Override
    public JSONArray getBody() {
        return mProposal;
    }
}