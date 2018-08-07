/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kobe_t
 * @date 2018/7/20 15:33
 */
public class TestAtomic {

    public static void main(String[] args) {
        // 初始值
        AtomicInteger integer = new AtomicInteger(1);
        System.out.println("初始值atomicInteger:" + integer);

        // 以原子方式将当前值加1，注意这里返回的是自增前的值
        System.out.println("integer.getAndIncrement()= " + integer.getAndIncrement());
        System.out.println("自增后的值：" + integer);

        // 以原子方式将当前值减1，注意这里返回的是自减前的值
        System.out.println("integer.getAndDecrement()：" + integer.getAndDecrement());
        System.out.println("自减后的值" + integer);

        // 以原子方式将当前值与括号中的值相加，并返回结果相加后的结果
        System.out.println("integer.addAndGet: " + integer.addAndGet(10));
        System.out.println("相加后的值：" + integer);

        // 以原子方式将当前值与括号中的值相加，并返回结果计算之前的结果
        System.out.println("integer.getAndAdd：" + integer.getAndAdd(10));
        System.out.println("相加后的值：" + integer);

        // 如果输入的值等于预期的值，则以原子方式将该值设置成括号中的值
        System.out.println("设置值是否成功：" + integer.compareAndSet(1, 11));

        System.out.println("设置值是否成功：" + integer.compareAndSet(21, 22));
        System.out.println("成功设置之后的新值：" + integer);

    }
}
