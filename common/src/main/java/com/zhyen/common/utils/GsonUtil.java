package com.zhyen.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * author : fengxing
 * date : 2022/6/7 上午9:23
 * description :
 * Serialization:序列化，使Java对象到Json字符串的过程
 * Deserialization：反序列化，字符串转换成Java对象
 * <p>
 * JSON数据中的JsonElement有下面这四种类型:
 * 1.JsonPrimitive --　JsonElement的子类，该类对Java的基本类型及其对应的包装类进行了封装，并通过setValue方法为value赋值。
 * 2.JsonObject -- json对象类，包含了键值对，键是字符串类型，值是一个JsonElement。用 LinkedTreeMap<String, JsonElement> members来保存。
 * 3.JsonArray -- Json的数组包含的其实也是一个个Json串，用List<JsonElement>来添加json数组中的每个元素
 * 4.JsonNull -- 不可变类
 * <p>
 * Gson处理对象的几个要点
 * 1.没有必要用注解（@Expose 注解）指明某个字段是否会被序列化或者反序列化，所有包含在当前类（包括父类）中的字段都应该默认被序列化或者反序列化
 * 2.如果某个字段被 transient 这个Java关键词修饰，就不会被序列化或者反序列化
 * 3.当序列化的时候，如果对象的某个字段为null，是不会输出到Json字符串中的
 * 4.当反序列化的时候，某个字段在Json字符串中找不到对应的值，就会被赋值为null
 * 5.如果一个字段是 synthetic的,他会被忽视，也即是不应该被序列化或者反序列化
 * 6.内部类（或者anonymous class（匿名类），或者local class(局部类，可以理解为在方法内部声明的类)）的某个字段和外部类的某个字段一样的话，就会被忽视，不会被序列化或者反序列化
 * <p>
 * 注解：
 * 1.@SerializedName注解---指定该字段在json中对应的字段名称
 * 2.@Expose注解 ---指定该字段是否能够序列化或者反序列化，默认的是都支持（true）
 * 3.@Since --- 自从;从版本1.2之后才生效
 * 4.@Until --- 一直到;在0.9版本之前都是生效的
 * <p>
 * 内置类：
 * 1.JsonSerializer---接口，自定义序列化规则
 * 2.TypeAdapter---去掉了JsonElement中间层，直接用流来解析数据，极大程度上提高了解析效率
 * 3.JsonReader---读取json字符串并转化为一个token流，而JsonParser就是根据这个token流进行json语法分析并转为java对象
 * 4.TypeToken---Java的泛型在运行时会有类型擦除，Gson解析将得不到预期的类型，TypeToken就是解决这个问题的。解决方案就是：匿名类+反射。
 */
public final class GsonUtil {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() //不对没有用@Expose注解的属性进行操作
                .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
                .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
                .setPrettyPrinting() //对结果进行格式化，增加换行
                .disableHtmlEscaping() //防止特殊字符出现乱码
                //为某特定对象设置固定的序列或反序列方式，自定义Adapter需实现JsonSerializer或者JsonDeserializer接口
                //.registerTypeAdapter(User.class,new UserAdapter())
                .create();
    }

    /**
     * 对象转Json串
     *
     * @param object 对象
     * @return
     */
    public static String toJsonStr(Object object) {
        String json = null;
        if (gson != null) {
            json = gson.toJson(object);
        }
        return json;
    }

    /**
     * Json串转Bean对象
     *
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T toBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    public static <T> Map<String, T> toMap(String json) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            // 根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

}
