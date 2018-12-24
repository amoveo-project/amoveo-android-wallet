package com.amoveo.amoveowallet.api.requests;

import org.json.JSONArray;

public class PendingTransactionsRequest extends APIRequest<JSONArray> {
    @Override
    public JSONArray getBody() {
        JSONArray request = new JSONArray();

        request.put("txs");

        return request;
    }
}