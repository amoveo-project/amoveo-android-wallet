package com.amoveo.amoveowallet.view;

import com.amoveo.amoveowallet.api.results.items.TransactionInfo;

public interface ITransactionInfoView extends IBaseView, IConnectionView {
    void showTransactionInfo(TransactionInfo transactionInfo);
}