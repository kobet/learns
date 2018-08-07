/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation.cglig;


import com.example.learnjdk.reflation.PerformanceMonitor;
import com.example.learnjdk.reflation.service.FourmServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author kobe_t
 * @date 2018/7/16 19:35
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        PerformanceMonitor.begin(o.getClass().getName() + "." + method.getName());
        Object result = methodProxy.invokeSuper(o, args);
        PerformanceMonitor.end();

        return result;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        FourmServiceImpl fourmService = (FourmServiceImpl) proxy.getProxy(FourmServiceImpl.class);

        fourmService.removeFourm(11);

        fourmService.removeTopic(11);
    }
}
