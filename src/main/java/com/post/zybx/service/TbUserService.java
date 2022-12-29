package com.post.zybx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.post.zybx.bean.TbUser;
import com.post.zybx.dto.CommonPage;

import java.util.List;

/**
 * create by Luler on 2022/12/27 10:40
 *
 * @description
 */
public interface TbUserService extends IService<TbUser> {

    List<TbUser> findAllList();

    /**
     * 分页查询
     * @param myPage MyPage对象
     * @return Page对象
     */
    Page<TbUser> findPageVo(CommonPage myPage);

    int insertVo(TbUser tbUser);

    int inertListVo(List<TbUser> users);

}
