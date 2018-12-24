package com.amoveo.amoveowallet.toolbars;

import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;

public class BaseToolbar {
    final RootActivity mActivity;
    private final Unbinder mUnbinder;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    public BaseToolbar(View view, int titleResId, RootActivity activity) {
        mActivity = activity;
        mUnbinder = ButterKnife.bind(this, view);
        mToolbar.setTitle(titleResId);

        mActivity.setGoBackListener(null);
    }

    public void onDestroy() {
        mUnbinder.unbind();
    }
}