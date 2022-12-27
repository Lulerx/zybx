package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.post.zybx.bean.TbUser;
import com.post.zybx.mapper.TbUserMapper;
import com.post.zybx.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by Luler on 2022/12/27 10:43
 *
 * @description
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {
    private static final Logger logger = LoggerFactory.getLogger(TbUserServiceImpl.class);

    @Resource
    private TbUserMapper tbUserMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<TbUser> findPageVo(long current, long size) {
        Page<TbUser> page = new Page<>(current, size);
        Page<TbUser> tbUserPage = tbUserMapper.selectPage(page, null);
        return tbUserPage;
    }

    @Override
    public int insertVo(TbUser tbUser) {
        int res = tbUserMapper.insert(tbUser);
        return res;
    }

    @Override
    public int inertListVo(List<TbUser> users) {
        boolean res = this.saveBatch(users);
        return res ? 1 : 0;
    }
}
