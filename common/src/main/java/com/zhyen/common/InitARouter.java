package com.zhyen.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * author : fengxing
 * date : 2022/6/2 上午9:29
 * description :
 */
public final class InitARouter {

    public InitARouter() {
    }

    public void init(Application application) {
        init(application, true);
    }

    public void init(Application application, boolean isDebug) {
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }
}
