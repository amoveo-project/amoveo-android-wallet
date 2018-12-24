package com.amoveo.amoveowallet.toolbars;

import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;

public abstract class BaseSettingsToolbar extends BaseHomeToolbar {
    @BindView(R.id.toolbar_button)
    protected View mButton;

    BaseSettingsToolbar(View view, int titleResId, RootActivity activity) {
        super(view, titleResId, activity);

        mButton.setBackgroundResource(getButtonIcon());
        mButton.setOnClickListener(v -> onButton());
    }

    protected abstract void onButton();

    protected int getButtonIcon() {
        return R.drawable.ic_settings_white;
    }
}