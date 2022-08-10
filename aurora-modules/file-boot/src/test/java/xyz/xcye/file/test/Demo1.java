package xyz.xcye.file.test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author qsyyke
 * @date Created in 2022/8/10 16:23
 */


public class Demo1 {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/aurora/Movies/ishot录制/iShot2022-07-24 15.03.38.mp4");
        long totalSpace = file.getTotalSpace() / (1024 * 1024);
        long usableSpace = file.getUsableSpace() / (1024 * 1024);
        long freeSpace = file.getFreeSpace() / (1024 * 1024);
        long length = file.length();
        System.out.println(length);
        System.out.println((length / 1024 / 1024));

        FileInputStream fileInputStream = new FileInputStream(file);
        int available = fileInputStream.available();
        System.out.println((int) Math.pow(2,32) / 1024 / 1024);

    }
}
