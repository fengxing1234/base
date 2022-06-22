package com.map.base.lifecycle.compiler;

import com.google.auto.service.AutoService;
import com.map.base.lifecycle.anno.AppLifeCycle;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * author : fengxing
 * date : 2022/6/9 下午4:20
 * description : 生成代码
 * ProcessingEnvironment:
 * ProcessingEnvironment是由注解处理器框架去实现，以便在使用框架时提供给开发者一系列有用的工具类，
 * 可以理解为上下文环境对象， 通过对象可以获取到很多有用的工具类:
 * Element、Elements，Filer，Messager、Types、Map、SourceVersion等
 * <p>
 * Element:
 * Element是一个用于描述被注解标记的节点，Java中的类、方法、成员属性变量都属于节点，通过这个接口可以获取对应节点上的信息。
 * 以下是节点类型：
 * Parameterizable：表示混合类型的元素（不仅只有一种类型的Element)
 * TypeParameterElement：带有泛型参数的类、接口、方法或者构造器
 * VariableElement：表示字段、常量、方法或构造函数。参数、局部变量、资源变量或异常参数
 * ExecutableElement：表示类或接口的方法、构造函数或初始化器（静态或实例），包括注释类型元素
 * TypeElement :表示类和接口
 * PackageElement：表示包
 * 以下是过去节点信息：
 * getModifiers()	获取被注解节点的修饰符
 * getEnclosingElement()	获取父类元素
 * getEnclosedElements()	获取子类元素
 * getAnnotation()	获取注解
 * TypeMirror asType()	可以根据TypeMirror，获取到被注解的Class对象，比如 element.asType()
 * <p>
 * Elements:是操作Element的工具类，可以通过{ProcessingEnvironment.getElementUtils}获取
 * <p>
 * Filer:
 * 注解处理器用于创建文件的工具接口类，需要通过{processingEnvironment.getFiler}获取。
 * <p>
 * Types与TypeMirror:
 * 操作TypeElement的工具类;
 * Types需要通过processingEnvironment.getTypeUtils()获取
 * 典型应用是首先通过element.asType()得到对应的TypeMirror对象，再调用Types的方法判断是否是符合需求的类型，
 * 比如说可以通过isSubType()方法判断是否是使用了某个指定注解
 */
@AutoService(Processor.class)
//@SupportedAnnotationTypes("com.map.base.lifecycle.anno.AppLifeCycle")
public class LifeCycleProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElements;
    private Messager mMessager;

    /**
     * 编译期间，init()会自动被注解处理工具调用，并传入ProcessingEnvironment
     * 通过该参数可以获取到很多有用的工具类
     *
     * @param processingEnv 参数
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        System.out.println("fengxing");
    }

    /**
     * 当扫描到注解时自动回调这个方法
     * Annotation Processor扫描出的结果会存储进roundEnvironment中，
     * 可以在这里获取到注解内容，编写你的操作逻辑。
     * 注意process()函数中不能直接进行异常抛出,否则程序会异常崩溃
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("fengxing");
        //获得所有包含AppLifeCycle注解的集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AppLifeCycle.class);
        TypeElement contextTypeElement = mElements.getTypeElement(LifeCycleConfig.CONTEXT);

        TypeMirror typeContext = contextTypeElement.asType();

        for (Element element : elements) {
            if (!element.getKind().isClass()) {
                throw new RuntimeException("Annotation AppLifecycle can only be used in class.");
            }
            TypeElement typeElement = (TypeElement) element;

            String classFullName = typeElement.getQualifiedName().toString();
            System.out.println("process class name : " + classFullName);

            List<? extends TypeMirror> mirrorList = typeElement.getInterfaces();
            if (mirrorList.isEmpty()) {
                throw new RuntimeException(classFullName + "必须实现 IApplicationLifecycleCallbacks 接口");
            }
            boolean checkInterfaceFlag = false;
            for (TypeMirror mirror : mirrorList) {
                System.out.println(mirror.toString());
                if (LifeCycleConfig.APPLICATION_LIFECYCLE_CALLBACK_QUALIFIED_NAME.equals(mirror.toString())) {
                    checkInterfaceFlag = true;
                }
                if (!checkInterfaceFlag) {
                    throw new RuntimeException(classFullName + "必须实现 IApplicationLifecycleCallbacks 接口");
                }
                System.out.println(classFullName + "start to generate proxy class code.");
                ApplicationLifecycleProxyClassCreator.generateProxyClassCode(typeElement, mFiler, typeContext);
            }
        }
        return true;
    }

    /**
     * 指定处理的注解,需要将要处理的注解的全名放到Set中返回，
     * 可以使用注解@SupportedAnnotationTypes({Consts.ANN_TYPE_ROUTE})替代
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(AppLifeCycle.class.getCanonicalName());
        System.out.println("fengxing = " + AppLifeCycle.class.getCanonicalName());
        log(AppLifeCycle.class.getCanonicalName());
        return set;
        //return Collections.singleton(AppLifeCycle.class.getCanonicalName());
    }

    /**
     * 指定注解处理器接收的参数
     * 可以使用注解@SupportedOptions(Consts.ARGUMENTS_NAME)替代
     */
    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    /**
     * 设置你的Java版本，JDK1.8
     * 可以使用@SupportedSourceVersion(SourceVersion.RELEASE_8)替代
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    public void log(String name) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, name);
    }
}
