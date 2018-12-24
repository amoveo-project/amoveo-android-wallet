package com.amoveo.amoveowallet.fragments;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.CheckPinPresenter;
import com.amoveo.amoveowallet.presenters.PinPresenter;

public class CheckPinFragment extends PinFragment {
    @Override
    public void showDefaultTitle() {
        mTitle.setText(R.string.enter_pin);
    }

    @Override
    protected PinPresenter createPresenter() {
        return new CheckPinPresenter();
    }

    public static CheckPinFragment newInstance() {
        return new CheckPinFragment();
    }
}
