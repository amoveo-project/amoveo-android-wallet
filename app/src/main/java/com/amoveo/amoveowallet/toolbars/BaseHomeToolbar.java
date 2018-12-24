package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;

public abstract class BaseHomeToolbar extends BaseNavigationToolbar {
    BaseHomeToolbar(View view, int titleResId, RootActivity activity) {
        super(view, titleResId, activity);
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_arrow_back_black;
    }
}
