//
//  PermissionHandlerEnums.h
//  permission_handler
//
//  Created by Razvan Lung on 15/02/2019.
//

// ios: PermissionGroupCamera
// Info.plist: NSCameraUsageDescription
// dart: PermissionGroup.camera
#ifndef PERMISSION_CAMERA
    #define PERMISSION_CAMERA 1
#endif

// ios: PermissionGroupPhotos
// Info.plist: NSPhotoLibraryUsageDescription
// dart: PermissionGroup.photos
#ifndef PERMISSION_PHOTOS
    #define PERMISSION_PHOTOS 1
#endif

// ios: PermissionGroupNotification
// dart: PermissionGroup.notification
#ifndef PERMISSION_NOTIFICATIONS
    #define PERMISSION_NOTIFICATIONS 1
#endif

typedef NS_ENUM(int, PermissionGroup) {
    PermissionGroupCamera = 1,
    PermissionGroupPhotos = 9,
    PermissionGroupNotification = 16,
    PermissionGroupUnknown = 19,
};

typedef NS_ENUM(int, PermissionStatus) {
    PermissionStatusDenied = 0,
    PermissionStatusGranted,
    PermissionStatusRestricted,
    PermissionStatusNotDetermined,
};

typedef NS_ENUM(int, ServiceStatus) {
    ServiceStatusDisabled = 0,
    ServiceStatusEnabled,
    ServiceStatusNotApplicable,
};
