package com.post.zybx.service;

import com.post.zybx.bean.TbDept;

import java.util.List;

/**
 * create by Luler on 2023/1/3 16:33
 *
 * @description
 */
public interface TbDeptService {

    /**
     * 查询所有地市
     * @return
     */
    List<TbDept> findAllCity();

    /**
     * 根据地市 ID 查询其下所有区县
     * @param cityId
     * @return
     */
    List<TbDept> findDistByCityId(String cityId);

    /**
     * 获取所有地市、区县的树形结构
     * @return
     */
    List getTreeDept();

}
