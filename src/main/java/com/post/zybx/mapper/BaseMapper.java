package com.post.zybx.mapper;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * create by Luler on 2023/1/4 11:54
 *
 * @description
 */
public interface BaseMapper {

    int insertBatchSomeColumn(List<T> list);

}
