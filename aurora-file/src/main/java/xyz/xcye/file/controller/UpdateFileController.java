package xyz.xcye.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.common.valid.Update;
import xyz.xcye.file.service.FileService;

import javax.validation.groups.Default;

/**
 * 修改文件
 * @author qsyyke
 */

@Api(tags = "文件修改api")
@RequestMapping("/file")
@RestController
public class UpdateFileController {

    @Autowired
    private FileService fileService;

    /**
     * 根据传入的信息，修改数据库中文件的信息，一定要传入文件uid
     * @param fileInfo 需要修改的文件信息字段
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "修改文件属性",notes = "只能修改文件的简介，因为其余的字段，修改没有任何意义，名字这些都是和文件本身绑定")
    @PutMapping("/update")
    public ModifyResult updateFile(@Validated({Update.class,Default.class}) File fileInfo) {
        return fileService.updateFile(fileInfo);
    }
}
