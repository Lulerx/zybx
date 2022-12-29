package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.dto.CommonPage;
import com.post.zybx.mapper.UserAlertMapper;
import com.post.zybx.service.PageQuerier;
import com.post.zybx.service.UserAlertService;
import com.post.zybx.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/26 15:57
 *
 * @description
 */
@Service
public class UserAlertServiceImpl implements UserAlertService, PageQuerier<UserAlert> {

    @Resource
    private UserAlertMapper userAlertMapper;

    @Override
    public Integer selectCount() {
        Integer res = userAlertMapper.selectCount(null);
        return res;
    }

    @Override
    public List<UserAlert> findAllList() {
        List<UserAlert> list = userAlertMapper.selectList(null);
        return list;
    }

    @Override
    public Page<UserAlert> findPageVo(CommonPage myPage) {
        Page<UserAlert> page = new Page<>(myPage.getCurrent(), myPage.getSize());
        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, null);
        return userAlertPage;
    }


    @Override
    public Page<UserAlert> findPageByMap(CommonPage myPage,  Map<String, Object> map) {
        Page<UserAlert> page = new Page<>(myPage.getCurrent(), myPage.getSize());
        String phoneNo = (String) map.get("phoneNo");
        String email = (String) map.get("email");
        String cards = (String) map.get("cards");
        Integer modelId = (Integer) map.get("modelId");

        QueryWrapper<UserAlert> wrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(phoneNo)){
            wrapper.eq("phone", phoneNo);
        }
        if (StringUtil.isNotEmpty(email)){
            wrapper.eq("email", email);
        }
        if (StringUtil.isNotEmpty(cards)){
            wrapper.eq("id_card", cards);
        }
        if (modelId != 0){
            wrapper.eq("model_id", modelId);
        }

        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, wrapper);
        return userAlertPage;
    }

    @Override
    public Page<UserAlert> findPage(CommonPage<UserAlert> myPage) {
        Page<UserAlert> page = new Page<>(myPage.getCurrent(), myPage.getSize());
        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, null);
        return userAlertPage;
    }
}
