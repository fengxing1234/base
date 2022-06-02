### 基础组件

#### 网络

采用Retrofit+RxJava框架，自定义拦截器，统一错误处理，统一数据规范、网络监听，可配置网络参数，文件上传下载并展示进度。

#### 图片加载

采用策略模式，Glide与Picasso之间可以随意切换，并提供三方库的全部功能

#### 通信机制

ARouter，组件通讯

#### MVP

#### 基类

常用的base类，比如BaseActivity，BaseFragment

#### 工具类

#### 自定义View

自定义view(包括对话框，ToolBar布局，圆形图片等view的自定义)

#### 公共资源

共有的shape，drawable，layout，color等资源文件

#### 线程池

全局初始化异步线程池封装库，各个组件均可以用到

### ARouter

功能介绍： 支持直接解析标准URL进行跳转，并自动注入参数到目标页面中 支持多模块工程使用 支持添加多个拦截器，自定义拦截顺序 支持依赖注入，可单独作为依赖注入框架使用 支持InstantRun
支持MultiDex(Google方案)
映射关系按组分类、多级管理，按需初始化 支持用户指定全局降级与局部降级策略 页面、拦截器、服务等组件均自动注册到框架 支持多种方式配置转场动画 支持获取Fragment 完全支持Kotlin以及混编(
配置见文末 其他#5)
支持第三方 App 加固(使用 arouter-register 实现自动注册)
支持生成路由文档 提供 IDE 插件便捷的关联路径和目标类 支持增量编译(开启文档生成后无法增量编译)
支持动态注册路由信息

典型应用： 从外部URL映射到内部页面，以及参数传递与解析 跨模块页面跳转，模块间解耦 拦截跳转过程，处理登陆、埋点等逻辑 跨模块API调用，通过控制反转来做组件解耦

## 待解决问题

Maven上传脚本多个model能否复用一个脚本，类似：Config.gradle