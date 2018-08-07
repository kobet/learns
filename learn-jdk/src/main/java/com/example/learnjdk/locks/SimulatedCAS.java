/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;


/**
 * @author kobe_t
 * @date 2018/7/20 14:36
 */
public class SimulatedCAS {

    private int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        // 比较并交换
        if (oldValue == expectedValue) {
            value = newValue;
        }

        return oldValue;
    }

    public synchronized int get() {
        return value;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
