package com.example.checkdualsim.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.checkdualsim.BaseViewModel;
import com.example.checkdualsim.PermissionRequestUtil.SystemPropertiesProxy;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends BaseViewModel {
    TelephonyManager telephonyManager;
    SubscriptionManager subscriptionManager;
    List<SubscriptionInfo> subscriptionInfoList;
    MutableLiveData<List<SubscriptionInfo>> subscriptionInfos = new MutableLiveData<>();
    Activity activity;

    public MainViewModel(SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, Activity activity) {
        this.subscriptionManager = subscriptionManager;
        this.activity = activity;
        this.telephonyManager = telephonyManager;
    }

    @TargetApi(23)
    public void getSimInfo() throws SecurityException{
            //subscriptionInfos.postValue(subscriptionManager.getActiveSubscriptionInfoList());
        subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

        Log.d("subscriptionInfo", "subscription phonecount = " + telephonyManager.getPhoneCount());

        if(telephonyManager.getPhoneCount() > 1) {
            Log.d("subscriptionInfo", "multisim");
        }

        for(SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
            Log.d("subscriptionInfo", "subscription slotIndex = " + subscriptionInfo.getSimSlotIndex());
            Log.d("subscriptionInfo", "subscription subscriptionId = " + subscriptionInfo.getSubscriptionId());
            Log.d("subscriptionInfo", "subscription iccId = " + subscriptionInfo.getIccId());
            Log.d("subscriptionInfo", "subscription carrierName = " + subscriptionInfo.getCarrierName());
            Log.d("subscriptionInfo", "subscription number = " + subscriptionInfo.getNumber());
        }

        Log.d("subscriptionInfo", "subscription simSerial(ICCID) = " + telephonyManager.getSimSerialNumber());
        Log.d("subscriptionInfo", "subscription simSerial(IMSI) = " + telephonyManager.getSubscriberId());

    }
}
