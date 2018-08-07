/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kobe_t
 * @date 2018/7/5 23:06
 */
public class ReflectTest {

    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Car car = initByDefaultConst();
        car.introduce();
    }

    private Car initByDefaultConst() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        // 通过类加载器获取Car类对象
        // 第1种获取Class的方式
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        Class clazz = loader.loadClass("com.example.learnjdk.reflation.Car");

        // 第2种获取Class的方式
        Class clazz = Class.forName("com.example.learnjdk.reflation.Car");

        // 获取类的默认构造器对象并通过它实例化Car
        Constructor constructor = clazz.getDeclaredConstructor((Class[]) null);
        Car car = (Car) constructor.newInstance();

        // 通过反射设置属性
        Method setBrand = clazz.getMethod("setBrand", String.class);
        setBrand.invoke(car, "buck");

        Method setColor = clazz.getMethod("setColor", Integer.class);
        setColor.invoke(car, 1);

        Method setMaxSpeed = clazz.getMethod("setMaxSpeed", int.class);
        setMaxSpeed.invoke(car, 2);

        // 无参方法
        Method introduce = clazz.getMethod("introduce", null);
        introduce.invoke(car, null);

        // 有参方法
        Method introduceWithParameters = clazz.getMethod("introduceWithParameters", String.class, int.class);
        introduceWithParameters.invoke(car, "mvm", 50);

        Field brand = clazz.getDeclaredField("brand");
        // 设置私有属性访问
        brand.setAccessible(true);
        brand.set(car, "大众");

        // 基础类型
        Field maxSpeed = clazz.getDeclaredField("maxSpeed");
        maxSpeed.setAccessible(true);
//        maxSpeed.setInt(car, 33);
        maxSpeed.set(car, 33);

        System.out.println(car);

        System.out.println(StringUtils.capitalize("cap"));

        return car;
    }

    @Test
    public void demoTest() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        // 获取Class对象
        Class clazz = Class.forName("com.example.learnjdk.reflation.Car");

        // 获取类的默认构造器对象并通过它实例化Car
        Constructor<Car> constructor = clazz.getConstructor(null);
        Car car = constructor.newInstance();

        // 通过反射设置属性
        Method setBrand = clazz.getDeclaredMethod("setBrand", String.class);
        setBrand.invoke(car, "大众");

        // 通过反射调用方法
        Method introduce = clazz.getMethod("introduce", null);
        introduce.invoke(car, null);

        // 私有方法
        Method innerMethod = clazz.getDeclaredMethod("innerMethod", null);
        innerMethod.setAccessible(true);
        innerMethod.invoke(car, null);

        System.out.println(car);

        // 获取Class类信息
        System.out.println("类字面值方式：" + Car.class.getName());
        System.out.println("对象方式：" + car.getClass().getName());


        // 通过构造函数，实例化对象
        //获取所有构造函数
        Constructor[] constructors = clazz.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            System.out.println(constructors[i]);
        }

        // 根据指定类型获取无参构造函数
        Constructor<Car> defaultConstructor = clazz.getConstructor(null);
        System.out.println("默认构造函数：" + defaultConstructor);
        Car defaultCar = defaultConstructor.newInstance(null);
        // 相当于new Car()
        System.out.println("默认构造函数创建的对象：" + defaultCar);

        // 根据指定类型获取有参构造函数
        Constructor<Car> constructorHasParameters = clazz.getConstructor(String.class, Integer.class, int.class);
        System.out.println("有参构造：" + constructorHasParameters);
        Car carHasParameters = constructorHasParameters.newInstance("宝马", 2, 5000);
        // 相当于new Car("宝马", 2, 5000)
        System.out.println("有参构造的函数：" + carHasParameters);


        // 获取反射的所有的方法，并调用
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println("反射获取的方法：" + methods[i]);
            // 以 String 形式返回此 Method 对象表示的方法名称。
            System.out.println("方法名称：" + methods[i].getName());

            Class[] parameterTypes = methods[i].getParameterTypes();
            // 按照声明顺序返回 Class 对象的数组，这些对象描述了此 Method 对象所表示的方法的形参类型。
            // 如果底层方法不带参数，则返回长度为 0 的数组。
            for (int j = 0; j < parameterTypes.length; j++) {
                System.out.println("方法参数类型：" + parameterTypes[j]);
            }

            // 返回一个 Class 对象，该对象描述了此 Method 对象所表示的方法的正式返回类型。
            Class<?> returnType = methods[i].getReturnType();
            System.out.println("方法返回类型：" + returnType);

            // 获取所以的字段信息
            Field[] fields = clazz.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                System.out.println("属性" + j + "：" + fields[j].getName());
            }

            // 设置属性值brand
            Field brand = clazz.getDeclaredField("brand");
            // 设置访问private
            brand.setAccessible(true);
            brand.set(car, "奔驰");
            // 设置maxSpeed
            Field maxSpeed = clazz.getDeclaredField("maxSpeed");
            maxSpeed.setAccessible(true);
            maxSpeed.set(car, 500);
            // 对于基本类型，有对应的setType方法;比如int,setInt;boolean,setBoolean
            maxSpeed.setInt(car, 600);
            System.out.println(car);

            // 获取语言修饰符
            int modifier = brand.getModifiers();
            System.out.println(brand.getName() + "的修饰符是" + modifier);

            // 获取方法修饰符
            int methodModifier = setBrand.getModifiers();
            System.out.println(setBrand.getName() + "的修饰符是" + methodModifier);

        }

    }
}
