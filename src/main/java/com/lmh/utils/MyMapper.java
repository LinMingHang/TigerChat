package com.lmh.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * @ClassName: MyMapper
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/29 下午3:40
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
    //TODO
    //FIXME 特别注意,该接口不能被扫描到,否则会出错
}