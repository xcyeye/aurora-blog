package xyz.xcye.core.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:13:08
 */

public class StringFileUtils {
    public static String getFileContent(File file) throws IOException {
        if (file == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        while (reader.readLine() != null) {
            builder.append(reader.readLine()).append("\n");
        }
        String content = builder.toString();
        reader.close();
        return content;
    }
}
