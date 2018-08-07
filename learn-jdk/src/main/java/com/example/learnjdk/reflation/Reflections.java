package com.example.learnjdk.reflation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射工具类.
 * 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 */
@Slf4j
public class Reflections {

    /**
     * set方法前缀
     */
    private static final String SETTER_PREFIX = "set";

    /**
     * get方法前缀
     */
    private static final String GETTER_PREFIX = "get";

    /**
     * 内部类路径
     */
    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * 字段属性分隔符
     */
    private static final String PROPERTY_SAPERATOR = ".";

    /**
     * 默认参数
     */
    private static final Class<?>[] PARAMETER_TYPES = new Class[]{};

    /**
     * 默认参数
     */
    private static final Object[] PARAMETER_ARGS = new Object[]{};

    private static final Pattern LIST_PARAM_PAT = Pattern.compile("\\[[0-9]+\\]");

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     * 支持list元素，如: addressInfo.selfAddress[1].name
     */
    public static Object invokeGetter(Object obj, String propertyName) throws InvocationTargetException, IllegalAccessException {

        if (null == obj || StringUtils.isBlank(propertyName)) {
            return null;
        }
        Object object = obj;
        // 多级属性
        for (String name : StringUtils.split(propertyName, PROPERTY_SAPERATOR)) {

            // 兼容List格式：name[1]
            Matcher matcher = LIST_PARAM_PAT.matcher(name);
            if (matcher.find()) {
                name = StringUtils.replace(name, matcher.group(), "");
            }

            object = invokeMethod(object, GETTER_PREFIX + StringUtils.capitalize(name), PARAMETER_TYPES, PARAMETER_ARGS);
            // 如果上级对象为空则返回null
            if (null == object) {
                return null;
            }
            if (object instanceof List) {
                String matchStr = matcher.group();
                Integer ind = Integer.valueOf(matchStr.substring(1, matchStr.length() - 1));
                List list = (List) object;
                if (list.size() <= ind) {
                    return null;
                }
                object = list.get(ind);
            }

        }
        return object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) throws InvocationTargetException, IllegalAccessException {

        if (null == obj || StringUtils.isBlank(propertyName)) {
            return;
        }
        Object object = obj;
        String[] names = StringUtils.split(propertyName, PROPERTY_SAPERATOR);
        for (int i = 0; i < names.length; i++) {
            // 获取上级对象
            if (i < names.length - 1) {
                object = invokeMethod(object, GETTER_PREFIX + StringUtils.capitalize(names[i]), PARAMETER_TYPES, PARAMETER_ARGS);
                // 如果上级对象不存在则不设置
                if (null == object) {
                    return;
                }
                continue;
            }
            invokeMethodByName(object, SETTER_PREFIX + StringUtils.capitalize(names[i]), new Object[]{value});
        }
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) throws IllegalAccessException {

        // 参数为空
        if (null == obj || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = getAccessibleField(obj, fieldName);
        // 字段为空
        if (field == null) {
            return null;
        }
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws IllegalAccessException {

        if (null == obj || StringUtils.isBlank(fieldName) || null == value) {
            return;
        }
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            return;
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
     * 同时匹配方法名+参数类型，
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) throws InvocationTargetException, IllegalAccessException {

        if (null == obj || StringUtils.isBlank(methodName)) {
            return null;
        }
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符，
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
     * 只匹配函数名，如果有多个同名函数调用第一个。
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) throws InvocationTargetException, IllegalAccessException {

        if (null == obj || StringUtils.isBlank(methodName)) {
            return null;
        }
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 根据调用静态方法
     */
    public static Object invokeStaticMethodByName(final String classPath, final String methodName, final Object[] args) throws Exception {

        if (StringUtils.isBlank(classPath) || StringUtils.isBlank(methodName)) {
            return null;
        }
        Class<?> clzz = getClass(classPath);
        if (null == clzz) {
            return null;
        }
        try {
            Method method = clzz.getMethod(methodName, String.class);
            if (method == null) {
                return null;
            }
            return method.invoke(null, args);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * <p>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {

        if (null == obj || StringUtils.isBlank(fieldName)) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {

        if (null == obj || StringUtils.isBlank(methodName)) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 只匹配函数名。
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {

        if (null == obj || StringUtils.isBlank(methodName)) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {

        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }


    /**
     * 通过发射查询对象的方法
     *
     * @param clazz          对象的class
     * @param methodName     方法名称
     * @param parameterTypes 方法参数
     * @return 方法对象
     */
    public static Method getMethod(final Class clazz, final String methodName,
                                   final Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {

        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
     * 如无法找到, 返回Object.class.
     * eg.
     * public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * <p>
     * 如public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    public static Class getClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {

            log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {

            log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {

            log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {

        if (null == instance) {
            return null;
        }
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {

            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * 获取Class对象
     */
    public static Class<?> getClass(String calssPath) {

        if (StringUtils.isBlank(calssPath)) {
            return null;
        }
        try {
            return Class.forName(calssPath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 创建对象
     */
    public static Object newInstance(Constructor constructor, Object... initargs) {

        if (null == constructor) {
            return null;
        }
        try {
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            return null;
        }
    }
}
