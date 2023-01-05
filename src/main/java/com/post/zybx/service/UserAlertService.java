package com.post.zybx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.common.CommonPage;
import com.post.zybx.common.CommonResult;

import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/26 15:55
 *
 * @description
 */
public interface UserAlertService {

    Integer selectCount();

    List<UserAlert> findAllList();

    /**
     * 分页查询
     * @param myPage 自定义对象
     * @return Page对象
     */
    Page<UserAlert> findPageVo(CommonPage myPage);


    Page<UserAlert> findPageByMap(CommonPage myPage, Map<String, Object> map);

    /**
     * 对碰 status=0 和 status = 1 的数据，查询出已核销的数据并更 status
     */
    void alertCheck();

    /**
     * 查询各类预警的数量
     * @return
     */
    CommonResult findAlertNum();

    /**
     * 查询各类模型的预警数量
     * @return
     */
    CommonResult findModelNum();
}
