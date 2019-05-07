package com.example.checkdualsim.PermissionRequestUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionRequestUtil {

    public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    public void requestReadPhoneStatePermission(Activity activity){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
            Toast.makeText(activity.getApplicationContext(), "전화 상태 읽기 요청.", Toast.LENGTH_SHORT).show();
            showPermissionPopup(activity, "전화 권한 요청", "전화 상태 읽기 요청."
                    , MY_PERMISSIONS_REQUEST_READ_PHONE_STATE, new String[]{Manifest.permission.READ_PHONE_STATE});
        } else {
            showPermissionPopup(activity, "전화 권한 요청", "전화 상태 읽기 요청."
                    , MY_PERMISSIONS_REQUEST_READ_PHONE_STATE, new String[]{Manifest.permission.READ_PHONE_STATE});
            //ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    public void showPermissionPopup(Activity activity, String title, String message,  int requestCode, String... permissions) {
       ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED;
    }
}
