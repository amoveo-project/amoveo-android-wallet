package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.utils.HLog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public abstract class StringAPIOperation<R> extends APIOperation<APIResult<R>, String> implements Response.Listener<String>{
    @Override
    protected APIResult<R> buildResult(VolleyError volleyError) {
        return new APIResult<>(volleyError);
    }

    @Override
    protected abstract APIResult<R> buildResult(String response) ;

    public final Request buildRequest() {
        String url = getUrl();
        HLog.debug(TAG, "buildRequest " + url);
        return new StringRequest(url, this, this);
    }


    @Override
    protected abstract void onSuccess(APIResult<R> result);

    @Override
    protected abstract void onFailure(APIResult<R> result);
}