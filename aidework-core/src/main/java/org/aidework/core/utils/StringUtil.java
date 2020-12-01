package org.aidework.core.utils;

import java.util.Random;

/**
 * 字符串操作工具类
 *
 * @author bkc
 */
public class StringUtil {

    /**
     * 获取随机数字字符串
     * @param len
     * @return
     */
    public static String getRandomNumber(int len){
        StringBuilder sb=new StringBuilder();
        Random random=new Random();
        while(len-->0){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null || str.length()==0;
    }

    /**
     * 是否为Integer
     * @param str
     * @return
     */
    public static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为Float
     * @param str
     * @return
     */
    public static boolean isFloat(String str){
        try{
            Float.parseFloat(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为Short
     * @param str
     * @return
     */
    public static boolean isShort(String str){
        try{
            Short.parseShort(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为Byte
     * @param str
     * @return
     */
    public static boolean isByte(String str){
        try{
            Byte.parseByte(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为Long
     * @param str
     * @return
     */
    public static boolean isLong(String str){
        try{
            Long.parseLong(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为Double
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        return isInteger(str)
                || isFloat(str)
                || isDouble(str)
                || isLong(str)
                || isShort(str)
                || isByte(str);
    }
}
