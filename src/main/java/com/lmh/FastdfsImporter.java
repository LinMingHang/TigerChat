package com.lmh;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @ClassName: FastdfsImporter
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/30 下午2:43
 */
@Configuration
@Import(FdfsClientConfig.class)
// 解决 jmx 重复注册 bean 的问题
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
public class FastdfsImporter {
    // 导入依赖组件
}