package com.post.zybx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.dto.CommonPage;

/**
 * create by Luler on 2022/12/29 15:04
 *
 * @description
 */
public interface PageQuerier<T> {
    /**
     * 查询指定页数据，同时会查询总数
     *
     * @param myPage
     * @return
     */
    Page<T> findPage(CommonPage<T> myPage);
}
