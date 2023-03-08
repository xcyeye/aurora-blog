package xyz.xcye.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.ResponseRealResult;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.exception.file.FileException;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.file.dto.FileEntityDTO;
import xyz.xcye.file.pojo.FilePojo;
import xyz.xcye.file.service.FileService;
import xyz.xcye.file.vo.FileVO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    @PostMapping("/singleUploadFile")
    public FileVO singleUploadFile(FilePojo fileInfo, @RequestParam(value = "file") MultipartFile file)
            throws IOException, FileException, ExecutionException, InterruptedException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        return fileService.insertFile(fileEntity, fileInfo);
    }

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @PostMapping("/multiUploadFile")
    @SelectOperation
    @Operation(summary = "上传多个文件，返回集合",description = "不接收文件简介，默认本地存储")
    public List<FileVO> multiUploadFile(
            @RequestParam(value = "files") MultipartFile[] files, FilePojo fileInfo)
            throws IOException, FileException, ExecutionException, InterruptedException {
        List<FileVO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(), file.getInputStream());
            FileVO fileVO = fileService.insertFile(fileEntity, fileInfo);
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
    @PostMapping("/typoraUploadFile")
    public String typoraUploadFile(
            @RequestParam(value = "file") MultipartFile file, FilePojo fileInfo)
            throws IOException, FileException, ExecutionException, InterruptedException {

        FileEntityDTO fileEntity = new FileEntityDTO(file.getOriginalFilename(),file.getInputStream());
        if (!StringUtils.hasLength(fileInfo.getSummary())) {
            fileInfo.setSummary("从typora上传的文件");
        }
        FileVO fileVO = fileService.insertFile(fileEntity, fileInfo);
        return fileVO.getPath();
    }

    /**
     * 根据传入的条件从数据库中查询文件记录
     * @return
     */
    @SelectOperation
    @Operation(summary = "查询文件数据", description = "其中keyword为文件的名字")
    @PostMapping("/queryListFileByCondition")
    public PageData<FileVO> queryListFileByCondition(@RequestBody Condition<Long> condition) {
        return fileService.queryListFileByCondition(condition);
    }

    @SelectOperation
    @Operation(summary = "查询指定后缀的所有文件", description = "其中keyword为文件的后缀名，需要加上.,如.jpg")
    @PostMapping("/querySpecifyFormatFiles")
    public PageData<FileVO> querySpecifyFormatFiles(@RequestBody Condition<Long> condition) {
        return fileService.querySpecifyFormatFiles(condition);
    }

    @SelectOperation
    @Operation(summary = "查询文件数据", description = "其中keyword为文件的名字")
    @PostMapping("/queryFileByUid")
    public FileVO queryFileByUid(@RequestBody FilePojo fileInfo) {
        return fileService.queryFileByUid(fileInfo.getUid());
    }

    @SelectOperation
    @Operation(summary = "查询该userUid所对应的所有文件的后缀信息")
    @PostMapping("/queryListFileFormat")
    public List<String> queryListFileFormat(@RequestBody FilePojo fileInfo) {
        return fileService.queryListFileFormat(fileInfo.getUserUid());
    }

    /**
     * 根据传入的信息，修改数据库中文件的信息，一定要传入文件uid
     * @param fileInfo 需要修改的文件信息字段
     * @return
     */
    @ModifyOperation
    @Operation(summary = "修改文件属性", description = "只能修改文件的简介，因为其余的字段，修改没有任何意义，名字这些都是和文件本身绑定")
    @PostMapping("/updateFile")
    public int updateFile(@Validated({Update.class, Default.class}) @RequestBody FilePojo fileInfo) {
        return fileService.updateFile(fileInfo);
    }

    /**
     * 删除指定文件
     * @param fileInfo 一定要传入uid
     * @return 文件的修改情况
     */
    @ModifyOperation
    @Operation(summary = "根据uid删除某个文件", description = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @PostMapping("/deleteFile")
    public int deleteFile(@RequestBody FilePojo fileInfo) throws FileException, IOException, ExecutionException, InterruptedException {
        return fileService.deleteFile(fileInfo.getUid());
    }

    @ModifyOperation
    @Operation(summary = "根据uid删除某个文件的信息，从数据库中删除", description = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @PostMapping("/physicalDeleteFileInfo")
    public int physicalDeleteFileInfo(@RequestBody FilePojo fileInfo) {
        return fileService.physicalDeleteFileInfo(fileInfo.getUid());
    }

    @Operation(summary = "查询文件数量")
    @SelectOperation
    @PostMapping("/queryTotalFileCount")
    public Integer queryCommentCount(@RequestBody FilePojo pojo) {
        return fileService.queryTotalFileCount(pojo);
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
