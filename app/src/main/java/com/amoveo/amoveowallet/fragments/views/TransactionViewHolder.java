package com.amoveo.amoveowallet.fragments.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.fragments.listeners.OnTransactionInfoClickListener;

import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.amoveo.amoveowallet.utils.Utils.formatDateFull;
import static com.amoveo.amoveowallet.utils.Utils.formatDoubleStripped;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.info)
    View mInfo;

    @BindView(R.id.icon)
    View mIcon;

    @BindView(R.id.icon_waiting)
    View mIconWaiting;

    @BindView(R.id.status)
    View mStatus;

    @BindView(R.id.address)
    protected TextView mDirection;

    @BindView(R.id.amount)
    protected TextView mAmount;

    @BindView(R.id.datetime)
    TextView mDateTime;

    public TransactionViewHolder(View viewGroup) {
        super(viewGroup);

        ButterKnife.bind(this, viewGroup);
    }

    public void setTransactionDescription(TransactionInfo transactionInfo, OnTransactionInfoClickListener infoClickListener) {
        mIcon.setBackgroundResource(transactionInfo.isSend() ? R.drawable.ic_sended1 : R.drawable.ic_received1);
        mIconWaiting.setVisibility(transactionInfo.isPending() ? VISIBLE : GONE);
        mDirection.setText(transactionInfo.isSend() ? transactionInfo.getTo() : transactionInfo.getFrom());
        mAmount.setText(String.format("%s VEO", formatDoubleStripped(transactionInfo.getAmount())));

        if (transactionInfo.isPending()) {
            mDateTime.setText(R.string.waiting_confirmation);
        } else {
            mDateTime.setText(formatDateFull(new Date(transactionInfo.getTimeStamp() * 1000)));
        }

        mInfo.setOnClickListener(infoClickListener);
    }
}