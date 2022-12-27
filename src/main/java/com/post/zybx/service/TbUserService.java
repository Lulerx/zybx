package com.post.zybx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.post.zybx.bean.TbUser;

import java.util.List;

/**
 * create by Luler on 2022/12/27 10:40
 *
 * @description
 */
public interface TbUserService extends IService<TbUser> {

    /**
     * 分页查询
     * @param current 当前页
     * @param size 每页显示条数
     * @return Page对象
     */
    Page<TbUser> findPageVo(long current, long size);

    int insertVo(TbUser tbUser);

    int inertListVo(List<TbUser> users);

}
