package com.post.zybx.dto;

import lombok.Data;

import java.util.List;

/**
 * create by Luler on 2022/12/28 11:50
 *
 * @description
 */
@Data
public class CommonPage<T> {
    /** 分页参数，第几页 */
    protected long current;

    /** 分页参数，当前页数量 */
    protected long size;

    /** 数据列表 */
    protected List<T> list;

    /** 数据总量 */
    protected long count;

    public CommonPage(long current, long size){
        this.current = current;
        this.size = size;
    }
}
