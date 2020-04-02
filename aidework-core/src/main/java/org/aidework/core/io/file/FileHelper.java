package org.aidework.core.io.file;

import org.aidework.core.io.IOHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件操作工具类
 * 完成文件非IO的操作
 *
 * @author Caysen
 *
 * @Date 2020-02-18
 */
public class FileHelper {

    private FileHelper(){

    }

    /**
     * 获取指定路径下的文件及文件夹路径
     * 此方法不包含子目录中的文件
     * @param path
     * @return
     */
    public static List<File> listFiles(String path){
        return listFiles(new File(path));
    }

    /**
     * 获取指定文件夹中的文件及文件夹路径
     * 此方法不包含子目录中的文件
     * @param file
     * @return
     */
    public static List<File> listFiles(File file){

        List<String> list=new ArrayList<>();

        return Arrays.asList(file.listFiles());
    }

    /**
     * 获取指定路径下的文件及文件夹路径
     * 此方法包含所有子目录中的文件及文件夹
     * @param path
     * @return
     */
    public static List<File> listAllFiles(String path){
        return listAllFiles(new File(path));
    }

    /**
     * 获取指定文件夹中的文件及文件夹路径
     * 此方法包含所有子目录中的文件及文件夹
     * @param file
     * @return
     */
    public static List<File> listAllFiles(File file){
        List<File> list=new ArrayList<>();
        if (!file.isFile()) {
            List<File> files = listFiles(file);
            for (File f : files) {
                listFiles(f);
            }
        } else {
            list.add(file);
        }
        return list;
    }

    /**
     * 获取指定路径下的文件及文件夹路径
     * 此方法包含所有子目录中的文件及文件夹
     * 同时可通过regex匹配文件名来筛选
     * @param file
     * @param regex
     * @return
     */
    public static List<File> listAllFiles(File file,String regex){
        List<File> list=new ArrayList<>();
        if (!file.isFile()) {
            List<File> files = listFiles(file);
            for (File f : files) {
                listFiles(f);
            }
        } else {
            if(file.getPath().matches(regex)){
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 对指定路径的文件/文件夹重命名
     * @param path
     * @param name
     */
    public static void rename(String path,String name){
        rename(new File(path),name);
    }

    /**
     * 对指定的文件/文件夹重命名
     * @param file
     * @param name
     */
    public static void rename(File file,String name){
        String dir=getParentDir(file);
        file.renameTo(new File(dir+File.separator+name));
    }

    /**
     * 获取指定路径文件/文件夹的父路径
     * @param path
     * @return
     */
    public static String getParentDir(String path){
        return getParentDir(path);
    }

    /**
     * 获取指定文件/文件夹的父路径
     * @param file
     * @return
     */
    public static String getParentDir(File file){
        return file.getParent();
    }

    /**
     * 从源路径中复制文件到目标路径中
     * 调用{@link #copy(File, File)}完成操作
     * @param src 源路径
     * @param target 目标路径
     * @return 复制是否成功
     */
    public static boolean copy(String src,String target){
        if(src==null||target==null){
            return false;
        }
        return copy(new File(src),new File(target));
    }

    /**
     * 将源文件复制到目标文件中
     * @param src 源文件
     * @param target 目标文件
     * @return 复制是否成功
     */
    public static boolean copy(File src,File target){
        if(src==null||target==null){
            return false;
        }
        /*
         * 为保证复制的完整性，为使用字节流对字节流的输出复制，而是先读取到完整数据后，再一次性写入。
         */
        return IOHelper.write(IOHelper.readBytes(src),target);
    }

    /**
     * 移动文件操作
     * 将源路径文件复制到目标路径文件中后再删除源文件
     * 此方法将传入参数路径包装为File对象，再调用{@link #moveTo(File, File)}方法返回操作结果
     * @param src 源文件路径
     * @param target 目标文件路径
     * @return 剪切是否成功
     */
    public static boolean moveTo(String src,String target){
        if(src==null||target==null){
            return false;
        }
        return moveTo(new File(src),new File(target));
    }

    /**
     * 将指定文件移至指定文件中
     */
    public static boolean moveTo(File src,File target){
        if(src==null||target==null){
            return false;
        }
        boolean success=IOHelper.write(IOHelper.readBytes(src),target);
        delete(src);
        return success;
    }

    /**
     * 删除文件操作
     * 将参数路径的文件删除掉
     * 该方法将调用{@link #delete(File)}返回操作结果
     * @param path 需要被删除的文件路径
     * @return 删除是否成功
     */
    public static boolean delete(String path){
        if(path==null){
            return false;
        }
        return delete(new File(path));
    }

    /**
     * 删除文件操作
     * 将参数File指针指向的文件删除
     * @param file 指向某个文件的File对象
     * @return 删除是否成功
     */
    public static boolean delete(File file){
        if(file==null){
            return false;
        }
        if(file.isDirectory()){
            return deleteDir(file);
        }else{
            return deleteFile(file);
        }
    }

    /**
     * 删除指定目录
     * @param path 目录路径
     * @return 删除是否成功
     */
    public static boolean deleteDir(String path){
        if(path==null){
            return false;
        }
        return deleteDir(new File(path));
    }

    /**
     * 删除指定目录
     * @param file 目录文件对象
     * @return 删除是否成功
     */
    public static boolean deleteDir(File file){
        if(file.isDirectory()){
            for(File f:file.listFiles()){
                if(f.isDirectory()){
                    deleteDir(f);
                }else{
                    deleteFile(f);
                }
            }
            file.delete();
        }else{
            deleteFile(file);
        }
        return true;
    }


    /**
     * 删除文件
     * @param path 文件路径
     * @return 删除是否成功
     */
    public static boolean deleteFile(String path){
        if(path==null){
            return false;
        }
        return deleteFile(new File(path));
    }

    /**
     * 删除文件
     * @param file 文件对象
     * @return 删除是否成功
     */
    public static boolean deleteFile(File file){
        if(file.isFile())
            return file.delete();
        return false;
    }


    /**
     * 确定指定文件是否存在，不存在则创建文件
     * @param file 指定文件
     */
    public static void ensure(File file){
        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 确保文件存在
     * @param path 文件路径
     */
    public static void ensureFile(String path){
        ensureFile(new File(path));
    }
    /**
     * 确保文件存在
     * @param file 文件
     */
    public static void ensureFile(File file){
        if(!file.exists()){
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 确保目录存在
     * @param path 目录路径
     */
    public static void ensureDirectory(String path){
        ensureDirectory(new File(path));
    }

    /**
     * 确保目录存在
     * @param file 目录
     */
    public static void ensureDirectory(File file){
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取文件后缀名
     * @param path 文件路径
     * @return
     */
    public static String getFileSuffix(String path){
        int index=path.lastIndexOf(".");
        if(index==-1)
            return null;
        return path.substring(index+1,path.length());
    }

    /**
     * 获取文件后缀名
     * @param file 文件
     * @return
     */
    public static String getFileSuffix(File file){

        return getFileSuffix(file.getName());
    }

    public static String getFileName(String path){
        if(path.indexOf("/")==-1 && path.indexOf("\\")==-1)
            return path;
        int i=path.lastIndexOf("/");
        int j=path.lastIndexOf("\\");
        int n=0;
        if(i>j)
            n=i;
        else
            n=j;
        return path.substring(n+1,path.length());
    }


}
