package xyz.xcye.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.FileEntityDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.FileDO;
import xyz.xcye.common.exception.file.FileException;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.FileVO;
import xyz.xcye.file.service.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qsyyke
 */

@Api(tags = "操作文件相关的工具类")
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传单个文件
     * @param fileInfo
     * @param file
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "上传单个文件",notes = "可以上传任何类型，最大不能操作30M，返回上传之后的文件信息")
    @PostMapping("/single")
    public FileVO singleUploadFile(@Validated FileDO fileInfo, @RequestParam(value = "file") MultipartFile file,
                                         @RequestParam(required = false) int storageMode) throws IOException, FileException, InstantiationException, IllegalAccessException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        return fileService.insertFile(fileEntity, fileInfo, storageMode);
    }

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @PostMapping("/multi")
    @ResponseResult
    @ApiOperation(value = "上传多个文件，返回集合",notes = "不接收文件简介，默认本地存储")
    public List<FileVO> multiUploadFile(
            @RequestParam(value = "files") MultipartFile[] files,
            @RequestParam(required = false) int storageMode)
            throws IOException, FileException, InstantiationException, IllegalAccessException {

        List<FileVO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(), file.getInputStream());
            FileDO fileInfo = new FileDO();
            FileVO fileVO = fileService.insertFile(fileEntity, fileInfo, storageMode);
            fileList.add(fileVO);
        }
        return fileList;
    }

    /**
     * 在typora中，将文件上传到自己服务器或者其他存储类型上
     * @return
     */
    @ApiOperation(value = "在typora中自动上传图片",notes = "用于在typora中粘贴图片时，将图片上传到本地服务器或者某个对象存储中")
    @PostMapping("/typora")
    public String typoraUploadFile(

            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(required = false) int storageMode)
            throws IOException, InstantiationException, IllegalAccessException, FileException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        FileDO fileInfo = new FileDO();
        fileInfo.setSummary("从typora上传的文件");
        FileVO fileVO = fileService.insertFile(fileEntity, fileInfo, storageMode);
        return fileVO.getPath();
    }

    /**
     * 根据传入的条件从数据库中查询文件记录
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "查询文件数据",notes = "其中keyword为文件的名字")
    @GetMapping
    public List<FileVO> queryAllFile(ConditionDTO<Long> condition)
            throws InstantiationException, IllegalAccessException {
        return fileService.queryAllFile(condition);
    }

    @ResponseResult
    @ApiOperation(value = "查询文件数据",notes = "其中keyword为文件的名字")
    @GetMapping("/{uid}")
    public FileVO queryFileByUid(@PathVariable("uid") long uid)
            throws InstantiationException, IllegalAccessException {
        return fileService.queryByUid(uid);
    }

    /**
     * 根据传入的信息，修改数据库中文件的信息，一定要传入文件uid
     * @param fileInfo 需要修改的文件信息字段
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "修改文件属性",notes = "只能修改文件的简介，因为其余的字段，修改没有任何意义，名字这些都是和文件本身绑定")
    @PutMapping
    public ModifyResult updateFile(@Validated({Update.class, Default.class}) FileDO fileInfo) {
        return fileService.updateFile(fileInfo);
    }

    /**
     * 删除指定文件
     * @param uid 一定要传入uid
     * @return 文件的修改情况
     */
    @ResponseResult
    @ApiOperation(value = "根据uid删除某个文件",notes = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @DeleteMapping("/{uid}")
    public ModifyResult deleteFile(@PathVariable("uid") long uid)
            throws InstantiationException, IllegalAccessException, FileException, IOException {
        return fileService.deleteFile(uid);
    }

    @ApiOperation(value = "根据uid下载文件")
    @GetMapping("/download/{uid}")
    public void downloadFile(@PathVariable("uid") long uid, HttpServletResponse response)
            throws FileException, IOException, InstantiationException, IllegalAccessException {
        FileEntityDTO fileEntityDTO = fileService.downloadFile(uid);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(fileEntityDTO.getInputStream());

        int i = bis.read(bytes);
        while (i != -1) {
            outputStream.write(bytes,0,bytes.length);
            outputStream.flush();
            i = bis.read(bytes);
        }
    }
}
