package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.amoveo.amoveowallet.fragments.adapters.TransactionsAdapter;
import com.amoveo.amoveowallet.presenters.BalancePresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IBalanceView;

import java.util.List;

import static android.text.Html.fromHtml;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.amoveo.amoveowallet.utils.Utils.formatDouble;

public class BalanceFragment extends BaseToolbarFragment<BalancePresenter, IBalanceView, BaseToolbar> implements IBalanceView {
    @BindView(R.id.connection_notify)
    View mConnectionView;

    @BindView(R.id.balance)
    TextView mBalance;

    @BindView(R.id.transactions_recycler_view)
    RecyclerView mTransactions;

    @BindView(R.id.empty)
    View mEmpty;

    @BindView(R.id.empty_link)
    TextView mEmptyLink;

    private TransactionsAdapter mAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TransactionsAdapter((RootActivity) getActivity());
        mEmptyLink.setOnClickListener(v -> ((RootActivity) getActivity()).setCurrentItem(0));
        mTransactions.setAdapter(mAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_balance;
    }

    @Override
    protected BalancePresenter createPresenter() {
        return new BalancePresenter();
    }

    @Override
    protected BaseToolbar createToolbar(View view) {
        return new BaseToolbar(view, R.string.title_balance, (RootActivity) getActivity());
    }

    @Override
    public void notifyConnection(boolean isConnected) {
        mConnectionView.setVisibility(isConnected ? GONE : VISIBLE);
    }

    @Override
    public void showBalance(String balance) {
        mBalance.setText(formatDouble(balance));
    }

    @Override
    public void showTransactions(List<TransactionInfo> transactionInfos) {
        mAdapter.setItems(transactionInfos);

        if (null == transactionInfos || transactionInfos.isEmpty()) {
            if (null != transactionInfos) {
                mEmpty.setVisibility(VISIBLE);
                mEmptyLink.setText(fromHtml(getString(R.string.no_transactions_info)));
            }
            mTransactions.setVisibility(GONE);
        } else {
            mEmpty.setVisibility(GONE);
            mTransactions.setVisibility(VISIBLE);
        }
    }

    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }
}
