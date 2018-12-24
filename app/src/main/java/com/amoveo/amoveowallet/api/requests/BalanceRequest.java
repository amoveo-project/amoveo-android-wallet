package com.amoveo.amoveowallet.api.requests;

import org.json.JSONArray;

public class BalanceRequest extends APIRequest<JSONArray> {
    private final String mAddress;

    public BalanceRequest(String address) {
        mAddress = address;
    }

    @Override
    public JSONArray getBody() {
        JSONArray request = new JSONArray();

        request.put("account");
        request.put(mAddress);

        return request;
    }
}