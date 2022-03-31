package xyz.xcye.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.entity.FileEntity;
import xyz.xcye.common.entity.table.File;
import xyz.xcye.service.FileService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qsyyke
 */


@Slf4j
@RequestMapping("/file/query")
@RestController
public class QueryFileController {

    @Autowired
    private FileService service;

    @GetMapping("/test")
    public Object query(File file) {
        return null;
    }
}
