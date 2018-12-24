package com.amoveo.amoveowallet.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import com.amoveo.amoveowallet.utils.SimpleTextWatcher;
import com.amoveo.amoveowallet.view.IChangeNodeView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.presenters.operations.SaveContentOperation.saveContent;

public class ChangeNodePresenter extends BasePresenter<IChangeNodeView> {
    public void onRestoreDefault() {
        mView.setDefaultNode(SETTINGS.getDefaultApiUrl());
    }

    public TextWatcher onNodeChanged() {
        return new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                SETTINGS.setApiUrl(s.toString());
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        saveContent();
    }
}