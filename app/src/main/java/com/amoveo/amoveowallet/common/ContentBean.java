package com.amoveo.amoveowallet.common;

import com.amoveo.amoveowallet.wallet.Wallet;

import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;

public class ContentBean {
    private int network;
    private int currency;
    private Wallet wallet;
    private String balance;
    private String apiUrl;
    private boolean emptyPassword;

    public ContentBean() {
        wallet = WALLET.getWallet();
        balance = WALLET.getBalance();
        emptyPassword = WALLET.isEmptyPassword();

        currency = SETTINGS.getCurrency();
        network = SETTINGS.getNetwork();
        apiUrl = SETTINGS.getApiUrl();
    }

    public void resolve() {
        WALLET.setWallet(wallet);
        WALLET.setBalance(balance);
        WALLET.setEmptyPassword(emptyPassword);

        WALLET.updateBalance();

        SETTINGS.setCurrency(currency);
        SETTINGS.setNetwork(network);
        SETTINGS.setApiUrl(isEmpty(apiUrl) ? SETTINGS.getDefaultApiUrl() : apiUrl);
    }
}