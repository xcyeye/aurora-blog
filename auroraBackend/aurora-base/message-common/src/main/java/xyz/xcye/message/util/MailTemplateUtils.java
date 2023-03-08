package xyz.xcye.message.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.message.enums.MailTemplateEnum;

import java.io.*;

/**
 * @author qsyyke
 */


@Slf4j
public class MailTemplateUtils {

    /**
     * 获取
     * @param templateName
     * @param templateFolderPath
     * @return
     * @throws IOException
     */
    public static String readContentFromTemplateFile(String templateName, String templateFolderPath) throws IOException {
        if (!StringUtils.hasLength(templateName)) {
            throw new IOException("模板文件名不能为空或者null");
        }

        // 因为模板文件是html，所以修改模板文件的名字
        templateName = templateName + ".html";
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

    /**
     * 读取邮件发送的html模板文件的内容
     * @param templatePath
     * @return
     * @throws IOException
     */
    public static String readContentFromFile(String templatePath) throws IOException {
        LogUtils.logCommonInfo("模板文件的位置" + templatePath);
        AssertUtils.stateThrow(templatePath != null, () -> new IOException("文件名不能为null"));
        File file = new File(templatePath);
        if (!file.exists()) {
            // 文件不存在
            throw new IOException("不存在此文件" + templatePath);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder builder = new StringBuilder();
        String s = "";
        while ((s = reader.readLine()) != null) {
            builder.append(System.lineSeparator()).append(s);
        }
        reader.close();
        return builder.toString();
    }
}
