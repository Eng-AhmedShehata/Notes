package com.example.note;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this);
    }
}
