package xyz.xcye.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.ResponseRealResult;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.exception.file.FileException;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.file.dto.FileEntityDTO;
import xyz.xcye.file.po.File;
import xyz.xcye.file.service.FileService;
import xyz.xcye.file.vo.FileVO;

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

@Tag(name = "操作文件相关的工具类")
@RequestMapping("/file")
@RefreshScope
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
    @SelectOperation
    @Operation(summary = "上传单个文件",description = "可以上传任何类型，最大不能操作30M，返回上传之后的文件信息")
    @PostMapping("/single")
    public FileVO singleUploadFile(@Validated({Insert.class, Default.class}) File fileInfo,
                                   @RequestParam(value = "file") MultipartFile file,
                                   @RequestParam(required = false) int storageMode, long userUid)
            throws IOException, FileException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        return fileService.insertFile(fileEntity, fileInfo, storageMode, userUid);
    }

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @PostMapping("/multi")
    @SelectOperation
    @Operation(summary = "上传多个文件，返回集合",description = "不接收文件简介，默认本地存储")
    public List<FileVO> multiUploadFile(
            @RequestParam(value = "file") MultipartFile[] files,
            @RequestParam(required = false) int storageMode, long userUid)
            throws IOException, FileException {
        List<FileVO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(), file.getInputStream());
            File fileInfo = new File();
            FileVO fileVO = fileService.insertFile(fileEntity, fileInfo, storageMode, userUid);
            fileList.add(fileVO);
        }
        return fileList;
    }

    /**
     * 在typora中，将文件上传到自己服务器或者其他存储类型上
     * @return
     */
    @ResponseRealResult
    @Operation(summary = "在typora中自动上传图片", description = "用于在typora中粘贴图片时，将图片上传到本地服务器或者某个对象存储中")
    @PostMapping("/typora")
    public String typoraUploadFile(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(required = false) int storageMode)
            throws IOException, FileException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        File fileInfo = new File();
        fileInfo.setSummary("从typora上传的文件");
        FileVO fileVO = fileService.insertFile(fileEntity, fileInfo, storageMode, 0);
        return fileVO.getPath();
    }

    /**
     * 根据传入的条件从数据库中查询文件记录
     * @return
     */
    @SelectOperation
    @Operation(summary = "查询文件数据", description = "其中keyword为文件的名字")
    @GetMapping
    public PageData<FileVO> queryAllFile(Condition<Long> condition) {
        return fileService.queryAllFile(condition);
    }

    @SelectOperation
    @Operation(summary = "查询指定后缀的所有文件", description = "其中keyword为文件的后缀名，需要加上.,如.jpg")
    @GetMapping("/formatFile")
    public PageData<FileVO> selectSpecifyFormatFiles(Condition<Long> condition) {
        return fileService.selectSpecifyFormatFiles(condition);
    }

    @SelectOperation
    @Operation(summary = "查询文件数据", description = "其中keyword为文件的名字")
    @GetMapping("/{uid}")
    public FileVO queryFileByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return fileService.queryByUid(uid);
    }

    @SelectOperation
    @Operation(summary = "查询该userUid所对应的所有文件的后缀信息")
    @GetMapping("/format/{userUid}")
    public List<String> queryAllFileFormat(@PathVariable("userUid") long userUid) {
        return fileService.selectAllFileFormat(userUid);
    }

    /**
     * 根据传入的信息，修改数据库中文件的信息，一定要传入文件uid
     * @param fileInfo 需要修改的文件信息字段
     * @return
     */
    @ModifyOperation
    @Operation(summary = "修改文件属性", description = "只能修改文件的简介，因为其余的字段，修改没有任何意义，名字这些都是和文件本身绑定")
    @PutMapping
    public int updateFile(@Validated({Update.class, Default.class}) File fileInfo) {
        return fileService.updateFile(fileInfo);
    }

    /**
     * 删除指定文件
     * @param uid 一定要传入uid
     * @return 文件的修改情况
     */
    @ModifyOperation
    @Operation(summary = "根据uid删除某个文件", description = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @DeleteMapping("/{uid}")
    public int deleteFile(@PathVariable("uid") long uid) throws FileException, IOException {
        return fileService.deleteFile(uid);
    }

    @ModifyOperation
    @Operation(summary = "根据uid删除某个文件的信息，从数据库中删除", description = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @DeleteMapping("/info/{uid}")
    public int deleteFileInfo(@PathVariable("uid") long uid) {
        return fileService.deleteFileInfo(uid);
    }

    @Operation(summary = "根据uid下载文件")
    @GetMapping("/download/{uid}")
    public void downloadFile(@PathVariable("uid") long uid, HttpServletResponse response)
            throws FileException, IOException, ReflectiveOperationException {
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
