package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.APIRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public abstract class JsonArrayAPIOperation<V, O extends JSONArray> extends APIOperation<APIResult<V>, JSONArray> implements Response.Listener<JSONArray>{
    private final APIRequest<O> mRequest;

    JsonArrayAPIOperation(APIRequest<O> request) {
        this.mRequest = request;
    }

    @Override
    protected String getUrl() {
        return SETTINGS.getApiUrl();
    }

    @Override
    protected final APIResult<V> buildResult(VolleyError volleyError) {
        return new APIResult<>(volleyError);
    }

    @Override
    public Request buildRequest() {
        return new JsonArrayRequest(Request.Method.PUT, getUrl(), mRequest.getBody(), this, this);
    }
}