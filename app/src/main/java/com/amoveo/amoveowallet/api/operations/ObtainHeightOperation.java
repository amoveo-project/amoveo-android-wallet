package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.requests.APIRequest;
import com.amoveo.amoveowallet.api.requests.HeightRequest;
import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.api.results.items.Height;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.utils.HLog;
import org.json.JSONArray;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class ObtainHeightOperation extends JsonArrayAPIOperation<Height, JSONArray> {

    private ObtainHeightOperation(APIRequest<JSONArray> request) {
        super(request);
    }

    @Override
    protected void onSuccess(APIResult<Height> result) {
        WALLET.setHeight(result.getResult().getHeight());
    }

    @Override
    protected void onFailure(APIResult<Height> result) {
        HLog.error(TAG, "onFailure", result.getException());
        SETTINGS.notifyConnection(new ConnectionResult(result.getException()));
    }

    @Override
    protected APIResult<Height> buildResult(JSONArray response) {
        try {
            return new APIResult<>(null == response || 0 == response.length() ? null : new Height((Integer) response.get(1)));
        } catch (Exception e) {
            return new APIResult<>(e);
        }
    }

    public static void obtainHeight() {
        putRequest(new ObtainHeightOperation(new HeightRequest()).buildRequest());
    }
}