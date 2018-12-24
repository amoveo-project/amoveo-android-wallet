package com.amoveo.amoveowallet.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import com.amoveo.amoveowallet.R;
import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.presenters.ReceivePresenter;
import com.amoveo.amoveowallet.toolbars.BaseToolbar;
import com.amoveo.amoveowallet.view.IReceiveView;
import com.devspark.robototextview.widget.RobotoTextView;

import static com.amoveo.amoveowallet.utils.Utils.formatAddress;

public class ReceiveFragment extends BaseToolbarFragment<ReceivePresenter, IReceiveView, BaseToolbar> implements IReceiveView {
    @BindView(R.id.address)
    RobotoTextView mAddress;

    @BindView(R.id.copy_button)
    View mCopyButton;

    @BindView(R.id.receive_button)
    View mReceiveButton;

    @BindView(R.id.qr_code)
    View mQRCode;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCopyButton.setOnClickListener(v -> mPresenter.onCopyButtoon());
        mAddress.setOnClickListener(v -> mPresenter.onCopyButtoon());
        mReceiveButton.setOnClickListener(v -> mPresenter.onReceive());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_receive;
    }

    @Override
    protected BaseToolbar createToolbar(View view) {
        return new BaseToolbar(view, R.string.title_receive, (RootActivity) getActivity());
    }

    @Override
    public void setAddress(String address) {
        mAddress.setText(formatAddress(address, 32));
    }

    @Override
    public void showQRImage(Bitmap bitmap) {
        if (null != mQRCode) {
            mQRCode.setBackground(new BitmapDrawable(bitmap));
        }
    }

    @Override
    protected ReceivePresenter createPresenter() {
        return new ReceivePresenter();
    }

    public static ReceiveFragment newInstance() {
        return new ReceiveFragment();
    }
}
