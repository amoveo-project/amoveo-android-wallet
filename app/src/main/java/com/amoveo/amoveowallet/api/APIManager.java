package com.amoveo.amoveowallet.api;

import android.content.Context;
import android.support.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public enum APIManager {
    API;
    private RequestQueue mQueue;

    public void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public void putRequest(@NonNull final Request request) {
        mQueue.add(request);
    }
}