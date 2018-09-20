package com.example.commonlib.base.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.commonlib.utils.LogUtil;


/**
 * Created by shaw on 2017/7/12.
 */

public class AppStatusTracker implements Application.ActivityLifecycleCallbacks {

    private boolean isForground;
    private int activeCount;    //计数器，所有的Activity都执行了stop方法，那么count=0,app在后台执行

    private static AppStatusTracker tracker;

    private AppStatusTracker(Application application) {
        application.registerActivityLifecycleCallbacks(this);//必须注册才能接受到该方法的回调
    }

    public static void init(Application application) {
        tracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return tracker;
    }

    public boolean isForground() {
        return isForground;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityStopped");
        activeCount --;
        if (activeCount == 0){
            isForground = false;//在后台执行
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityStarted");
        activeCount ++;
    }

    //界面要显示时调用此方法，onCreate -- onResume 之间不该写大量代码，
    // 防止初始化时间过长会导致黑屏，
    //一旦此方法回调，就可以确定当前应用是在前台执行
    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityResumed");
        //Activity 跳转过程中，onPause -> onResume 需要时间，这过程中算前台？后台？
        isForground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityPaused");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.e(getClass().getSimpleName(),activity.toString()+" onActivityDestroyed");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }
}
