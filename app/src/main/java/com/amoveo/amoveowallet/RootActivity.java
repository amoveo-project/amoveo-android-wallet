package com.amoveo.amoveowallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.amoveo.amoveowallet.common.CacheBean;
import com.amoveo.amoveowallet.fragments.AccessWalletFragment;
import com.amoveo.amoveowallet.fragments.BaseFragment;
import com.amoveo.amoveowallet.presenters.SendPresenter;
import com.amoveo.amoveowallet.presenters.listeners.OnQRScanListener;
import com.amoveo.amoveowallet.toolbars.GoBackListener;
import com.amoveo.amoveowallet.utils.HLog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import java.io.File;
import java.io.FileWriter;

import static com.amoveo.amoveowallet.api.APIManager.API;
import static com.amoveo.amoveowallet.common.Settings.SETTINGS;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static com.amoveo.amoveowallet.engine.Engine.ENGINE;
import static com.amoveo.amoveowallet.presenters.operations.LoadCacheOperation.loadCache;
import static com.amoveo.amoveowallet.presenters.operations.LoadContentOperation.loadContent;
import static com.amoveo.amoveowallet.presenters.operations.LoadWordListOperation.loadWordList;
import static com.amoveo.amoveowallet.utils.Utils.GSON;

public class RootActivity extends AppCompatActivity {
    private static final String TAG = RootActivity.class.getName();
    private static final String TAG_CONTAINER = "container";
    private static final String TAG_NAVIGABLE = "navigable";

    private OnQRScanListener mQrScanListener;
    private AHBottomNavigation mBottomNavigation;
    private GoBackListener mGoBackListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_view);
        Fabric.with(this, new Crashlytics());

        API.init(this);
        start();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onBackPressed() {
        if (null != mGoBackListener) {
            GoBackListener listener = mGoBackListener;
            mGoBackListener = null;
            listener.onNavigationClick();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        start();
    }

    private void start() {
        SETTINGS.expand();
        SETTINGS.setCacheFile(new File(getFilesDir(), "cache.json"));
        SETTINGS.setContentFile(new File(getFilesDir(), "cws.json"));
        ENGINE.restart();

        loadWordList(this);

        if (SETTINGS.getContentFile().exists()) {
            loadCache(this);
            loadContent(this, false);
        } else {
            show(AccessWalletFragment.newInstance());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        ENGINE.shutdown();
    }

    private static final String UNREADABLE = "";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        synchronized (SETTINGS) {
            try {
                FileWriter fileWriter = new FileWriter(SETTINGS.getCacheFile());
                fileWriter.write(GSON.toJson(new CacheBean()));
                fileWriter.close();
            } catch (Exception e) {
                HLog.error(TAG, "writeContent", e);
            }
        }

        SETTINGS.notifyCollapseTime();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mQrScanListener && mQrScanListener.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setOnQRScanListener(OnQRScanListener qrScanListener) {
        mQrScanListener = qrScanListener;
    }

    public void show(final BaseFragment fragment) {
        show(fragment, R.id.container, TAG_CONTAINER);
    }

    public void showNavigable(final BaseFragment fragment) {
        show(fragment, R.id.navigable, TAG_NAVIGABLE);
    }

    private synchronized void show(final BaseFragment fragment, int containerId, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (null != fragmentManager && !fragmentManager.isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (null == fragmentManager.findFragmentByTag(tag)) {
                fragmentTransaction.add(containerId, fragment, tag);
            } else {
                fragmentTransaction.replace(containerId, fragment, tag);
            }

            fragmentTransaction.commit();
        }
    }

    public void dropWallet() {
        SETTINGS.setPin(new byte[]{0});
        SETTINGS.notifyPinAttempt(true);
        WALLET.dropWallet();

        SendPresenter.clear();

        SETTINGS.setContentFile(updateFile(SETTINGS.getContentFile(), "cws.json"));
        SETTINGS.setCacheFile(updateFile(SETTINGS.getCacheFile(), "cache.json"));

        show(AccessWalletFragment.newInstance());
    }

    private File updateFile(File file, String fileName) {
        if (null != file && file.exists()) {
            file.delete();
        }

        return new File(getFilesDir(), fileName);
    }



    public void setGoBackListener(GoBackListener listener) {
        mGoBackListener = listener;
    }

    public void setBottomNavigation(AHBottomNavigation bottomNavigation) {
        mBottomNavigation = bottomNavigation;
    }

    public void setCurrentItem(int position) {
        mBottomNavigation.setCurrentItem(position);
    }
}