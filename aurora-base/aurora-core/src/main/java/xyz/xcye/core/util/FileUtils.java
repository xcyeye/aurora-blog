package xyz.xcye.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 与文件相关的工具类，对hutool工具包的进一步封装
 *
 * @author qsyyke
 */


public class FileUtils {

    /**
     * 返回文件名，包含扩展名
     * @param path 文件路径，网页路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        return cn.hutool.core.io.FileUtil.getName(path);
    }

    /**
     * 返回主文件名 /usr/bb/a.jpg 返回a
     * @param path 文件路径，网页路径
     * @return 主文件名，不含扩展
     */
    public static String getMainName(String path) {
        return cn.hutool.core.io.FileUtil.mainName(path);
    }

    /**
     * 返回文件扩展名，不包含.
     * @param path 文件路径，网页路径
     * @return 扩展名
     */
    public static String getExtName(String path) {
        return cn.hutool.core.io.FileUtil.extName(new File(path));
    }

    /**
     * 将文件从一个路径，拷贝到另一个路径，原文件不会被删除
     * <p>如果目标目录不存在，则会自己创建，尽管有多级目录不存在，也会自动创建<p/>
     * @param copyFilePath 需要拷贝的文件路径
     * @param targetFilePath 目标文件路径
     * @return 传输的byte数
     * @throws IOException 异常
     */
    public static long copyFile(String copyFilePath, String targetFilePath) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = cn.hutool.core.io.FileUtil.getInputStream(copyFilePath);
            out = cn.hutool.core.io.FileUtil.getOutputStream(targetFilePath);
        } catch (IORuntimeException e) {
            throw new IOException(e.getMessage());
        }
        return IoUtil.copy(in, out, IoUtil.DEFAULT_LARGE_BUFFER_SIZE);
    }

    /**
     * 从指定路径中，读取多行内容，不能读取二进制文件
     * @param path 文件路径，.md,.txt等文本路径
     * @return 文本内容
     * @throws IOException 文件不存在异常
     */
    public static String readLines(String path) throws IOException {
        //从文件中读取的多行数据
        List<String> linesList = null;
        try {
            linesList = cn.hutool.core.io.FileUtil.readLines(path, CharsetUtil.CHARSET_UTF_8);
        } catch (IORuntimeException e) {
            throw new IOException(e.getMessage());
        }
        String contentTemp = "";
        for (String content : linesList) {
            contentTemp = contentTemp + content + "\n";
        }
        return contentTemp;
    }

    /**
     * 列出指定路径下得所有文件
     * @param path 需要遍历文件的路径
     * @return 该路径下所有文件集合
     * @throws IOException 文件不存在异常
     */
    public static List<File> fileList(String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            throw new IOException("file does not exist: " + path);
        }

        //判断是否是目录
        if (!file.isDirectory()) {
            return Collections.singletonList(file);
        }

        //循环列出该目录中的所有文件
        List<File> allFiles = new ArrayList<>();
        File[] listFile = file.listFiles();
        for (File fileSingle : listFile) {
            listFileFromFolder(fileSingle.getAbsolutePath(),allFiles);
        }

        return allFiles;
    }

    /**
     * 获取指定文件或者文件夹大小
     * @param path 文件路径
     * @return 总大小，bytes长度，包含目录本身
     * @throws IOException 异常
     */
    public static long getSize(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("file does not exist: " + path);
        }

        return cn.hutool.core.io.FileUtil.size(file,true);
    }

    /**
     * 遍历文件
     * @param path 路径
     * @param allFiles 存储file对象的集合
     */
    private static void listFileFromFolder(String path, List<File> allFiles) {
        File file = new File(path);
        if(file.isDirectory()) {
            File [] lists = file.listFiles();
            for (File fileSingle : lists) {
                listFileFromFolder(fileSingle.getAbsolutePath(),allFiles);
            }
        }else {
            allFiles.add(file);
        }
    }

    /**
     * 判断文件是否存在
     * @param path 路径
     * @return true存在，false不存在
     */
    public static boolean isExists(String path) {
        return new File(path).exists();
    }

    public static boolean createFile(String path, boolean folder) {
        File newFile = null;
        if (folder) {
            //创建文件夹
            newFile = cn.hutool.core.io.FileUtil.mkdir(path);
        }else {
            newFile = cn.hutool.core.io.FileUtil.touch(new File(path));
        }

        return newFile.exists();
    }

    /**
     * 返回指定文件的输入流
     * @param path 文件路径 不能为文件夹
     * @return 输入流，如果是文件夹，返回null
     * @throws IOException 异常
     */
    public static InputStream getInputStream(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            return null;
        }
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IOException(e.getMessage());
        }
        return inputStream;
    }

    /**
     * 返回指定路径的输出流
     * @param path 文件路径
     * @return 输出流
     * @throws IOException 文件不存在，或者无权访问此文件
     */
    public static OutputStream getOutputStream(String path) throws IOException {
        File file = new File(path);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return outputStream;
    }

    /**
     * 将一个流对象写入到目标路径中
     * @param inputStream 输入流对象
     * @param path 目标写入路径，会自动创建对应的目录和文件
     * @return FileDO
     * @throws IOException 异常
     */
    public static File writeByStream(InputStream inputStream,String path) throws IOException {
        File file = null;
        try {
            file = cn.hutool.core.io.FileUtil.writeFromStream(inputStream, new File(path));
        } catch (IORuntimeException e) {
            throw new IOException(e.getMessage());
        }
        return file;
    }

    /**
     * 根据传入的前缀，将需要切割的文件路径变成在nginx中映射的访问后缀
     * <p>如在nginx中配置某个server，root为/usr/aurora-file，/usr/aurora-file目录中存在一个文件，路径为
     * /usr/aurora-file/image/2022/bb/a.jpg，那么就返回/image/2022/bb/a.jpg</p>
     * <p>prefixPath可以是/usr/aurora-file或者/usr/aurora-file/</p>
     * @param prefixPath nginx设置的root路径，如描述中的/usr/aurora-file
     * @param path 存放在nginx的root路径下得文件路径，如描述中的/image/2022/bb/a.jpg
     * @return
     */
    public static String getFileSplitPath(String prefixPath,String path) {
        if (prefixPath.endsWith(File.separator)) {
            prefixPath = prefixPath.substring(0,prefixPath.length() -1);
        }
        return path.substring(prefixPath.length());
    }

    /**
     * 删除指定文件路径的文件
     * @param path
     * @return true删除成功，false删除失败
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        return file.delete();
    }

    /**
     * 获取文件最后一次修改时间
     * @param path
     * @return
     */
    public static String lastModifiedTime(String path) {
        return DateUtils.format(FileUtil.lastModifiedTime(path));
    }

}
