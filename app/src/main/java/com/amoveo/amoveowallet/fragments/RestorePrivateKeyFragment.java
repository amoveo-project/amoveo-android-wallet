package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.RestorePrivateKeyPresenter;
import com.amoveo.amoveowallet.view.IRestorePrivateKeyWalletView;

public class RestorePrivateKeyFragment extends BaseFragment<RestorePrivateKeyPresenter, IRestorePrivateKeyWalletView> implements IRestorePrivateKeyWalletView {
    @BindView(R.id.private_key)
    protected TextView mPrivateKey;

    @BindView(R.id.continue_button)
    protected View mContinueButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContinueButton.setOnClickListener(view1 -> mPresenter.onRestore(mPrivateKey));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_restore_private_key;
    }

    @Override
    protected RestorePrivateKeyPresenter createPresenter() {
        return new RestorePrivateKeyPresenter();
    }

    public static RestorePrivateKeyFragment newInstance() {
        return new RestorePrivateKeyFragment();
    }
}
