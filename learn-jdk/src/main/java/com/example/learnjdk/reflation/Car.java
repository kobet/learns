/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

import lombok.Data;
import lombok.ToString;

/**
 * @author kobe_t
 * @date 2018/7/5 22:56
 */
@Data
@ToString
public class Car {

    /**
     * 无参构造
     */
    public Car() {

    }

    public Car(String brand, Integer color, int maxSpeed) {
        this.brand = brand;
        this.color = color;
        this.maxSpeed = maxSpeed;
    }

    private Video video;

    /**
     * 车牌
     */
    private String brand;

    /**
     * 颜色
     */
    private Integer color;

    /**
     * 最大速度
     */
    private int maxSpeed;

    /**
     * 无参方法
     */
    public void introduce() {
        System.out.println("brand:" + brand + ";color:" + color + ";maxSpeed:" + maxSpeed);
    }

    public void introduceWithParameters(String brand, int maxSpeed) {
        System.out.println("brand:" + brand + ";maxSpeed:" + maxSpeed);
    }

    private void innerMethod() {
        System.out.println("This is a private method");
    }


}
