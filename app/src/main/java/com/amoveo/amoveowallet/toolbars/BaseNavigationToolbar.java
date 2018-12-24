package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.RootActivity;

public abstract class BaseNavigationToolbar extends BaseToolbar implements GoBackListener {
    BaseNavigationToolbar(View view, int titleResId, RootActivity activity) {
        super(view, titleResId, activity);

        mToolbar.setNavigationIcon(getNavigationIcon());
        mToolbar.setNavigationOnClickListener(v -> onNavigationClick());
        mActivity.setGoBackListener(this);
    }

    protected abstract int getNavigationIcon();
}