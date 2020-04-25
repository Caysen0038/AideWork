package org.aidework.core.object;

/**
 * 复印机抽象基类
 * 其实现类针对各种Object实现了复制克隆操作
 */
public abstract class Duplicator {

    /**
     * 对指定对象src进行复制操作，返回指定类型的结果对象
     * @param src 源对象
     * @param target 结果对象
     * @param <T> 结果对象类型
     * @return 结果对象
     */
    public abstract <T> T duplicate(Object src,Class<T> target);

}
