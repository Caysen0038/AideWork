package org.aidework.core.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Bean Duplicator
 * 针对Java Bean的复制操作，复制其属性
 * 相对于基类Duplicator,扩展了duplicate方法可传入类型信息返回目标类型
 */
public class BeanDuplicator extends Duplicator {


    @Override
    public <T> T duplicate(Object src,Class<T> clazz){
        T obj=null;
        try {
            obj=clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(obj==null){
            return null;
        }
        if(duplicate(src,obj)){
            return obj;
        }
        return null;
    }

    /**
     * 将指定对象src属性值(若target中存在相同值)复制进目标对象target中.
     * @param src 源对象
     * @param target 目标对象
     * @return 复制是否出错
     */
    public boolean duplicate(Object src, Object target) {
        if(src==null||target==null){
            return false;
        }
        /**
         * 存储类型中的属性信息
         */
        Map<String,Field> srcMap=new HashMap<>();
        for(Field f:src.getClass().getDeclaredFields()){
            srcMap.put(f.getName(),f);
        }
        Map<String,Field> targetMap=new HashMap<>();
        for(Field f:target.getClass().getDeclaredFields()){
            targetMap.put(f.getName(),f);
        }
        /**
         * 判断两个类中能够有效转换的类属性
         */
        Field sf,tf;
        for(String key:srcMap.keySet()){
            sf=srcMap.get(key);
            tf=targetMap.get(key);
            if(sf!=null && tf!=null && srcMap.get(key).getName().equals(targetMap.get(key).getName()) &&
                    srcMap.get(key).getType().equals(targetMap.get(key).getType())){
                sf.setAccessible(true);
                tf.setAccessible(true);
                try {
                    Object obj=sf.get(src);
                    tf.set(target,obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public <T> List<T> duplicate(List list, Class<T> clazz){
        T target;
        List<T> newList=new ArrayList<>();
        try{
            for(Object obj:list){
                target=clazz.newInstance();
                this.duplicate(obj,target);
                newList.add(target);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return newList;
    }
}
