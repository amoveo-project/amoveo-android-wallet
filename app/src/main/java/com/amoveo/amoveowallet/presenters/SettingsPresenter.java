package com.amoveo.amoveowallet.presenters;

import android.content.Intent;
import android.os.Handler;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.dialogs.EnterPasswordDialog;
import com.amoveo.amoveowallet.dialogs.TwoButtonDialog;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;
import com.amoveo.amoveowallet.fragments.AboutFragment;
import com.amoveo.amoveowallet.fragments.ChangeNodeFragment;
import com.amoveo.amoveowallet.fragments.RemindMnemonicFragment;
import com.amoveo.amoveowallet.presenters.listeners.OperationListener;
import com.amoveo.amoveowallet.presenters.results.DecryptResult;
import com.amoveo.amoveowallet.view.ISettingsView;
import org.jetbrains.annotations.NotNull;

import static android.os.Looper.getMainLooper;
import static android.text.TextUtils.isEmpty;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.presenters.operations.CheckContentOperation.checkContent;
import static com.amoveo.amoveowallet.presenters.operations.DecryptOperations.decrypt;
import static com.amoveo.amoveowallet.wallet.Wallet.EMPTY_PASSWORD;

public class SettingsPresenter extends BasePresenter<ISettingsView> {
    public void onSetupPin() {
        checkContent(mActivity, new byte[]{0}, false);
    }

    public void onNewWallet() {
        new TwoButtonDialog(mActivity, getString(R.string.warning), getString(R.string.new_wallet_notify)) {
            @Override
            protected void onButtonOkClick() {
                dismiss();
                mActivity.dropWallet();
            }
        }.show();
    }

    public void onAbout() {
        mActivity.show(AboutFragment.newInstance(0));
    }

    public void onExportPrivateKey() {
        if (null == WALLET.getEcKeyPair()) {
            if (WALLET.isEmptyPassword()) {
                decrypt(EMPTY_PASSWORD, getPrivateKeyDecryptManager());
            } else {
                new EnterPasswordDialog(mActivity, getPrivateKeyDecryptManager()).show();
            }
        } else {
            exportPrivateKey();
        }
    }

    private void exportPrivateKey() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, WALLET.getPrivateKey());
        intent.setType("text/plain");
        mActivity.startActivity(intent);
    }

    public void onShowMnemonic() {
        if (null == WALLET.getEcKeyPair()) {
            if (WALLET.isEmptyPassword()) {
                decrypt(EMPTY_PASSWORD, getShowMnemonicDecryptManager());
            } else {
                new EnterPasswordDialog(mActivity, getShowMnemonicDecryptManager()).show();
            }
        } else {
            showMnemonic();
        }
    }

    @NotNull
    private OperationListener getPrivateKeyDecryptManager() {
        return new OperationListener<DecryptResult>() {
            @Override
            public void onStart() {
                new Handler(getMainLooper()).post(() -> mView.showProgress());
            }

            @Override
            public void onSuccess(DecryptResult result) {
                new Handler(getMainLooper()).post(() -> mView.hideProgress());
                exportPrivateKey();
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

    @NotNull
    private OperationListener getShowMnemonicDecryptManager() {
        return new OperationListener<DecryptResult>() {
            @Override
            public void onStart() {
                new Handler(getMainLooper()).post(() -> mView.showProgress());
            }

            @Override
            public void onSuccess(DecryptResult result) {
                new Handler(getMainLooper()).post(() -> {
                    mView.hideProgress();
                    showMnemonic();
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

    public void onChangeNode() {
        mActivity.show(ChangeNodeFragment.newInstance());
    }

    private void showMnemonic() {
        if (isEmpty(WALLET.getMnemonic())) {
            mView.showInfoDialog(getString(R.string.no_mnemonic));
        } else {
            mActivity.show(RemindMnemonicFragment.newInstance(WALLET.getMnemonic()));
        }
    }
}