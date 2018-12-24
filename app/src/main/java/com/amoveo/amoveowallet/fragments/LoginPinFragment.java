package com.amoveo.amoveowallet.fragments;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.LoginPinPresenter;
import com.amoveo.amoveowallet.presenters.PinPresenter;

public class LoginPinFragment extends PinFragment {
    @Override
    public void showDefaultTitle() {
        mTitle.setText(R.string.enter_pin);
    }

    @Override
    protected PinPresenter createPresenter() {
        return new LoginPinPresenter();
    }

    public static LoginPinFragment newInstance() {
        return new LoginPinFragment();
    }
}
