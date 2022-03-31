package xyz.xcye.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是文件上传的api
 * @author qsyyke
 */

@Api(tags = "上传文件")
@RequestMapping("/file/upload")
@RestController
public class UploadFileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传单个文件
     * @param fileInfo
     * @param file
     * @return
     */
    @ApiOperation(value = "上传单个文件",notes = "可以上传任何类型，最大不能操作30M，返回上传之后的文件信息")
    @PostMapping("/single")
    public File singleUploadFile(@Validated File fileInfo, MultipartFile file,
                                 @RequestParam(required = false) int storageMode) throws IOException {
        FileEntity fileEntity = new FileEntity(file.getOriginalFilename(),file.getInputStream());
        return fileService.insertFile(fileEntity, fileInfo, storageMode);
    }


    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @PostMapping("/multi")
    @ApiOperation(value = "上传多个文件，返回集合",notes = "不接收文件简介，默认本地存储")
    public List<File> multiUploadFile(MultipartFile[] files,@RequestParam(required = false) int storageMode) throws IOException {
        List<File> fileList = new ArrayList<>();

        for (MultipartFile file : files) {
            FileEntity fileEntity = new FileEntity(file.getOriginalFilename(), file.getInputStream());
            File fileInfo = new File();
            File insertFileInfo = fileService.insertFile(fileEntity, fileInfo, storageMode);
            fileList.add(insertFileInfo);
        }
        return fileList;
    }

    /**
     * 在typora中，将文件上传到自己服务器或者其他存储类型上
     * @return
     */
    @ApiOperation(value = "在typora中自动上传图片",notes = "用于在typora中粘贴图片时，将图片上传到本地服务器或者某个对象存储中")
    @PostMapping("/typora")
    public String typoraUploadFile(MultipartFile file,@RequestParam(required = false) int storageMode) throws IOException {
        FileEntity fileEntity = new FileEntity(file.getOriginalFilename(),file.getInputStream());
        File fileInfo = new File();
        fileInfo.setSummary("从typora上传的文件");
        return fileService.insertFile(fileEntity, fileInfo, storageMode).getPath();
    }

}
