package xyz.xcye.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.file.exception.CustomFileException;
import xyz.xcye.file.service.FileService;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.common.valid.Delete;

/**
 * 这是删除文件的api
 * @author qsyyke
 */

@Api(tags = "删除指定文件")
@RequestMapping("/file")
@RestController
public class DeleteFileController {

    @Autowired
    private FileService fileService;

    /**
     * 删除指定文件
     * @param file 一定要传入uid
     * @return 文件的修改情况
     * @throws CustomFileException 删除文件异常（可能无权操作文件，文件不存在等等）
     */
    @ResponseResult
    @ApiOperation(value = "根据uid删除某个文件",notes = "从数据库中删除对应数据，删除与之关联的本地，对象存储中的文件，返回删除成功之后的文件对象")
    @DeleteMapping("/delete")
    public ModifyResult deleteFile(@Validated(Delete.class) File file) throws CustomFileException {
        return fileService.deleteFile(file);
    }
}
