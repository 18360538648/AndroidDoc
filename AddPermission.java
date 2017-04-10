
package com.example.test;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

public class AddPermission {
    private static final int REQUEST_CODE = 1;

    public static void requestSpecialPermission(Activity activity) {
        requestAlertWindowPermission(activity);
        requestWriteSettings(activity);
    }

    @SuppressLint("NewApi")
    public static void checkpermission(Activity activity) {
        if (!(activity.checkSelfPermission(
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)) {
            requestDangerPermission(activity);
        }
    }

    @SuppressLint("NewApi")
    public static void requestDangerPermission(Activity activity) {
        String[] permissions = {
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        activity.requestPermissions(permissions, REQUEST_CODE);
    }

    private static void requestAlertWindowPermission(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    private static void requestWriteSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, REQUEST_CODE);
    }
}
