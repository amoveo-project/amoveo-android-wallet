package com.amoveo.amoveowallet.fragments;

import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.presenters.PinPresenter;
import com.amoveo.amoveowallet.presenters.SetupPinPresenter;
import com.amoveo.amoveowallet.view.ISetupPinView;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class SetupPinFragment extends PinFragment<PinPresenter, ISetupPinView> implements ISetupPinView {
    @Override
    public void showDefaultTitle() {
        boolean isEmpty = true;

        for (int index = 0; isEmpty && index < SETTINGS.getPin().length; index++) {
            isEmpty &= 0 == SETTINGS.getPin()[index];
        }

        mTitle.setText(isEmpty ? R.string.create_pin : R.string.change_pin);
    }

    @Override
    protected PinPresenter createPresenter() {
        return new SetupPinPresenter();
    }

    public static SetupPinFragment newInstance() {
        return new SetupPinFragment();
    }

    @Override
    public void confirmPin() {
        setPin0Background(R.drawable.oval_yellow_shadow);
        setPin1Background(R.drawable.oval_yellow_shadow);
        setPin2Background(R.drawable.oval_yellow_shadow);
        setPin3Background(R.drawable.oval_yellow_shadow);

        mTitle.setText(R.string.confirm_pin);
    }
}
