package com.kuma.im.serialize;

/**
 * 序列化接口
 *
 * @author kuma 2021-02-25
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     *
     * @see SerializerAlgorithm
     * @return byte, code of Algorithm
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制字节数组
     *
     * @param obj Object
     * @return byte[] 字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * 将字节数组转换成实体类实例
     *
     * @param clazz 范型
     * @param bytes 字节数组
     * @param <T> 范型类
     * @return instance of T
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
