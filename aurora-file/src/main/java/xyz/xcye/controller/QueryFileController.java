package xyz.xcye.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.Pagination;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.service.FileService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qsyyke
 */


@Api(tags = "查询所有文件")
@RequestMapping("/file/query")
@RestController
public class QueryFileController {

    @Autowired
    private FileService fileService;

    /**
     * 根据传入的条件从数据库中查询文件记录
     * @param file 查询条件对象
     * @param pagination 设置页码，每页的长度，以及排序字段，可以传入驼峰命名，会自动转换
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "查询文件数据",notes = "可以传入file对象中的属性进行筛选以及排序")
    @GetMapping("/all")
    public List<File> queryAllFile(File file, Pagination pagination) {
        return fileService.queryAllFile(file,pagination);
    }
}
