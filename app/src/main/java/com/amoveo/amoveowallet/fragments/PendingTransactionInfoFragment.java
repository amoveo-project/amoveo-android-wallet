package com.amoveo.amoveowallet.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.presenters.PendingTransactionInfoPresenter;
import com.amoveo.amoveowallet.toolbars.BaseHomeToolbar;
import com.amoveo.amoveowallet.toolbars.TransactionInfoToolbar;
import com.amoveo.amoveowallet.view.ITransactionInfoView;

import static com.amoveo.amoveowallet.utils.Utils.formatDouble;

public class PendingTransactionInfoFragment<P extends PendingTransactionInfoPresenter> extends BaseToolbarFragment<P, ITransactionInfoView, BaseHomeToolbar> implements ITransactionInfoView {
    @BindView(R.id.connection_notify)
    View mConnectionView;

    @BindView(R.id.icon)
    View mIcon;

    @BindView(R.id.amount)
    TextView mAmount;

    @BindView(R.id.address_hint)
    TextView mDirectionHint;

    @BindView(R.id.address)
    TextView mDirection;

    @BindView(R.id.copy_address)
    View mCopyDirection;

    @BindView(R.id.actual_fee)
    TextView mActualFee;

    TransactionInfo mTransactionInfo;

    @Override
    protected int getLayout() {
        return R.layout.fragment_pending_transaction_info;
    }

    @Override
    protected TransactionInfoToolbar createToolbar(View view) {
        return new TransactionInfoToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected P createPresenter() {
        return (P) new PendingTransactionInfoPresenter(mTransactionInfo);
    }

    PendingTransactionInfoFragment setTransactionInfo(TransactionInfo transactionInfo) {
        mTransactionInfo = transactionInfo;
        return this;
    }

    @Override
    public void showTransactionInfo(TransactionInfo transactionInfo) {
        mIcon.setBackgroundResource(transactionInfo.isSend() ? R.drawable.ic_sended : R.drawable.ic_received);
        mAmount.setText(formatDouble(transactionInfo.getAmount()));
        mDirectionHint.setText(transactionInfo.isSend() ? R.string.to : R.string.from);
        mDirection.setText(transactionInfo.isSend() ? transactionInfo.getTo() : transactionInfo.getFrom());
        mActualFee.setText(formatDouble(transactionInfo.getFee()));
        mCopyDirection.setOnClickListener(view1 -> mPresenter.onCopyAddress(transactionInfo.isSend()));
    }

    @Override
    public void notifyConnection(boolean isConnected) {
        mConnectionView.setVisibility(isConnected ? View.GONE : View.VISIBLE);
    }

    public static BaseFragment newInstance(TransactionInfo transactionInfo) {
        return new PendingTransactionInfoFragment().setTransactionInfo(transactionInfo);
    }
}