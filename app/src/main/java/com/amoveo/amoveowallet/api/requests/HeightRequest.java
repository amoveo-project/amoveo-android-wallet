package com.amoveo.amoveowallet.api.requests;

import org.json.JSONArray;

public class HeightRequest extends APIRequest<JSONArray> {
    @Override
    public JSONArray getBody() {
        JSONArray request = new JSONArray();

        request.put("height");

        return request;
    }
}