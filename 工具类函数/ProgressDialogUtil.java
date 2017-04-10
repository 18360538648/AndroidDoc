package com.umeng.soexample.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {
    private static ProgressDialog progressDialog;

    public static void show(Context context, String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public static void close() {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
            System.gc();
        }
    }
}
