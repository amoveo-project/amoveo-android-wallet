package com.amoveo.amoveowallet.presenters;

import android.content.Intent;
import android.net.Uri;
import com.amoveo.amoveowallet.view.IBaseView;

public class AboutPresenter extends BasePresenter<IBaseView> {
    public void onTwitter() {

        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://t.me/amoveo"));
        mActivity.startActivity(telegram);
    }

    private void viewLink(String uri) {
        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }
}
