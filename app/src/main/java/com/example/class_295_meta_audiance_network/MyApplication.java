package com.example.class_295_meta_audiance_network;

import android.app.Application;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);

//        Go to logCat and click CTRL+f then search testDevice then copy the test id and pest here
        AdSettings.addTestDevice("6dc8347e-7c73-4d97-8477-59ee1081cca9");
    }
}
