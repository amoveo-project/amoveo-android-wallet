package com.amoveo.amoveowallet.presenters;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.api.requests.TransactionsRequest;
import com.amoveo.amoveowallet.api.results.items.Balance;
import com.amoveo.amoveowallet.api.results.items.TransactionInfoWrap;
import com.amoveo.amoveowallet.dialogs.EnterPasswordDialog;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;
import com.amoveo.amoveowallet.presenters.listeners.StartedOperationListener;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;
import com.amoveo.amoveowallet.presenters.results.DecryptResult;
import com.amoveo.amoveowallet.presenters.results.WalletContextUpdate;
import com.amoveo.amoveowallet.utils.SimpleTextWatcher;
import com.amoveo.amoveowallet.view.ISendView;
import com.google.zxing.integration.android.IntentIntegrator;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.os.Looper.getMainLooper;
import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.api.operations.ObtainBalanceOperation.obtainBalance;
import static com.amoveo.amoveowallet.api.operations.SendProposalOperation.sendProposal;
import static com.amoveo.amoveowallet.api.operations.SendTransactionOperation.sendTransaction;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.presenters.operations.DecryptOperations.decrypt;
import static com.amoveo.amoveowallet.utils.Utils.*;
import static com.amoveo.amoveowallet.wallet.Wallet.EMPTY_PASSWORD;
import static com.google.zxing.integration.android.IntentIntegrator.parseActivityResult;
import static java.lang.String.valueOf;

public class SendPresenter extends BasePresenter<ISendView> {
    private static final long MAX_FEE = 152050L;
    private static final long HALF_FEE = 61707L;
    private static final int STRING_LENGTH = 32;

    private static Long sAmount;
    private static Long sFee = MAX_FEE;
    private static String sAddressTo;
    private static int sSelectionStart = -1;
    private static int sSelectionEnd = -1;
    private static boolean sSendMax;

    public void onSendButton() {
        if (null == WALLET.getEcKeyPair()) {
            if (WALLET.isEmptyPassword()) {
                decrypt(EMPTY_PASSWORD, getDecryptListener());
            } else {
                new EnterPasswordDialog(mActivity, getDecryptListener()).show();
            }
        } else {
            mView.showProgress();
            getDecryptListener().onSuccess(null);
        }
    }

    private OperationListener getDecryptListener() {
        return new OperationListener<DecryptResult>() {
            @Override
            public void onStart() {
                new Handler(getMainLooper()).post(() -> mView.showProgress());
            }

            @Override
            public void onSuccess(DecryptResult decryptResult) {
                obtainBalance(sAddressTo, new StartedFailedOperationListener<Balance>() {
                    @Override
                    public void onSuccess(Balance balance) {
                        sendProposal(balance.isEmpty(), valueOf(sAmount), sAddressTo, new StartedFailedOperationListener<TransactionInfoWrap>() {
                            @Override
                            public void onSuccess(TransactionInfoWrap transactionInfoWrap) {
                                sendTransaction(new TransactionsRequest(transactionInfoWrap, balance.isEmpty()), new StartedFailedOperationListener<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        mView.hideProgress();
                                        mView.showInfoDialog(result);
                                        mView.clearScreen();
                                        mView.enableSendButton(false);

                                        clear();

                                        WALLET.getPendingTransactionInfo().add(transactionInfoWrap.getTransactionInfo());
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(ExceptionResult result) {
                new Handler(getMainLooper()).post(() -> {
                    mView.hideProgress();
                    mView.showErrorDialog("Invalid password", "");
                });
            }
        };
    }

    public static void clear() {
        sAddressTo = null;
        sAmount = null;
        sFee = MAX_FEE;
        sSendMax = false;
        sSelectionStart = -1;
        sSelectionEnd = -1;
    }

    public TextWatcher getAmountWatcher() {
        return new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                mView.enableClearAmountButton(isEmpty(editable));
                try {
                    sSendMax = getMaxAmount() == (sAmount = isEmpty(editable) ? null : parseAmount(valueOf(editable)));
                    enableSendButton();

                    mView.setErrorAmountHint(null != sAmount && (null == getMaxAmount() || 0 >= sAmount || getMaxAmount() < sAmount) ? "Invalid amount" : null);

                } catch (Exception ignored) {
                }
            }
        };
    }

    public void onAddressChanged(Editable editable, int selectionStart, int selectionEnd) {
        mView.enableClearAddressButton(isEmpty(editable));
        setAddressTo(editable.toString(), selectionStart, selectionEnd);
    }

    @Override
    public void onResume() {
        if (!isEmpty(sAddressTo)) {
            mView.setAddress(formatAddress(unformatAddress(sAddressTo), STRING_LENGTH), sSelectionStart, sSelectionEnd);
        }

        mView.showBalance(WALLET.getBalance());
        mView.notifyConnection(SETTINGS.isConnected());
        mView.setFee(valueOf(sFee));

        setAmount();
    }

    @Override
    public void onStop() {
        WALLET.clearCredentials();
    }

    public void scanQRCode() {
        mActivity.setOnQRScanListener((requestCode, resultCode, data) -> {
            mEventBus.postSticky(new AddressToEvent(parseActivityResult(requestCode, resultCode, data).getContents(), -1, -1));
            return false;
        });
        new IntentIntegrator(mActivity).initiateScan();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final WalletContextUpdate event) {
        if (event.isSuccess()) {
            mView.showBalance(WALLET.getBalance());
            setAmount();
        } else {
            mView.showErrorDialog(getString(R.string.error), event.getExceptionMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final FeeToEvent event) {
        mView.setFee(valueOf(sFee = event.getFee()));
        setAmount();

        enableSendButton();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final AddressToEvent event) {
        setAddressTo(event.mAddressTo, event.selectionStart, event.selectionEnd);
    }

    private boolean setAddressTo(String addressTo, int selectionStart, int selectionEnd) {
        if (validateAddressTo(unformatAddress(addressTo))) {
            sAddressTo = unformatAddress(addressTo);
            sSelectionStart = selectionStart;
            sSelectionEnd = selectionEnd;

            obtainBalance(sAddressTo, new StartedFailedOperationListener<Balance>() {
                @Override
                public void onSuccess(Balance result) {
                    mEventBus.postSticky(new FeeToEvent(result.isEmpty() ? MAX_FEE : HALF_FEE));
                }
            });

            updateAddress(addressTo, selectionStart, selectionEnd);
            mView.setErrorAddressHint(null);

            return false;
        } else {
            mView.enableSendButton(false);
            mView.setFee(valueOf(sFee = MAX_FEE));

            mView.setErrorAddressHint(isEmpty(addressTo) ? null : 8 < addressTo.length() && WALLET.getAddress().startsWith(unformatAddress(addressTo)) ? "Probably the address coincides with its own" : "Invalid address");

            updateAddress(addressTo, selectionStart, selectionEnd);

            return true;
        }
    }

    private void enableSendButton() {
        mView.enableSendButton(validateAddressTo(sAddressTo) && sAmount <= getMaxAmount() && 0 < sAmount);
    }

    private void updateAddress(String addressTo, int selectionStart, int selectionEnd) {
        if (!addressTo.equals(formatAddress(unformatAddress(addressTo), STRING_LENGTH))) {
            mView.setAddress(formatAddress(unformatAddress(addressTo), STRING_LENGTH), selectionStart, selectionEnd);
        }
    }

    public void onClearAddress() {
        mView.enableSendButton(false);
        mView.setFee(valueOf(sFee = MAX_FEE));
        mView.setAddress(sAddressTo = "", sSelectionStart = -1, sSelectionEnd = -1);

        setAmount();
    }

    public void onClearAmount() {
        sAmount = null;
        sSendMax = false;
        mView.setAmount(null);
    }

    public void onSendMax() {
        sSendMax = true;

        setAmount();
    }

    private void setAmount() {
        if (sSendMax) {
            sAmount = getMaxAmount();
        }

        mView.setAmount(null == sAmount ? null : formatDouble(valueOf(sAmount)));
    }

    private Long getMaxAmount() {
        long maxAmount = Long.valueOf(WALLET.getBalance()) - sFee - 1L;
        return 0 >= maxAmount ? null : maxAmount;
    }

    private abstract class StartedFailedOperationListener<R> extends StartedOperationListener<R> {
        @Override
        public final void onFailure(ExceptionResult result) {
            new Handler(getMainLooper()).post(() -> {
                mView.hideProgress();
                mView.showErrorDialog(getString(com.amoveo.amoveowallet.R.string.error), result.getExceptionMessage());
                SETTINGS.notifyConnection(new ConnectionResult(result.getException()));
            });
        }
    }

    private class FeeToEvent {
        private final long mFee;

        private FeeToEvent(long fee) {
            this.mFee = fee;
        }

        public long getFee() {
            return mFee;
        }
    }

    private class AddressToEvent {
        private final String mAddressTo;
        private final int selectionStart;
        private final int selectionEnd;

        private AddressToEvent(String addressTo, int selectionStart, int selectionEnd) {
            this.mAddressTo = addressTo;
            this.selectionStart = selectionStart;
            this.selectionEnd = selectionEnd;
        }
    }
}