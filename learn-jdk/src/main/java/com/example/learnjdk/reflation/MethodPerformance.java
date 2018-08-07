/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

/**
 * @author kobe_t
 * @date 2018/7/16 17:50
 */
public class MethodPerformance {

    private long begin;

    private long end;

    private String serviceMethod;

    public MethodPerformance(String serviceMethod) {
        this.begin = System.currentTimeMillis();

        this.serviceMethod = serviceMethod;
    }

    public void printPerformance() {
        end = System.currentTimeMillis();

        long takeTime = end - begin;

        System.out.println(serviceMethod + "花费" + takeTime + "毫秒");
    }

}
