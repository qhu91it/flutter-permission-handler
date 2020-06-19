package com.baseflow.permissionhandler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionUtils {

    @PermissionConstants.PermissionGroup
    static int parseManifestName(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                return PermissionConstants.PERMISSION_GROUP_CAMERA;
            default:
                return PermissionConstants.PERMISSION_GROUP_UNKNOWN;
        }
    }

    static List<String> getManifestNames(Context context, @PermissionConstants.PermissionGroup int permission) {
        final ArrayList<String> permissionNames = new ArrayList<>();

        switch (permission) {
            case PermissionConstants.PERMISSION_GROUP_CAMERA:
                if (hasPermissionInManifest(context, permissionNames, Manifest.permission.CAMERA))
                    permissionNames.add(Manifest.permission.CAMERA);
                break;

            case PermissionConstants.PERMISSION_GROUP_NOTIFICATION:
            case PermissionConstants.PERMISSION_GROUP_PHOTOS:
            case PermissionConstants.PERMISSION_GROUP_UNKNOWN:
                return null;
        }

        return permissionNames;
    }

    private static boolean hasPermissionInManifest(Context context, ArrayList<String> confirmedPermissions, String permission) {
        try {
            if (confirmedPermissions != null) {
                for (String r : confirmedPermissions) {
                    if (r.equals(permission)) {
                        return true;
                    }
                }
            }

            if (context == null) {
                Log.d(PermissionConstants.LOG_TAG, "Unable to detect current Activity or App Context.");
                return false;
            }

            PackageInfo info = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);

            if (info == null) {
                Log.d(PermissionConstants.LOG_TAG, "Unable to get Package info, will not be able to determine permissions to request.");
                return false;
            }

            confirmedPermissions = new ArrayList<>(Arrays.asList(info.requestedPermissions));
            for (String r : confirmedPermissions) {
                if (r.equals(permission)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            Log.d(PermissionConstants.LOG_TAG, "Unable to check manifest for permission: ", ex);
        }
        return false;
    }

    @PermissionConstants.PermissionStatus
    static int toPermissionStatus(final Activity activity, final String permissionName, int grantResult) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PermissionUtils.isNeverAskAgainSelected(activity, permissionName)
                    ? PermissionConstants.PERMISSION_STATUS_NEWER_ASK_AGAIN
                    : PermissionConstants.PERMISSION_STATUS_DENIED;
        }

        return PermissionConstants.PERMISSION_STATUS_GRANTED;
    }

    static void updatePermissionShouldShowStatus(final Activity activity, @PermissionConstants.PermissionGroup int permission) {
        if (activity == null) {
            return;
        }

        List<String> names = getManifestNames(activity, permission);

        if (names == null || names.isEmpty()) {
            return;
        }

        for (String name : names) {
            PermissionUtils.setRequestedPermission(activity, name);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    static boolean isNeverAskAgainSelected(final Activity activity, final String name) {
        if (activity == null) {
            return false;
        }

        return PermissionUtils.neverAskAgainSelected(activity, name);
    }

  @RequiresApi(api = Build.VERSION_CODES.M)
  static boolean neverAskAgainSelected(final Activity activity, final String permission) {
    final boolean hasRequestedPermissionBefore = getRequestedPermissionBefore(activity, permission);
    final boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    return hasRequestedPermissionBefore && !shouldShowRequestPermissionRationale;
  }

  static void setRequestedPermission(final Context context, final String permission) {
    SharedPreferences genPrefs = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = genPrefs.edit();
    editor.putBoolean(permission, true);
    editor.apply();
  }

  static boolean getRequestedPermissionBefore(final Context context, final String permission) {
    SharedPreferences genPrefs = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
    return genPrefs.getBoolean(permission, false);
  }
}
