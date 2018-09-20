package com.example.commonlib.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.commonlib.base.application.ActivityStackManager;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        initEnv();
        setUpContentView();
        bindView();
        setUpView();
        setupMVP();
        loadData();
        ActivityStackManager.getInstance().pushActivity(this);
    }

    /**
    * mvp设置
    */
    protected void setupMVP() {
    }

    /**
     * 布局设置前初始化环境
     */
    protected abstract void initEnv();

    /**
     * 布局
     */
    protected abstract void setUpContentView();

    /**
     * 绑定id
     */
    protected abstract void bindView();

    /**
     * view初始设置
     */
    protected abstract void setUpView();

    /**
     * 读取数据
     */
    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().popActivity(this);
    }
}
