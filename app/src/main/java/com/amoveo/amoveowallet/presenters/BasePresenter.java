package com.amoveo.amoveowallet.presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.utils.HLog;
import com.amoveo.amoveowallet.view.IBaseView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

public abstract class BasePresenter<V extends IBaseView> {
    protected static String TAG = "BasePresenter";

    final EventBus mEventBus = EventBus.getDefault();

    protected V mView;
    protected RootActivity mActivity;

    BasePresenter() {
        TAG = this.getClass().getSimpleName();
    }

    public void onCreate(V view) {
        mView = view;
    }

    private void subscribe() {
        try {
            if (!mEventBus.isRegistered(this)) {
                mEventBus.register(this);
            }
        } catch (EventBusException e) {
            HLog.verbose(TAG, TAG + " is not registered to EventBus");
        }
    }

    private void unsubscribe() {
        try {
            if (mEventBus.isRegistered(this)) {
                mEventBus.unregister(this);
            }
        } catch (EventBusException e) {
            HLog.verbose(TAG, TAG + " is not unregistered from EventBus");
        }
    }

    public void onAttach(RootActivity activity) {
        mActivity = activity;
        subscribe();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onPause() {
        ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Fragment) mView).getView().getWindowToken(), 0);
    }

    public void onStop() {
    }

    public void onDestroyView() {
    }

    public void onDestroy() {
    }

    public void onDetach() {
        unsubscribe();
    }

    String getString(int string) {
        return mActivity.getResources().getString(string);
    }
}