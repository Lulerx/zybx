package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.common.CommonPage;
import com.post.zybx.common.CommonResult;
import com.post.zybx.mapper.UserAlertMapper;
import com.post.zybx.service.PageQuerier;
import com.post.zybx.service.UserAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/26 15:57
 *
 * @description
 */
@Service
public class UserAlertServiceImpl implements UserAlertService, PageQuerier<UserAlert> {
    private static final Logger logger = LoggerFactory.getLogger(UserAlertServiceImpl.class);

    @Resource
    private UserAlertMapper userAlertMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer selectCount() {
        userAlertMapper.selectCount(null);
        return 1;
    }

    @Override
    public List<UserAlert> findAllList() {
        List<UserAlert> list = userAlertMapper.selectList(null);
        return list;
    }

    @Override
    public Page<UserAlert> findPageVo(CommonPage myPage) {
        Page<UserAlert> page = new Page<>(myPage.getCurrent(), myPage.getSize());
        QueryWrapper<UserAlert> wrapper = new QueryWrapper<>();
//        wrapper.orderByAsc("model_id");
        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, null);
        return userAlertPage;
    }


    @Override
    public Page<UserAlert> findPageByMap(CommonPage myPage,  Map<String, Object> map) {
        Page<UserAlert> page = new Page<>(myPage.getCurrent(), myPage.getSize());
        /*String phoneNo = (String) map.get("phoneNo");
        String email = (String) map.get("email");
        String cards = (String) map.get("cards");*/
        Integer modelId = (Integer) map.get("modelId");
        Integer status = (Integer) map.get("status");

        QueryWrapper<UserAlert> wrapper = new QueryWrapper<>();
        /*if (StringUtil.isNotEmpty(phoneNo)){
            wrapper.eq("phone", phoneNo);
        }
        if (StringUtil.isNotEmpty(email)){
            wrapper.eq("email", email);
        }
        if (StringUtil.isNotEmpty(cards)){
            wrapper.eq("id_card", cards);
        }*/
        if (modelId != 0){
            wrapper.eq("model_id", modelId);
        }
        if (status != null && status != -1){
            wrapper.eq("status", status);
        }

        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, wrapper);
        return userAlertPage;
    }

    @Override
    public Page<UserAlert> findPage(CommonPage<UserAlert> myPage) {
        Page<UserAlert> userAlertPage = findPageVo(myPage);
        return userAlertPage;
    }

    /**
     * 对碰 status=0 和 status = 1 的数据，查询出已核销的数据
     */
    @Override
    public void alertCheck() {

        String totalNumSql = "SELECT\n" +
                "  count(1)\n" +
                "FROM\n" +
                "  tb_user_alert tua1\n" +
                "WHERE\n" +
                "  tua1.`status` = '1'\n" +
                "AND NOT EXISTS (\n" +
                "  SELECT\n" +
                "    1\n" +
                "  FROM\n" +
                "    tb_user_alert tua\n" +
                "  WHERE\n" +
                "    tua.`status` = '0'\n" +
                "  AND tua.order_num = tua1.order_num\n" +
                "  AND tua1.model_id = tua.model_id\n" +
                ")";
        //查询出总数
        Integer totalNum = jdbcTemplate.queryForObject(totalNumSql, Integer.class);
        int pageSize = 1000;
        int pageLimit = totalNum / pageSize + 1;
        int fetch = 0;
        //分页查询，每次最多更新1000条，防止内存溢出
        for (int i = 0; i < pageLimit; i++) {
            String querySql = "SELECT \n" +
                    "  tua1.order_num, \n" +
                    "  tua1.model_id \n" +
                    "FROM \n" +
                    "  tb_user_alert tua1 \n" +
                    "WHERE\n" +
                    "  tua1.`status` = '1'\n" +
                    "AND NOT EXISTS (\n" +
                    "  SELECT\n" +
                    "    1\n" +
                    "  FROM\n" +
                    "    tb_user_alert tua\n" +
                    "  WHERE\n" +
                    "    tua.`status` = '0'\n" +
                    "  AND tua.order_num = tua1.order_num\n" +
                    "  AND tua1.model_id = tua.model_id\n" +
                    ") limit ?,? ";

            List<Map<String, Object>> resList = jdbcTemplate.queryForList(querySql, fetch, pageSize);
            //更新 status
            updateChecked(resList);
            fetch += pageSize;

        }
        //生成预警时，顺便将统计视图转为统计表
        viewToTable();

    }

    @Override
    public CommonResult findAlertNum() {
        String sql = "SELECT tua.`status`,count(1) alert_num  from tb_user_alert tua GROUP BY tua.`status`";
        List<Map<String, Object>> query = jdbcTemplate.queryForList(sql);
        return CommonResult.success(query);
    }

    @Override
    public CommonResult findModelNum() {
        String sql = "SELECT\n" +
                "  t1.model_id,\n" +
                "  tm.model_name,\n" +
                "  t1.alert_num\n" +
                "FROM (\n" +
                "  SELECT\n" +
                "    tua.model_id,\n" +
                "    count(1) alert_num\n" +
                "  FROM\n" +
                "    tb_user_alert tua\n" +
                "  GROUP BY\n" +
                "    tua.model_id\n" +
                ") t1\n" +
                "LEFT JOIN\n" +
                "  tb_model tm on tm.model_id = t1.model_id";
        List<Map<String, Object>> query = jdbcTemplate.queryForList(sql);
        return CommonResult.success(query);
    }


    /**
     * 将已核销的数据状态批量更新 status = 2
     * @param list 核销的数据，每次最多1000条
     */
    private void updateChecked(List<Map<String, Object>> list) {
        logger.info("======================批量更新 status = 2 ");
        String updateSql = "update tb_user_alert tua set tua.status = '2' where tua.order_num = ? and tua.model_id = ?";

        int[] updateCounts = jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                preparedStatement.setString(1, (String)list.get(i).get("order_num"));
                preparedStatement.setInt(2, (Integer)list.get(i).get("model_id"));
            }
            @Override
            public int getBatchSize(){
                return list.size();
            }
        });

    }


    /**
     * 将机构统计视图插入统计表中（从表中查询数据会快很多）
     */
    private void viewToTable(){
        String sql = "INSERT INTO tb_count_dept (\n" +
                "  city_name,\n" +
                "  city_id,\n" +
                "  dist_name,\n" +
                "  dist_id,\n" +
                "  total_alert_num,\n" +
                "  last_alert_num,\n" +
                "  not_check_alert_num,\n" +
                "  check_alert_num\n" +
                ") SELECT\n" +
                "  *\n" +
                "FROM\n" +
                "  v_count_dept\n";
        jdbcTemplate.update(sql);
    }




}
