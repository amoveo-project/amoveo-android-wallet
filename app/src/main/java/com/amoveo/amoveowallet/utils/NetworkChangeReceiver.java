package com.amoveo.amoveowallet.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.amoveo.amoveowallet.presenters.results.ConnectionResult;

import static com.amoveo.amoveowallet.common.Settings.SETTINGS;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        SETTINGS.notifyConnection(null != activeNetwork && activeNetwork.isConnectedOrConnecting() ? new ConnectionResult() : new ConnectionResult(new Exception()));
    }
}
