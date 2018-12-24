package com.amoveo.amoveowallet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.dialogs.ErrorDialog;
import com.amoveo.amoveowallet.dialogs.UpDialog;
import com.amoveo.amoveowallet.presenters.BasePresenter;
import com.amoveo.amoveowallet.view.IBaseView;

public abstract class BaseFragment<P extends BasePresenter, V extends IBaseView> extends Fragment {
    static String TAG = "BaseFragment";
    private Unbinder mUnbinder;
    protected P mPresenter;
    final V mView = (V) this;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();

        mPresenter = createPresenter();
        mPresenter.onAttach((RootActivity) getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.onCreate(mView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayout(), null);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();

        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mPresenter.onDetach();
    }

    public void showInfoDialog(String message) {
        new UpDialog(getActivity(), message).show();
    }

    public void showErrorDialog(String title, String message) {
        new ErrorDialog(getActivity(), title, message).show();
    }

    protected abstract int getLayout();

    protected abstract P createPresenter();
}