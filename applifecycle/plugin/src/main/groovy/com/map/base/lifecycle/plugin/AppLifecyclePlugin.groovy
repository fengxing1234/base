package com.map.base.lifecycle.plugin

import com.android.build.gradle.AppExtension;
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppLifecyclePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "------LifeCycle plugin entrance-------"
        println "fengxing project= $project"
        println("fengxing AppExtension = ${AppExtension.toString()}")
        //获取Android拓展
        def android = project.extensions.getByType(AppExtension)
        println "fengxing android = $android"
        // 注册 Transform，支持额外增加依赖
        android.registerTransform(new AppLifecycleTransform(project))
    }
}