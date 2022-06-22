package com.map.base.lifecycle.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.variant.VariantInfo
import org.gradle.api.Project;

class AppLifecycleTransform extends Transform {

    Project project

    AppLifecycleTransform(Project project) {
        this.project = project
    }

    /**
     * 指定 Transform 的名称，该名称还会用于组成 Task 的名称
     * 格式为 transform[InputTypes]With[name]For[Configuration]
     * @return
     */
    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    /**
     * 指定输入内容类型
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return ImmutableSet.of(QualifiedContent.DefaultContentType.CLASSES)
    }

    /**
     * （孵化中）用于过滤 Variant，返回 false 表示该 Variant 不执行 Transform
     * @param variant
     * @return
     */
//    @Override
//    boolean applyToVariant(VariantInfo variant) {
//        return true
//    }

    /**
     * 指定输出内容类型，默认取 getInputTypes() 的值
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.SUB_PROJECTS,
                QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    /**
     * 指定是否支持增量编译
     * @return
     */
    @Override
    boolean isIncremental() {
        return true
    }

    /**
     * Transform 的核心代码在 transform() 方法中，我们要做的就是遍历输入文件，再把修改后的文件复制到目标路径中，对于 JarInputs 还有一次解压和压缩。更进一步，再考虑增量编译的情况。
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        println " AppLifecycleTransform start to transform-------------->>>>>>>"
        def appLifecycleCallbackList = []
        transformInvocation.getInputs().forEach { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                println "directoryInput = ${directoryInput.name}"
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        //形如 AppLife$$****$$Proxy.class 的类，是我们要找的目标class
                        println "fileRecurse = ${file.name}"
                        if (ScanUtil.isTargetProxyClass(file)) {
                            appLifecycleCallbackList.add(file.name)
                        }
                    }
                }
                var dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.each { JarInput jarInput ->
                println "\njarInput = ${jarInput}"
                def jarName = jarInput.name
                def md5 = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                transformInvocation.getOutputProvider().getContentLocation(jarName + md5, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
                    //处理jar包里的代码
                    File src = jarInput.file
                    if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                        List<String> list = ScanUtil.scanJar(src, dest)
                        if (list != null) {
                            appLifecycleCallbackList.addAll(list)
                        }
                    }
                }
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        if (appLifecycleCallbackList.empty) {
            println " LifeCycleTransform appLifecycleCallbackList empty"
        } else {
            new AppLifecycleCodeInjector(appLifecycleCallbackList).execute()
        }

        println "LifeCycleTransform transform finish----------------<<<<<<<\n"
    }
}