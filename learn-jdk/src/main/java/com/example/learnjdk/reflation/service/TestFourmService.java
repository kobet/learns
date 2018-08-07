/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation.service;

import com.example.learnjdk.reflation.PerformanceHandler;
import com.example.learnjdk.reflation.PerformanceMonitor;

import java.lang.reflect.Proxy;

/**
 * @author kobe_t
 * @date 2018/7/16 19:01
 */
public class TestFourmService {

    public static void main(String[] args) {
        ForumService target = new FourmServiceImpl();

        PerformanceHandler handler = new PerformanceHandler(target);

        ForumService proxy = (ForumService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);

        proxy.removeFourm(10);

        proxy.removeTopic(10);
    }
}
