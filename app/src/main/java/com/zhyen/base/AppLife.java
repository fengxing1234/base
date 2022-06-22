package com.zhyen.base;

import android.content.Context;
import android.util.Log;

import com.map.base.lifecycle.anno.AppLifeCycle;
import com.map.base.lifecycle.core.IApplicationLifecycleCallbacks;

/**
 * author : fengxing
 * date : 2022/6/11 下午12:16
 * description :
 */
@AppLifeCycle
public class AppLife implements IApplicationLifecycleCallbacks {
    private static final String TAG = "fengxing";

    @Override
    public void attachBaseContext(Context base) {
        Log.d(TAG, "attachBaseContext: " + base.toString());
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void onCreate(Context context) {
        Log.d(TAG, "onCreate: " + context.toString());
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory: ");
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d(TAG, "onTrimMemory: " + level);
    }
}
