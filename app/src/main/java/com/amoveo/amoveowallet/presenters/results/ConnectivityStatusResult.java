package com.amoveo.amoveowallet.presenters.results;

import android.net.NetworkInfo;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class ConnectivityStatusResult extends ExceptionResult<NetworkInfo> {
    public ConnectivityStatusResult(NetworkInfo result) {
        super(result);
    }

    public ConnectivityStatusResult(Exception exception) {
        super(exception);
    }
}
