# Android 6.0 运行时权限

这是Android 6.0的一个新特性，如果将targetSdkVersion设为23以上(包括23)，且在Android 6.0系统以上(包括6.0)运行，需要用户手动对危险权限进行授权，如果不加入这一步骤，会导致程序中需要某些危险权限，但是用户根本不知情，导致应用会直接崩溃，这个在做打包工具时就出现这个问题。

## 1. 一些重要API

* checkSelfPermission。检测是否拥有某个权限
* requestPermissions。请求权限
* shouldShowRequestPermissionRationale。某个权限是否被用户拒绝过，如果拒绝过，当还需要这个权限时，可以对用户做出解析

## 2. 操作步骤

### 2.1  申明需要申请的权限集合

注意：这些权限在Manifest还是要申请的

```
public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };
```

### 2.2 申请权限

```
ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
```

### 2.3 处理权限申请回调

```
// requestCode申请权限的请求码(2.2中的)
// permissions处理的权限
// grantResults授权的结果
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
    }
```



