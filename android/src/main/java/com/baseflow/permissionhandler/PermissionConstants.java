package com.baseflow.permissionhandler;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

final class PermissionConstants {
    static final String LOG_TAG = "permissions_handler";
    static final int PERMISSION_CODE = 24;
    static final int PERMISSION_CODE_IGNORE_BATTERY_OPTIMIZATIONS = 5672353;

    //PERMISSION_GROUP
    static final int PERMISSION_GROUP_CAMERA = 1;
    static final int PERMISSION_GROUP_PHOTOS = 9;
    static final int PERMISSION_GROUP_NOTIFICATION = 16;
    static final int PERMISSION_GROUP_UNKNOWN = 19;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            PERMISSION_GROUP_CAMERA,
            PERMISSION_GROUP_PHOTOS,
            PERMISSION_GROUP_NOTIFICATION,
            PERMISSION_GROUP_UNKNOWN,
    })
    @interface PermissionGroup {
    }

    //PERMISSION_STATUS
    static final int PERMISSION_STATUS_DENIED = 0;
    static final int PERMISSION_STATUS_GRANTED = 1;
    static final int PERMISSION_STATUS_RESTRICTED = 2;
    static final int PERMISSION_STATUS_NOT_DETERMINED = 3;
    static final int PERMISSION_STATUS_NEWER_ASK_AGAIN = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            PERMISSION_STATUS_DENIED,
            PERMISSION_STATUS_GRANTED,
            PERMISSION_STATUS_RESTRICTED,
            PERMISSION_STATUS_NOT_DETERMINED,
            PERMISSION_STATUS_NEWER_ASK_AGAIN,
    })
    @interface PermissionStatus {
    }

    //SERVICE_STATUS
    static final int SERVICE_STATUS_DISABLED = 0;
    static final int SERVICE_STATUS_ENABLED = 1;
    static final int SERVICE_STATUS_NOT_APPLICABLE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            SERVICE_STATUS_DISABLED,
            SERVICE_STATUS_ENABLED,
            SERVICE_STATUS_NOT_APPLICABLE
    })
    @interface ServiceStatus {
    }
}
