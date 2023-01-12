package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.post.zybx.bean.TbLogin;
import com.post.zybx.common.CommonResult;
import com.post.zybx.mapper.TbLoginMapper;
import com.post.zybx.service.TbLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * create by Luler on 2023/1/12 11:01
 *
 * @description
 */
@Service("tbLoginService")
public class TbLoginServiceImpl implements TbLoginService {
    private static final Logger logger = LoggerFactory.getLogger(TbLoginServiceImpl.class);

    @Resource
    private TbLoginMapper loginMapper;

    @Override
    public CommonResult register(TbLogin login) {
        logger.info("==================用户开始注册");
        QueryWrapper<TbLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", login.getUsername());
        TbLogin user = loginMapper.selectOne(wrapper);
        if (user != null) {
            return new CommonResult(null, 500, "用户名已存在");
        }
        int insert = loginMapper.insert(login);
        if (insert <= 0 ){
            return new CommonResult(null, 500, "系统出错，请重试");
        }
        logger.info("==================用户注册成功");
        return new CommonResult(user, 200, "注册成功");
    }

    @Override
    public CommonResult userLogin(TbLogin login) {
        logger.info("==================用户尝试登录");
        QueryWrapper<TbLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", login.getUsername());
        wrapper.eq("pwd", login.getPwd());
        TbLogin user = loginMapper.selectOne(wrapper);
        if (user == null){
            return new CommonResult(null, 500, "用户名或密码不正确");
        }
        logger.info("==================用户登录成功");
        user.setPwd("how are you?");
        return new CommonResult(user, 200, "登录成功");
    }
}
