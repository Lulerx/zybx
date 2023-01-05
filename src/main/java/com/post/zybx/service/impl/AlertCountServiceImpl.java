package com.post.zybx.service.impl;

import com.post.zybx.common.CommonPage;
import com.post.zybx.service.AlertCountService;
import com.post.zybx.utils.StringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/30 11:40
 *
 * @description
 */
@Service
public class AlertCountServiceImpl implements AlertCountService {

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public CommonPage countModelPage(CommonPage page) {
        String totalSql = "select count(1) from v_count_model";
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);
        String pageSql = "select " +
                "model_id," +
                "model_name," +
                "total_alert_num," +
                "last_alert_num," +
                "not_check_alert_num," +
                "check_alert_num " +
                "from v_count_model limit ?,?";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(pageSql,(page.getCurrent()-1) * page.getSize(), page.getSize());
        page.setCount(total);
        page.setList(mapList);
        return page;
    }

    @Override
    public CommonPage countDeptPage(CommonPage page) {
        String totalSql = "select count(1) from tb_count_dept";
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);

        String pageSql = "select " +
                "city_name," +
                "dist_name," +
                "total_alert_num," +
                "last_alert_num," +
                "not_check_alert_num," +
                "check_alert_num " +
                "from " +
                "   tb_count_dept " +
                "limit ?,?";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(pageSql,(page.getCurrent()-1) * page.getSize(), page.getSize());
        page.setCount(total);
        page.setList(mapList);
        return page;
    }

    @Override
    public CommonPage searchCountDeptPage(CommonPage page, Map map) {
        String totalSql = "select count(1) from tb_count_dept";
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);

        Integer modelId = (Integer) map.get("modelId");
        String cityName = (String) map.get("cityName");
        String distName = (String) map.get("distName");

        StringBuilder pageSql = new StringBuilder();

        pageSql.append("SELECT \n" +
                "  td.city_name,\n" +
                "  td.city_id,\n" +
                "  td.dist_name,\n" +
                "  td.dist_id,\n" +
                "  sum(t1.num) total_alert_num,\n" +
                "  sum(case t1.`status` when '0' then t1.num else 0 END ) last_alert_num,\n" +
                "  sum(case t1.`status` when '1' then t1.num else 0 END ) not_check_alert_num,\n" +
                "  sum(case t1.`status` when '2' then t1.num else 0 END ) check_alert_num\n" +
                "from (\n" +
                "SELECT\n" +
                "  ta.branch_id,\n" +
                "  ta.`status`,\n" +
                "  count(1) num\n" +
                "FROM\n" +
                "    tb_user_alert ta\n" +
                "  WHERE 1= 1");

        if (modelId != 0){
            pageSql.append(" AND ta.model_id ='" + modelId +"'\n");
        }

        pageSql.append(" GROUP BY\n" +
                "    ta.branch_id,\n" +
                "    ta.`status`\n" +
                ") t1\n" +
                "LEFT JOIN\n" +
                "  tb_dept td on td.branch_id = t1.branch_id\n" +
                "GROUP BY\n" +
                "  td.city_name,\n" +
                "  td.city_id,\n" +
                "  td.dist_id,\n" +
                "  td.dist_name ");
        pageSql.append("having 1=1 \n");
        if (StringUtil.isNotEmpty(cityName)){
            pageSql.append("AND td.city_name like '%" + cityName + "%' \n");
        }
        if (StringUtil.isNotEmpty(distName)){
            pageSql.append("AND td.dist_name like '%" + distName + "%' \n");
        }

        pageSql.append(" limit ?,?");

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(pageSql.toString(),page.getCurrent()-1,page.getSize());

        page.setCount(total);
        page.setList(mapList);

        return page;
    }
}
