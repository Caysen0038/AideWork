package org.aidework.core.data;

public class DataHelper {

    public static String byte2Hex(byte[] data){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < data.length; i++) {
            temp = Integer.toHexString(data[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static int byte2Int(byte[] data){

        return (data[0]&0xff<<24)+
                (data[1]&0xff<<16)+
                (data[2]&0xff<<8)+
                (data[3]&0xff<<0);
    }

    public static byte[] int2Byte(int n){
        byte[] data=new byte[4];
        data[0]= (byte) (n>>24&0xff);
        data[1]= (byte) (n>>16&0xff);
        data[2]= (byte) (n>>8&0xff);
        data[3]= (byte) (n>>0&0xff);
        return data;
    }

}
