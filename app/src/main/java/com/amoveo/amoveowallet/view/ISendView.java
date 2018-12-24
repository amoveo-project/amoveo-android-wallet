package com.amoveo.amoveowallet.view;

public interface ISendView extends IBaseNavigableView, IProgressView, IConnectionView {
    void setAddress(String address, int selectionStart, int selectionEnd);

    void enableSendButton(boolean isEnable);

    void setAmount(String amount);

    void setFee(String fee);

    void showBalance(String balance);

    void clearScreen();

    void enableClearAddressButton(boolean isEmpty);

    void enableClearAmountButton(boolean isEmpty);

    void setErrorAddressHint(String hint);

    void setErrorAmountHint(String o);
}