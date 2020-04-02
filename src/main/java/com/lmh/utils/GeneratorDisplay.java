package com.lmh.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GeneratorDisplay
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/29 下午4:01
 */
public class GeneratorDisplay {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        // 指定逆向工程配置文件
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generatorConfig.xml");

        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,callback,warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        try {
            GeneratorDisplay generatorDisplay = new GeneratorDisplay();
            generatorDisplay.generator();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}