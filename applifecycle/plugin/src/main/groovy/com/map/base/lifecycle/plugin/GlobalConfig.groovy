package com.map.base.lifecycle.plugin

class GlobalConfig {
    static final PROXY_CLASS_PREFIX = "AppLife\$\$"
    static final PROXY_CLASS_SUFFIX = "\$\$Proxy.class"
    static final PROXY_CLASS_PACKAGE_NAME = "com/map/base/lifecycle/compiler/proxy"
    //com.map.base.lifecycle.core
    static final REGISTER_CLASS_FILE_NAME = "com/map/base/lifecycle/core/AppLifecycleCallbackManager.class"

    static final INJECT_CLASS_NAME = "com/map/base/lifecycle/core/AppLifecycleCallbackManager"
    static final INJECT_METHOD_NAME = "registerApplicationLifecycleCallbacks"
    static final INJECT_PARAMS_DESC = "(Ljava/lang/String;)V"

}