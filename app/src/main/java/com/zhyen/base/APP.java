package com.zhyen.base;

import android.app.Application;

import androidx.multidex.MultiDex;

/**
 * author : fengxing
 * date : 2022/6/2 下午1:54
 * description :
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
