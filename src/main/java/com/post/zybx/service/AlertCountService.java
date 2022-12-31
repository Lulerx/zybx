package com.post.zybx.service;

import com.post.zybx.dto.CommonPage;

import java.util.Map;

/**
 * create by Luler on 2022/12/30 11:37
 *
 * @description
 */
public interface AlertCountService {

    /**
     * 模型统计的分页查询
     * @param page
     * @return
     */
    CommonPage countModelPage(CommonPage page);


    /**
     * 机构统计的分页查询
     * @param page
     * @return
     */
    CommonPage countDeptPage(CommonPage page);


    CommonPage searchCountDeptPage(CommonPage page, Map map);

}
