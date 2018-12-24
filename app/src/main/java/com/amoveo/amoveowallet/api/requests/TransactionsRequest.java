package com.amoveo.amoveowallet.api.requests;

import com.amoveo.amoveowallet.api.results.items.TransactionInfoWrap;
import org.json.JSONArray;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.wallet.CommonKt.generateTx;

public class TransactionsRequest extends APIRequest<JSONArray>  {
    private final JSONArray mTransaction;

    public TransactionsRequest(TransactionInfoWrap response, Boolean isNew) {
        JSONArray transaction = new JSONArray();
        transaction.put("txs");

        JSONArray in1 = new JSONArray();
        in1.put(-6);

        JSONArray in2 = new JSONArray();
        in2.put("signed");
        in2.put(response.getBody());
        in2.put(generateTx(response, WALLET.getPrivateKey(), WALLET.getAddress(), isNew));
        JSONArray in3 = new JSONArray();
        in3.put(-6);
        in2.put(in3);
        in1.put(in2);
        transaction.put(in1);

        mTransaction = transaction;
    }

    @Override
    public JSONArray getBody() {
        return mTransaction;
    }
}