/**
 * @copyright remark holdings
 */
package com.example.learnjdk.reflation.service;

/**
 * @author kobe_t
 * @date 2018/7/16 17:45
 */
public class FourmServiceImpl implements ForumService {

    @Override
    public void removeFourm(int fourmId) {
        System.out.println("模拟删除Fourm记录:" + fourmId);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTopic(int topicId) {
        System.out.println("模拟删除Topic记录:" + topicId);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
