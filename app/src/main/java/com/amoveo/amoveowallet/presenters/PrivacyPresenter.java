package com.amoveo.amoveowallet.presenters;

import android.view.View;
import android.widget.AdapterView;
import com.amoveo.amoveowallet.utils.SimpleSpinnerItemSelectedListener;
import com.amoveo.amoveowallet.view.IBaseView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class PrivacyPresenter extends BasePresenter<IBaseView> {
    public AdapterView.OnItemSelectedListener OnSelectLanguage() {
        return new SimpleSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SETTINGS.setLanguage(position);
            }
        };
    }

    public AdapterView.OnItemSelectedListener OnSelectPrivacy() {
        return new SimpleSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SETTINGS.setPrivacy(position);
            }
        };
    }

    public AdapterView.OnItemSelectedListener OnSelectMnemonicLength() {
        return new SimpleSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SETTINGS.setMnemonicSelection(position);
            }
        };
    }
}
