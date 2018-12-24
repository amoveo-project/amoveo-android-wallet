package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.ChangeNodePresenter;
import com.amoveo.amoveowallet.toolbars.ChangeNodeToolbar;
import com.amoveo.amoveowallet.view.IChangeNodeView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class ChangeNodeFragment extends BaseToolbarFragment<ChangeNodePresenter, IChangeNodeView, ChangeNodeToolbar> implements IChangeNodeView {
    @BindView(R.id.node_address)
    protected EditText mNodeAddress;

    @BindView(R.id.restore_default)
    protected View mRestoreDefault;

    @Override
    protected ChangeNodeToolbar createToolbar(View view) {
        return new ChangeNodeToolbar(view, (RootActivity) getActivity());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_change_node;
    }

    @Override
    protected ChangeNodePresenter createPresenter() {
        return new ChangeNodePresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNodeAddress.setText(SETTINGS.getApiUrl());
        mNodeAddress.addTextChangedListener(mPresenter.onNodeChanged());
        mRestoreDefault.setOnClickListener(v -> mPresenter.onRestoreDefault());
    }

    @Override
    public void setDefaultNode(String defaultApiUrl) {
        mNodeAddress.setText(defaultApiUrl);
        mNodeAddress.setSelection(defaultApiUrl.length());
    }

    public static BaseFragment newInstance() {
        return new ChangeNodeFragment();
    }
}