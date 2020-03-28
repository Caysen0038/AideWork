package org.aidework.core.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IO工具类
 * 完成指定的IO操作
 */
public class IOHelper {
    /**
     * 默认解析编码，当方法中 charset 参数缺省或为null时使用该编码
     */
    public static final String DEFAULT_CHARSET="UTF-8";

    /*
     *  静态工具类，禁止实例化
     */
    private IOHelper(){

    }

    /**
     * 读取指定路径文件的字节数据
     * 该方法会直接将path实例化为File对象，然后调用{@link #readBytes(File)}方法读取字节
     * @param path 目标文件路径
     * @return 读取后的byte型数组
     */
    public static byte[] readBytes(String path){
        // 直接封装为File对象，然后调用重载的方法。
        return readBytes(new File(path));
    }

    /**
     * 读取指定文件的字节数据
     * 该方法将实例化InputStream对象，然后调用{@link #readBytes(InputStream)}方法读取字节
     * @param file 目标文件
     * @return 读取后的byte型数组
     */
    public static byte[] readBytes(File file){
        InputStream input=null;
        try {
            input=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] data=readBytes(input);
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    /**
     * 通过传入的InputStream对象，读取并返回流中的字节数据
     * @param input 传入的InputStream对象，此方法不负责关闭传入的字节流，关闭操作应由调用者完成
     * @return 从字节流中读取到的字节，如果传入的InputStream为null，则直接返回null
     */
    public static byte[] readBytes(InputStream input){
        if(input!=null){
            int temp;
            List<Byte> list=new ArrayList<>();
            try {
                while((temp=input.read())!=-1){
                    list.add((byte)temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data=new byte[list.size()];
            int i=0;
            for(Byte b:list){
                data[i++]=b;
            }
            return data;
        }
        return null;
    }

    /**
     * 从指定路径中的文件中读取数据并转换为String对象
     * 该方法会调用{@link #readBytes(String)}获取字节数据，
     * 然后使用String中默认的字符编码转换为字符串
     *
     * @param path 指定文件数据
     * @return 从文件中读取的String对象
     */
    public static String read(String path){
        return new String(readBytes(path));
    }

    /**
     * 从指定路径中的文件中读取数据并转换为String对象
     * 该方法直接返回{@link #read(String, String)}操作后的数据
     * @param path 指定文件的路径
     * @param charset 指定byte转换String的编码格式
     * @return 文件数据按指定charset转换后的String对象
     */
    public static String read(String path,String charset){
        return read(new File(path),charset);
    }

    /**
     * 通过传入的File对象读取调用{@link #readBytes(File)}得到文件字节
     * 之后转换为Sting对象并将其返回
     * @param file 传入的File对象
     * @return 从file对象中读取并转换后的String
     */
    public static String read(File file){
        return new String(readBytes(file));
    }

    /**
     * 读取传入的File对象字节并按照指定的charset编码转换为String并将其返回
     * @param file 传入的File对象
     * @param charset 指定的字符编码
     * @return
     */
    public static String read(File file,String charset){
        if(charset==null){
            return read(file);
        }
        try {
            return new String(readBytes(file),charset);
        } catch (UnsupportedEncodingException e) {
            return read(file);
        }
    }

    /**
     * 传入一个已经打开的数据流，读取字节并转换为String返回
     * @param input 被传入的一个已经打开的字节流，此方法不负责关闭字节流，关闭操作应由调用者完成
     * @return 从字节流中读取到的Strin数据
     */
    public static String read(InputStream input){
        return new String(readBytes(input));
    }

    /**
     * 传入一个已经打开的数据流，读取字节并按照指定的charset转换为String返回
     * @param input 被传入的一个已经打开的字节流，此方法不负责关闭字节流，关闭操作应由调用者完成
     * @param charset 指定的字符编码
     * @return 从字节流中读取到的String数据
     */
    public static String readContent(InputStream input,String charset){
        if(charset==null){
            return read(input);
        }
        try {
            return new String(readBytes(input),charset);
        } catch (UnsupportedEncodingException e) {
            return read(input);
        }
    }



    /**
     * 将指定的String型数据写入进指定路径文件中
     * @param content 需要写入的String数据
     * @param path 指定的写入数据的文件路径，如果路径文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,String path){
        return write(content,path,false);
    }

    /**
     * 将指定的String型数据写入进指定路径文件中
     * @param content 需要写入的String数据
     * @param path 指定的写入数据的文件路径，如果路径文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param append 是否为追加数据
     * @return
     */
    public static boolean write(String content,String path,boolean append){
        
        return write(content.getBytes(),path,append);
    }

    /**
     * 将指定的String型数据按照指定字符编码写入进指定路径文件中
     * @param content 需要写入的String数据
     * @param path 指定的写入数据的文件路径，如果路径文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param charset 指定字符编码
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,String path,String charset){
        return write(content,path,charset,false);
    }

    /**
     * 将指定的String型数据按照指定字符编码写入进指定路径文件中
     * @param content 需要写入的String数据
     * @param path 指定的写入数据的文件路径，如果路径文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param charset 指定字符编码
     * @param append 是否为追加数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,String path,String charset,boolean append){
        if(charset==null){
            charset=DEFAULT_CHARSET;
        }
        try {
            return write(content.getBytes(charset),path,append);
        } catch (UnsupportedEncodingException e) {
            return write(content.getBytes(),path,append);
        }
    }

    /**
     * 将指定的String型数据写入进指定File中
     * @param content 需要写入的String数据
     * @param file 存储数据的File对象，如果文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,File file){
        return write(content,file,false);
    }

    /**
     * 将指定的String型数据写入进指定File中
     * @param content 需要写入的String数据
     * @param file 存储数据的File对象，如果文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param append 是否为追加数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,File file,boolean append){
        if(content==null){
            return false;
        }
        return write(content.getBytes(),file,append);
    }

    /**
     * 将指定的String型数据按照指定字符编码写入进指定File中
     * @param content 需要写入的String数据
     * @param file 存储数据的File对象，如果文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param charset 指定字符编码
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,File file,String charset){
        return write(content,file,charset,false);
    }

    /**
     * 将指定的String型数据按照指定字符编码写入进指定File中
     * @param content 需要写入的String数据
     * @param file 存储数据的File对象，如果文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param charset 指定字符编码
     * @param append 是否为追加数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(String content,File file,String charset,boolean append){
        if(content==null){
            return false;
        }
        if(charset==null){
            charset=DEFAULT_CHARSET;
        }
        try {
            return write(content.getBytes(charset),file,append);
        } catch (UnsupportedEncodingException e) {
            return write(content.getBytes(),file,append);
        }
    }
    
    /**
     * 将数据写入指定路径的文件中
     * @param data 数据
     * @param path 文件路径
     * @return 写入是否成功
     */
    public static boolean write(byte[] data,String path){
        return write(data,path,false);
    }

    /**
     * 将数据写入指定路径的文件中
     * @param data 数据
     * @param path 文件路径
     * @param append 是否为追加数据
     * @return 写入是否成功
     */
    public static boolean write(byte[] data,String path,boolean append){
        return write(data,new File(path),append);
    }

    /**
     * 将数据写入指定File中
     * @param data 数据
     * @param file 文件指针
     * @return 写入是否成功
     */
    public static boolean write(byte[] data,File file){
        return write(data,file,false);
    }

    /**
     * 将数据写入指定File中
     * @param data 数据
     * @param file 文件指针
     * @param append 是否为追加数据
     * @return 写入是否成功
     */
    public static boolean write(byte[] data,File file,boolean append){
        if(file==null){
            return false;
        }
        FileHelper.ensureFile(file);
        OutputStream out=null;
        try {
            out=new FileOutputStream(file,append);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        boolean success=write(data,out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean write(InputStream input,String path){
        return write(input,path,false);
    }

    /**
     * 写入文件操作
     * 从一个已经打开的字节流中读取到数据然后写入到指定path中
     * @param input 被传入的一个已经打开的字节流，此方法不负责关闭字节流，关闭操作应由调用者完成
     * @param path 指定的写入数据的文件路径，如果路径文件不存在或被删除，则会创建一个新的同名文件写入数据
     * @param append 是否为追加数据
     * @return 写入文件操作是否成功
     */
    public static boolean write(InputStream input,String path,boolean append){
        File file=new File(path);
        FileHelper.ensureFile(file);
        OutputStream output=null;
        try {
            output = new FileOutputStream(file,append);
        } catch (FileNotFoundException e1) {
            System.err.println("The file path :"+file.getPath()+" is invalid");
        }
        boolean success=write(input,output);
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 将数据写入指定的输出流中
     * @param data 数据
     * @param output 指定的输出流
     * @return 写入是否成功
     */
    public static boolean write(byte[] data,OutputStream output){
        if(output!=null){
            try {

                output.write(data);
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 将指定输入流的数据写入指定的输出流
     * @param input 指定的输入流
     * @param output 指定的输出流
     * @return 写入是否成功
     */
    public static boolean write(InputStream input,OutputStream output){
        if(input==null||output==null){
            return false;
        }
        int data;
        try {
            while((data=input.read())!=-1){
                output.write(data);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 将指定数据追加至指定路径的文件末尾
     * @param data 追加数据
     * @param path 指定文件路径
     * @return 是否追加成功
     */
    public static boolean append(byte[] data,String path){
        return write(data,path,true);
    }

    /**
     * 将指定数据追加至指定的文件末尾
     * @param data 追加数据
     * @param file 指定文件
     * @return 是否追加成功
     */
    public static boolean append(byte[] data,File file){
        return write(data,file,true);
    }

}
