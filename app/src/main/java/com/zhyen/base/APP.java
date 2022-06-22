package com.zhyen.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.map.base.lifecycle.core.AppLifecycleCallbackManager;

/**
 * author : fengxing
 * date : 2022/6/2 下午1:54
 * description :
 */
public class APP extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AppLifecycleCallbackManager.init();
        AppLifecycleCallbackManager.onCreate(this);
        Log.d("fengxingAPP = ", "onCreate: ");
    }
}
