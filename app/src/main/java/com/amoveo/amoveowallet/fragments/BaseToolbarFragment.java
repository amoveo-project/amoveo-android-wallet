package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.amoveo.amoveowallet.presenters.BasePresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IBaseView;

public abstract class BaseToolbarFragment<P extends BasePresenter, V extends IBaseView, T extends BaseToolbar> extends BaseFragment<P, V> {
    private T mBaseToolbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBaseToolbar = createToolbar(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mBaseToolbar.onDestroy();
    }

    protected abstract T createToolbar(View view);
}
