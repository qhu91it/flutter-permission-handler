package com.baseflow.permissionhandler;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

final class ServiceManager {
    @FunctionalInterface
    interface SuccessCallback {
        void onSuccess(@PermissionConstants.ServiceStatus int serviceStatus);
    }

    void checkServiceStatus(
            int permission,
            Context context,
            SuccessCallback successCallback,
            ErrorCallback errorCallback) {
        if(context == null) {
            Log.d(PermissionConstants.LOG_TAG, "Context cannot be null.");
            errorCallback.onError("PermissionHandler.ServiceManager", "Android context cannot be null.");
            return;
        }

        successCallback.onSuccess(PermissionConstants.SERVICE_STATUS_NOT_APPLICABLE);
    }

    private boolean isLocationServiceEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            final LocationManager locationManager = context.getSystemService(LocationManager.class);
            if (locationManager == null) {
                return false;
            }

            return locationManager.isLocationEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return isLocationServiceEnabledKitKat(context);
        } else {
            return isLocationServiceEnablePreKitKat(context);
        }
    }

    // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
    // pre Pie versions of Android.
    @SuppressWarnings("deprecation")
    private static boolean isLocationServiceEnabledKitKat(Context context)
    {
        if (Build.VERSION.SDK_INT < VERSION_CODES.KITKAT) {
            return false;
        }

        final int locationMode;

        try {
            locationMode = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
    // pre KitKat versions of Android.
    @SuppressWarnings("deprecation")
    private static boolean isLocationServiceEnablePreKitKat(Context context)
    {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT)
            return false;

        final String locationProviders = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return !TextUtils.isEmpty(locationProviders);
    }
}
