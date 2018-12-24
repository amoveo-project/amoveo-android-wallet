package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.engine.operations.Operation;
import com.amoveo.amoveowallet.utils.HLog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import static com.amoveo.amoveowallet.api.APIManager.API;

public abstract class APIOperation<R extends APIResult, T> extends Operation<R> implements Response.Listener<T>, Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onFailure(buildResult(volleyError));
    }

    @Override
    public void onResponse(T response) {
        HLog.debug(TAG, "onResponse " + response);
        R result = buildResult(response);
        if (result.isSuccess()) {
            onSuccess(result);
        } else {
            onFailure(result);
        }
    }

    protected abstract String getUrl();

    protected abstract R buildResult(VolleyError volleyError);

    protected abstract R buildResult(T response);

    public abstract Request buildRequest();

    @Override
    protected void onStart() {
    }

    static void putRequest(Request request) {
        API.putRequest(request);
    }
}