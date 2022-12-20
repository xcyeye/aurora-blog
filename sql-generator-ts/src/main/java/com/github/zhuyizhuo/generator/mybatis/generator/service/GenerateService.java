package com.github.zhuyizhuo.generator.mybatis.generator.service;

import com.github.zhuyizhuo.generator.mybatis.vo.GenerateMetaData;

/**
 * class: GenerateService <br>
 * description: 生成器 service <br>
 * time: 2019/5/23
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public interface GenerateService {

    /**
     * 文件生成
     * @param generateMetaData 生成所需元数据
     */
    void generate(GenerateMetaData generateMetaData);
}
