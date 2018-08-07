/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author kobe_t
 * @date 2018/7/16 18:52
 */
public class PerformanceHandler implements InvocationHandler {

    private Object target;

    public PerformanceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法名称：" + target.getClass().getName() + "." + method.getName());

        PerformanceMonitor.begin(target.getClass().getName() + "." + method.getName());
        Object object = method.invoke(target, args);
        PerformanceMonitor.end();
        return object;
    }
}
