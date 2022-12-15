package xyz.xcye.file.test;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author qsyyke
 * @date Created in 2022/8/10 16:23
 */


public class Demo1 {
    public static void main(String[] args) throws Exception {
        // File file = new File("/Users/aurora/Movies/ishot录制/iShot2022-07-24 15.03.38.mp4");
        // long totalSpace = file.getTotalSpace() / (1024 * 1024);
        // long usableSpace = file.getUsableSpace() / (1024 * 1024);
        // long freeSpace = file.getFreeSpace() / (1024 * 1024);
        // long length = file.length();
        // System.out.println(length);
        // System.out.println((length / 1024 / 1024));
        //
        // FileInputStream fileInputStream = new FileInputStream(file);
        // int available = fileInputStream.available();
        // System.out.println((int) Math.pow(2,32) / 1024 / 1024);

        File file = new File("F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2022\\12\\wallhaven-wqve97.png");
        File fil2 = new File("F:\\opt\\aurora-theme\\nginx-upload\\aurora-upload\\png\\2022\\12\\wallhaven-q22jv7.png");
        System.out.println(fil2.exists());
        boolean delete = fil2.delete();
        boolean del = FileUtil.del(fil2);
        System.out.println(delete);

    }
}
