package com.amoveo.amoveowallet.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.fragments.listeners.OnTransactionInfoClickListener;
import com.amoveo.amoveowallet.fragments.views.TransactionViewHolder;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    private List<TransactionInfo> mTransactionInfos;
    private RootActivity mActivity;

    public TransactionsAdapter(RootActivity activity) {
        mActivity = activity;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new TransactionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_transaction_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder transactionViewHolder, int position) {
        TransactionInfo transactionInfo = mTransactionInfos.get(position);
        transactionViewHolder.setTransactionDescription(transactionInfo, new OnTransactionInfoClickListener(mActivity, transactionInfo));
    }

    @Override
    public int getItemCount() {
        return null == mTransactionInfos ? 0 : mTransactionInfos.size();
    }

    public void setItems(List<TransactionInfo> items) {
        this.mTransactionInfos = items;

        notifyDataSetChanged();
    }
}