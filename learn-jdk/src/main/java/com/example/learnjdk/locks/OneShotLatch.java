/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author kobe_t
 * @date 2018/7/19 17:39
 */
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() {
        // 释放
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        // 获取锁
        sync.acquireSharedInterruptibly(0);
    }


    private class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int ignored) {
            // 如果闭锁是开的 state == 1,那么这个操作将成功,否则将失败
            return getState() == 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignored) {
            // 打开闭锁
            setState(1);
            // 现在其它线程可以获取该闭锁
            return true;
        }
    }


}
