package com.map.base.lifecycle.compiler;

/**
 * author : fengxing
 * date : 2022/6/9 下午3:56
 * description :
 */
public class LifeCycleConfig {
    /**
     * 生成代理类的包名
     */
    public static final String PROXY_CLASS_PACKAGE_NAME = "com.map.base.lifecycle.compiler.proxy";

    /**
     * 生成代理类统一的后缀
     */
    public static final String PROXY_CLASS_SUFFIX = "$$Proxy";

    /**
     * 生成代理类统一的前缀
     */
    public static final String PROXY_CLASS_PREFIX = "AppLife$$";


    public static final String APPLICATION_LIFECYCLE_CALLBACK_QUALIFIED_NAME = "com.map.base.lifecycle.core.IApplicationLifecycleCallbacks";

    public static final String APPLICATION_LIFECYCLE_CALLBACK_SIMPLE_NAME = "IApplicationLifecycleCallbacks";

    public static final String CONTEXT = "android.content.Context";
}
