/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

/**
 * @author kobe_t
 * @date 2018/7/16 17:56
 */
public class PerformanceMonitor {

    private static ThreadLocal<MethodPerformance> performances = new ThreadLocal<>();

    public static void begin(String method) {
        System.out.println("begin monitor");
        MethodPerformance methodPerformance = new MethodPerformance(method);

        performances.set(methodPerformance);

    }

    public static void end() {
        System.out.println("end monitor");

        MethodPerformance methodPerformance = performances.get();

        methodPerformance.printPerformance();
    }
}
