package com.map.base.lifecycle.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * author : fengxing
 * date : 2022/6/11 上午10:25
 * description :
 */
public class ApplicationLifecycleProxyClassCreator {

    private static final String METHOD_ATTACH_BASE_CONTEXT = "attachBaseContext";
    private static final String METHOD_ON_CREATE = "onCreate";
    private static final String METHOD_ON_TERMINATE = "onTerminate";
    private static final String METHOD_ON_LOW_MEMORY = "onLowMemory";
    private static final String METHOD_ON_TRIM_MEMORY = "onTrimMemory";
    private static final String METHOD_GET_PRIORITY = "getPriority";

    private static final String FIELD_APPLICATION_LIFECYCLE_CALLBACK = "mApplicationLifecycleCallback";

    public static void generateProxyClassCode(TypeElement typeElement, Filer filer, TypeMirror contextType) {
        System.out.println("fengxing=" + typeElement.toString());
        System.out.println("fengxing=" + filer.toString());
        System.out.println("fengxing=" + contextType.toString());
        TypeSpec appLifecycleProxyClass = getApplicationLifecycleProxyClass(typeElement, contextType);
        JavaFile javaFile = JavaFile.builder(LifeCycleConfig.PROXY_CLASS_PACKAGE_NAME, appLifecycleProxyClass).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TypeSpec getApplicationLifecycleProxyClass(TypeElement typeElement, TypeMirror contextType) {
        //创建类
        return TypeSpec.classBuilder(getProxyClassName(typeElement.getSimpleName().toString()))
                //addSuperinterface 实现接口
                .addSuperinterface(TypeName.get(typeElement.getInterfaces().get(0)))
                //添加修饰符
                .addModifiers(Modifier.PUBLIC)
                //添加回调字段
                .addField(TypeName.get(typeElement.getInterfaces().get(0)), FIELD_APPLICATION_LIFECYCLE_CALLBACK, Modifier.PRIVATE, Modifier.FINAL)
                //添加构造函数
                .addMethod(getConstructorMethod(typeElement))
                .addMethod(getPriorityMethod())
                .addMethod(getOnCreateMethod(contextType))
                .addMethod(getOnLowMemoryMethod())
                .addMethod(getOnTerminateMethod())
                .addMethod(getOnTrimMemoryMethod())
                .addMethod(getAttachBaseContextMethod(contextType))
                .build();
    }

    private static MethodSpec getAttachBaseContextMethod(TypeMirror contextType) {
        return MethodSpec.methodBuilder(METHOD_ATTACH_BASE_CONTEXT)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addParameter(TypeName.get(contextType), "base")
                .addStatement("this.$N.$N($N)", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_ATTACH_BASE_CONTEXT, "base")
                .build();
    }

    private static MethodSpec getOnTrimMemoryMethod() {
        return MethodSpec.methodBuilder(METHOD_ON_TRIM_MEMORY)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addParameter(int.class, "level")
                .addStatement("this.$N.$N($N)", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_ON_TRIM_MEMORY,"level")
                .build();
    }

    private static MethodSpec getOnTerminateMethod() {
        return MethodSpec.methodBuilder(METHOD_ON_TERMINATE)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addStatement("this.$N.$N()", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_ON_TERMINATE)
                .build();
    }

    private static MethodSpec getOnLowMemoryMethod() {
        return MethodSpec.methodBuilder(METHOD_ON_LOW_MEMORY)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addStatement("this.$N.$N()", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_ON_LOW_MEMORY)
                .build();
    }

    private static MethodSpec getOnCreateMethod(TypeMirror typeMirror) {
        return MethodSpec.methodBuilder(METHOD_ON_CREATE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(TypeName.get(typeMirror), "context")
                .addStatement("this.$N.$N($N)", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_ON_CREATE, "context")
                .build();
    }

    private static MethodSpec getPriorityMethod() {
        return MethodSpec.methodBuilder(METHOD_GET_PRIORITY)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("return this.$N.$N()", FIELD_APPLICATION_LIFECYCLE_CALLBACK, METHOD_GET_PRIORITY)
                .build();

    }

    /**
     * 创建构造方法
     *
     * @param typeElement
     * @return
     */
    private static MethodSpec getConstructorMethod(TypeElement typeElement) {
        return MethodSpec.constructorBuilder()//构造方法
                .addModifiers(Modifier.PUBLIC)//添加修饰符
                .addStatement("this.$N = new $T()", FIELD_APPLICATION_LIFECYCLE_CALLBACK, ClassName.get(typeElement))
                .build();
    }

    private static String getProxyClassName(String simpleClassName) {
        return LifeCycleConfig.PROXY_CLASS_PREFIX
                + simpleClassName
                + LifeCycleConfig.PROXY_CLASS_SUFFIX;
    }
}
