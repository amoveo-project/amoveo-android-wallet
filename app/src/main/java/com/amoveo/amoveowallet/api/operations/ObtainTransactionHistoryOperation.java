package com.amoveo.amoveowallet.api.operations;

import com.amoveo.amoveowallet.api.results.APIResult;
import com.amoveo.amoveowallet.api.results.items.TransactionHistory;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.utils.HLog;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.utils.Utils.GSON;

public class ObtainTransactionHistoryOperation extends StringAPIOperation<TransactionHistory>  {
    private final String mAddress;

    private ObtainTransactionHistoryOperation(String address) {
        this.mAddress = address;
    }

    @Override
    protected String getUrl() {
        return "https://amoveo.exan.tech/explorer/api/v1/txlist?limit=30&address=".concat(mAddress);
    }

    @Override
    protected APIResult<TransactionHistory> buildResult(String response) {
        return new APIResult<>(GSON.fromJson(response, TransactionHistory.class));
    }

    @Override
    protected void onSuccess(APIResult<TransactionHistory> result) {
        WALLET.setTransactionHistory(result.getResult().result);

    }
    @Override
    protected void onFailure(APIResult<TransactionHistory> result) {
        HLog.error(TAG, "onFailure", result.getException());
        SETTINGS.notifyConnection(new ConnectionResult(result.getException()));
    }

    public static void obtainTransactionHistory(String address) {
        putRequest(new ObtainTransactionHistoryOperation(address).buildRequest());
    }
}