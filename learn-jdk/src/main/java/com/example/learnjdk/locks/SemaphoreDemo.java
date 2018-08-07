/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author kobe_t
 * @date 2018/7/20 10:06
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("global-pool-%d").build();

        Semaphore semaphore = new Semaphore(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("get permit");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
