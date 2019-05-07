package com.example.checkdualsim.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.checkdualsim.BaseActivity;
import com.example.checkdualsim.PermissionRequestUtil.PermissionRequestUtil;
import com.example.checkdualsim.R;
import com.example.checkdualsim.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.example.checkdualsim.PermissionRequestUtil.PermissionRequestUtil.MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;

public class MainActivity extends BaseActivity {
    SubscriptionManager subscriptionManager;
    MainViewModel mainViewModel;
    ActivityMainBinding activityMainBinding;
    PermissionRequestUtil permissionRequestUtil;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscriptionManager = null;
        permissionRequestUtil = new PermissionRequestUtil();

        if(Build.VERSION.SDK_INT > 21)
            subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        mainViewModel = new MainViewModel(subscriptionManager, telephonyManager, this);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainVM(mainViewModel);

        if(subscriptionManager != null)
            getSimInfo();
    }

    public void getSimInfo() {
        if(permissionRequestUtil.checkPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            permissionRequestUtil.requestReadPhoneStatePermission(this);
            return;
        }

        mainViewModel.getSimInfo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainViewModel.getSimInfo();
                } else {
                    Toast.makeText(this, "전화 상태 읽기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
