package com.example.commonlib.base.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

public abstract class BaseApplication extends Application{
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppStatusTracker.init(this);
        CrashHandler.getInstance().init(this);
        Stetho.initializeWithDefaults(this);
        init();
    }


    protected abstract void init();

    public static BaseApplication getInstance() {
        return instance;
    }
}
