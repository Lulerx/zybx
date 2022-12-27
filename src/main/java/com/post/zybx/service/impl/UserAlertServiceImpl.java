package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.mapper.UserAlertMapper;
import com.post.zybx.service.UserAlertService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by Luler on 2022/12/26 15:57
 *
 * @description
 */
@Service
public class UserAlertServiceImpl implements UserAlertService {

    @Resource
    private UserAlertMapper userAlertMapper;

    @Override
    public List<UserAlert> findAllVo() {
        List<UserAlert> list = userAlertMapper.selectList(null);
        return list;
    }

    @Override
    public Page<UserAlert> findPageVo(long current, long size) {
        Page<UserAlert> page = new Page<>(current, size);
        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, null);
        return userAlertPage;
    }
}
