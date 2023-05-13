package xyz.xcye.file.utils;

import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.FileUtils;
import xyz.xcye.file.pojo.FilePojo;

import java.io.File;
import java.util.Date;

/**
 * @author xcye
 * @description
 * @date 2023-05-07 20:47:33
 */

public class FileStorageUtil {
    public static String getStoragePathDirByTimeAndFileName(String fileName, boolean isRemoteStorage, FilePojo pojo) {
        //获取上传文件的扩展名
        String extName = FileUtils.getExtName(fileName);

        String separator = File.separator;

        if (isRemoteStorage) {
            separator = "/";
        }

        //获取当前的年和月
        int currentYear = DateUtils.getYear(new Date());
        int currentMonth = DateUtils.getMonth(new Date());

        // 如果用户的userUid不为null，则加上userUid
        String pathTemp = "";
        if (pojo.getUserUid() != null) {
            pathTemp = pojo.getUserUid() + separator;
        }
        pathTemp = pathTemp + extName + separator + currentYear + separator + currentMonth;
        return pathTemp;
    }
}
