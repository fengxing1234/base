package com.map.base.lifecycle.core;

import android.content.Context;

/**
 * author : fengxing
 * date : 2022/6/9 下午3:33
 * description :
 */
public interface IApplicationLifecycleCallbacks {

    void attachBaseContext(Context base);

    int getPriority();

    void onCreate(Context context);

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);
}
