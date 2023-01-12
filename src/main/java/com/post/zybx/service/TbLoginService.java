package com.post.zybx.service;

import com.post.zybx.bean.TbLogin;
import com.post.zybx.common.CommonResult;

/**
 * create by Luler on 2023/1/12 10:56
 *
 * @description
 */
public interface TbLoginService {

    /**
     * 用户注册
     * @param login
     * @return
     */
    CommonResult register(TbLogin login);

    /**
     * 根据用户名密码登录
     * @param login
     * @return
     */
    CommonResult userLogin(TbLogin login);

}
