package com.github.zhuyizhuo.generator.mybatis.app;

import com.github.zhuyizhuo.generator.mybatis.generator.Generator;
import com.github.zhuyizhuo.generator.mybatis.generator.GeneratorBuilder;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;
import com.google.common.base.Charsets;
import org.apache.ibatis.io.Resources;

import java.io.*;
import java.nio.file.Files;

/**
 * Hello world!
 *
 */
public class AuroraCommonApp {
	public static void main(String[] args) throws Exception {
		/**
		 * 此处使用 配置文件的绝对路径或者在项目中的相对路径 本例配置文件路径在 maven 项目的 src/main/resources 文件夹下
		 */
		Generator generator = new GeneratorBuilder().build("aurora-common-config.properties");
		generator.generate();

		// InputStream resourceAsStream = Resources.getResourceAsStream("xcye-config.properties");
		// PropertiesUtils.loadProperties(new BufferedReader(new InputStreamReader(resourceAsStream, Charsets.UTF_8)));
		// System.out.println(PropertiesUtils.getConfig("print"));
	}
}
