package com.amoveo.amoveowallet.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.presenters.TransactionInfoPresenter;

import java.util.Date;

import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.utils.Utils.formatDateFull;

public class TransactionInfoFragment extends PendingTransactionInfoFragment<TransactionInfoPresenter> {
    @BindView(R.id.datetime)
    TextView mDateTime;

    @BindView(R.id.txhash)
    TextView mHash;

    @BindView(R.id.copy_txhash)
    View mCopyHash;

    @BindView(R.id.goto_txhash)
    View mGotoHash;

    @BindView(R.id.confirmations)
    TextView mConfirmations;

    @Override
    protected int getLayout() {
        return R.layout.fragment_transaction_info;
    }

    @Override
    protected TransactionInfoPresenter createPresenter() {
        return new TransactionInfoPresenter(mTransactionInfo);
    }

    @Override
    public void showTransactionInfo(TransactionInfo transactionInfo) {
        super.showTransactionInfo(transactionInfo);

        mDateTime.setText(formatDateFull(new Date(transactionInfo.getTimeStamp() * 1000)));
        mHash.setText(transactionInfo.getHash());
        mCopyHash.setOnClickListener(view1 -> mPresenter.onCopyHash());
        mGotoHash.setOnClickListener(view1 -> mPresenter.onGotoHash());
        mConfirmations.setText(String.valueOf(WALLET.getHeight() - transactionInfo.getBlockNumber() + 1));
    }

    public static BaseFragment newInstance(TransactionInfo transactionInfo) {
        return new TransactionInfoFragment().setTransactionInfo(transactionInfo);
    }
}