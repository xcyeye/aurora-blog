package xyz.xcye;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.util.FileUtil;
import xyz.xcye.interfaces.impl.LocalFileStorageServiceImpl;
import xyz.xcye.service.FileService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author qsyyke
 */

@SpringBootTest
public class TestMain {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate1;

    @Autowired
    private FileService fileService;

    /*@Autowired
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer;*/

    @Autowired
    private LocalFileStorageServiceImpl storageService;

    @Test
    public void test() throws Exception {
        InputStream inputStream = new FileInputStream("/Users/aurora/nginx-upload/a.txt");
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName("a.txt");
        FileEntity upload = storageService.upload(inputStream, fileEntity);
        System.out.println(upload);
        //System.out.println(fileEntity);
    }

    public static void main(String[] args) throws Exception {

    }




}
