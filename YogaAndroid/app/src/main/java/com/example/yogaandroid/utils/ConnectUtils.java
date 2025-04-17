package com.example.yogaandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class ConnectUtils {
    // Check network
    public static boolean isConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get the current network capabilities
        NetworkCapabilities networkCapabilities = null;
        if (connectivityManager != null) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        }

        // Check network capabilities
        if (networkCapabilities != null) {
            boolean isWifiConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            boolean isMobileConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

            return isWifiConnected || isMobileConnected;
        }

        return false;
    }
}
