package com.nazaruk.myappv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class NetworkCheck extends BroadcastReceiver {
    private View view;

    public NetworkCheck(View view) {
        super();
        this.view = view;
    }

    public void onReceive(Context context, Intent intent) {
        if (!isOnline(context)) {
            Snackbar.make(view, R.string.conError, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        }
        return false;
    }
}

