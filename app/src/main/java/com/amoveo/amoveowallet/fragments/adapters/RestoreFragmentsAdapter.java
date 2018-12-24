package com.amoveo.amoveowallet.fragments.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.fragments.RestoreMnemonicFragment;
import com.amoveo.amoveowallet.fragments.RestorePrivateKeyFragment;

public class RestoreFragmentsAdapter extends FragmentPagerAdapter {
    private final FragmentActivity mActivity;

    public RestoreFragmentsAdapter(FragmentManager childFragmentManager, FragmentActivity activity) {
        super(childFragmentManager);
        this.mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return RestoreMnemonicFragment.newInstance();
            }
            case 1: {
                return RestorePrivateKeyFragment.newInstance();
            }
//            case 2: {
//                return ChartFragment.newInstance();
//            }
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return mActivity.getResources().getString(R.string.with_passphrase);
            }
            case 1: {
                return mActivity.getResources().getString(R.string.with_private_key);
            }
//            case 2: {
//                return mActivity.getResources().getString(R.string.chart);
//            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}