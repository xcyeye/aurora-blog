package xyz.xcye.message.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import xyz.xcye.message.enums.MailTemplateEnum;

import java.io.*;

/**
 * @author qsyyke
 */


public class MailTemplateUtils {

    public static String readContentFromTemplateFile(String templateName, String templateFolderPath) throws IOException {
        if (!StringUtils.hasLength(templateName)) {
            throw new IOException("模板文件名不能为空或者null");
        }
        ClassPathResource classPathResource = new ClassPathResource(MailTemplateEnum.APPLICATION_MAIN_CONFIG.getTemplateName());
        String applicationFilePath = classPathResource.getFile().getAbsolutePath();
        String classPathAbsolutePath = applicationFilePath.substring(0,applicationFilePath.length() -
                MailTemplateEnum.APPLICATION_MAIN_CONFIG.getTemplateName().length());

        // classPathAbsolutePath就是resource文件夹的绝对路径
        if (!StringUtils.hasLength(templateFolderPath)) {
            return readContentFromFile(classPathAbsolutePath + templateName);
        }

        return readContentFromFile(classPathAbsolutePath + templateFolderPath + File.separator + templateName);
    }

    private static String readContentFromFile(String templatePath) throws IOException {
        File file = new File(templatePath);
        if (!file.exists()) {
            // 文件不存在
            throw new IOException("不存在此文件" + templatePath);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder builder = new StringBuilder();
        String s = "";
        while ((s = reader.readLine()) != null) {
            builder.append(System.lineSeparator() + s);
        }
        reader.close();
        return builder.toString();
    }
}
