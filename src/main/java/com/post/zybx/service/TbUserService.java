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

    /**
     * 清空表数据
     * @return
     */
    void deleteAll();

    List<TbUser> findAllList();

    /**
     * 分页查询
     * @param myPage MyPage对象
     * @return Page对象
     */
    Page<TbUser> findPageVo(CommonPage myPage);

    /**
     * 插入单个对象数据
     * @param tbUser 单个对象
     * @return
     */
    int insertVo(TbUser tbUser);

    /**
     * 批量插入对象
     * @param users 对象列表
     * @return
     */
    int inertListVo(List<TbUser> users);

    /**
     * 生成预警数据，使用存储过程生成
     */
    void buildAlert();

}
