/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kobe_t
 * @date 2018/7/6 16:59
 */
public class ReflectionsTest {

    @Test
    public void invokeSetter() throws InvocationTargetException, IllegalAccessException {
        // 设置直接属性
        Car car = new Car();
        String propertyName = "brand";
        String value = "宝马";
//        Reflections.invokeSetter(car, propertyName, value);
//        System.out.println(car);
//
//        // 设置继承属性
//        BigCar bigCar = new BigCar();
//        propertyName = "height";
//        Double heightValue = 1d;
//        String superPropertyName = "brand";
//        String superValue = "大众";
//        Reflections.invokeSetter(bigCar, propertyName, heightValue);
//        Reflections.invokeSetter(bigCar, superPropertyName, superValue);
//        System.out.println(bigCar + "," + bigCar.getBrand());

        // 设置级联属性
        propertyName = "video.voice";
        car.setVideo(new Video());
        int voice = 11;
        Reflections.invokeSetter(car, propertyName, voice);

        System.out.println(car);


        List<String> aList = new ArrayList<>();
        aList.add("a");
        aList.add("b");
        List<String> bList = Arrays.asList("a", "b", "c");
        bList = aList;
        aList.clear();
        bList.add("d");
        aList = null;
        System.out.println("bList is :" + bList);
        System.out.println("aList is :" + aList);
    }
}
