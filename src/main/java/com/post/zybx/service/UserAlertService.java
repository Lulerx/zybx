package com.post.zybx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;

import java.util.List;

/**
 * create by Luler on 2022/12/26 15:55
 *
 * @description
 */
public interface UserAlertService {

    List<UserAlert> findAllVo();

    /**
     * 分页查询
     * @param current 当前页
     * @param size 每页显示条数
     * @return Page对象
     */
    Page<UserAlert> findPageVo(long current, long size);

}
