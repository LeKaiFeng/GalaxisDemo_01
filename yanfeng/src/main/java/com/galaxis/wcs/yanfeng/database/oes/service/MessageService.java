package com.galaxis.wcs.yanfeng.database.oes.service;

import java.util.List;

public interface MessageService {

    /**
     * 向队列头部设值
     *
     * @param key   键
     * @param value 值
     * @return 序号
     */
    Long leftPush(String key, Object value);

    /**
     * 向队列尾部设值
     *
     * @param key   键
     * @param value 值
     * @return 序号
     */
    Long rightPush(String key, Object value);

    /**
     * 从队列头部取值并删除该值
     *
     * @param key   键
     * @param clazz 值的类型
     * @param <T>   值的类型
     * @return 值
     */
    <T> T leftPop(String key, Class<T> clazz);

    /**
     * 从队列头部取值但不删除该值
     *
     * @param key   键
     * @param clazz 值的类型
     * @param <T>   值的类型
     * @return 值
     */
    <T> T leftGet(String key, Class<T> clazz);

    /**
     * 获取序列
     *
     * @param key key
     * @return 序列
     */
    Long getSeq(String key);

    /**
     * 获取list
     *
     * @param key   键
     * @param clear 获取后是否清空
     * @return 返回获取的list
     */
    List<Integer> getList(String key, boolean clear);
}
