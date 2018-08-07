/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation;

import org.junit.Test;

/**
 * @author kobe_t
 * @date 2018/7/6 0:03
 */
public class ClassLoaderTest {

    @Test
    public void test() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("current loader:" + loader);
        System.out.println("parent loader:" + loader.getParent());
        System.out.println("root loader:" + loader.getParent().getParent());
    }
}
