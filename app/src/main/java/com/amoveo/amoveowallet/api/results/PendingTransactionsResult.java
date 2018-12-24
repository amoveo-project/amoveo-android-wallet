package com.amoveo.amoveowallet.api.results;

import com.amoveo.amoveowallet.api.results.items.TransactionInfo;

import java.util.List;

public class PendingTransactionsResult extends APIResult<List<TransactionInfo>>  {
    public PendingTransactionsResult(Exception exception) {
        super(exception);
    }

    public PendingTransactionsResult(List<TransactionInfo> result) {
        super(result);
    }
}