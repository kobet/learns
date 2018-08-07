/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author kobe_t
 * @date 2018/7/20 21:55
 */
public class ThreadPool {

    public static void main(String[] args) {
        // 获取处理器的个数
        int cpuCount = Runtime.getRuntime().availableProcessors();

        // 核心线程,cpu的2倍
        int corePoolSize = cpuCount << 1;

        // 最大线程数
        int maximumPoolSize = cpuCount << 4;

        long keepAliveTime = 60L;

        int queueSize = 10000;

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("global-pool-%d").build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, cpuCount, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }


    public static void createDefaultThreadPool() {
        // 单线程：这种可能耗尽资源,造成OOM
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        // 固定大小的线程：类似newSingleThreadExecutor，会造成任务堆积，造成OOM，因为最大为Integer.MAX_VALUE
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);

        // 自适应线程池
        ExecutorService executorService3 = Executors.newCachedThreadPool();
    }
}
