package xyz.xcye.springtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;

@SpringBootTest
class SpringTestApplicationTests {

    @Test
    void contextLoads() throws IOException {
        /*ClassPathResource classPathResource = new ClassPathResource("html/notice.html");
        InputStream inputStream = classPathResource.getInputStream();

        System.out.println(inputStream);*/
        ClassPathResource classPathResource = new ClassPathResource("application.properties");
        System.out.println(classPathResource.getFile().getAbsolutePath());
        String absolutePath = classPathResource.getFile().getAbsolutePath();
        String substring = absolutePath.substring(0,absolutePath.length() - "application.properties".length());

        System.out.println(substring);
        File file = new File(substring + "html/notice.html");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder builder = new StringBuilder();
        String s = "";
        while ((s = br.readLine()) != null) {
            builder.append(System.lineSeparator() + s);
        }

        br.close();

        System.out.println(builder);
    }

}
