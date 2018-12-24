package com.amoveo.amoveowallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.navigation.BottomNavigation;
import com.amoveo.amoveowallet.presenters.BaseNavigationPresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IBaseNavigationView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import static com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW;

public abstract class BaseNavigationFragment<P extends BaseNavigationPresenter, V extends IBaseNavigationView, T extends BaseToolbar> extends BaseFragment<P, V> implements IBaseNavigationView {
    @BindView(R.id.footer)
    protected AHBottomNavigation mNavigation;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(getActivity(), R.menu.navigation);
        navigationAdapter.setupWithBottomNavigation(mNavigation);
        mNavigation.setTitleState(ALWAYS_SHOW);
        ((RootActivity) getActivity()).setBottomNavigation(mNavigation);
        mNavigation.setOnTabSelectedListener(new BottomNavigation(mPresenter));
        mNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.gray));
    }

    public void setSelectedItemPosition(int position) {
        if (isAdded()) {
            mNavigation.setCurrentItem(position, true);
        }
    }
}
