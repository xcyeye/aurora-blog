package xyz.xcye.core.util.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xcye
 * @description
 * @date 2023-01-20 23:13:08
 */

public class StringFileUtils {
    public static String getFileContent(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String readLine = "";
        while ((readLine = reader.readLine()) != null) {
            builder.append(readLine).append("\n");
        }
        String content = builder.toString();
        reader.close();
        return content;
    }
}
