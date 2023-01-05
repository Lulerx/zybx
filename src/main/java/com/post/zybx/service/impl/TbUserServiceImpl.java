package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.post.zybx.bean.TbUser;
import com.post.zybx.common.CommonPage;
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
    private static final Logger log = LoggerFactory.getLogger(TbUserServiceImpl.class);

    @Resource
    private TbUserMapper tbUserMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public void deleteAll() {
        jdbcTemplate.execute("truncate table tb_user");
    }

    @Override
    public List<TbUser> findAllList() {
        List<TbUser> list = tbUserMapper.selectList(null);
        return list;
    }

    @Override
    public Page<TbUser> findPageVo(CommonPage myPage) {
        Page<TbUser> page = new Page<>(myPage.getCurrent(), myPage.getSize());
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
//        boolean res = this.saveBatch(users);
        Integer integer = tbUserMapper.insertBatchSomeColumn(users);
        return integer;
    }

    @Override
    public void buildAlert() {
        log.info("======================== 将上一次预警 status = 0 --> 1 ");
        String updateSql = "update tb_user_alert tua set tua.status = '1' where tua.status = '0' ";
        jdbcTemplate.update(updateSql);
        log.info("======================== 开始调用存储过程生成预警数据 ------>");
        jdbcTemplate.execute("call proc_alert_model_all()");
        log.info("======================== 存储过程调用完成！ ");
    }
}
