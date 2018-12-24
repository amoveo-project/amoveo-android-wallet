package com.amoveo.amoveowallet.view;

import com.amoveo.amoveowallet.api.results.items.TransactionInfo;

import java.util.List;

public interface IBalanceView extends IBaseView, IConnectionView {
    void showBalance(String balance);

    void showTransactions(List<TransactionInfo> transactionInfos);
}
