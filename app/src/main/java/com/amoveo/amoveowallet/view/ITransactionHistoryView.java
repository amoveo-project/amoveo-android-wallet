package com.amoveo.amoveowallet.view;

import com.amoveo.amoveowallet.api.results.items.TransactionInfo;

import java.util.List;

public interface ITransactionHistoryView extends IBaseView, IProgressView, IConnectionView {
    void showTransactionInfos(List<TransactionInfo> transactionInfos);
}
