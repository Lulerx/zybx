package com.post.zybx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.post.zybx.bean.TbDept;
import com.post.zybx.mapper.TbDeptMapper;
import com.post.zybx.service.TbDeptService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2023/1/3 16:36
 *
 * @description
 */
@Service
public class TbDeptServiceImpl implements TbDeptService {

    @Resource
    private TbDeptMapper tbDeptMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TbDept> findAllCity() {
        String sql = "SELECT td.city_id,td.city_name from tb_dept td GROUP BY td.city_id, td.city_name";
        List<TbDept> query = jdbcTemplate.query(sql, (res, i) -> {
            TbDept dept = new TbDept();
            dept.setCityId(res.getString("city_id"));
            dept.setCityName(res.getString("city_name"));
            return dept;
        });
        return query;
    }

    @Override
    public List<TbDept> findDistByCityId(String cityId) {
        QueryWrapper<TbDept> wrapper = new QueryWrapper<>();
        wrapper.eq("city_id", cityId);
        wrapper.select("distinct dist_id, dist_name");
        List<TbDept> list = tbDeptMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List getTreeDept() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        List<TbDept> QueryCityList = findAllCity();

        for (TbDept city : QueryCityList) {
            Map<String, Object> cityMap = new HashMap<>();
            cityMap.put("title",city.getCityName());
            cityMap.put("id", city.getCityId());
            List<TbDept> queryDeptList = findDistByCityId(city.getCityId());

            List<Map<String, String>> deptList = new ArrayList<>();
            for (TbDept dist : queryDeptList) {

                Map<String, String> distMap = new HashMap<>();
                distMap.put("title",dist.getDistName());
                distMap.put("id",dist.getDistId());
                deptList.add(distMap);
            }
            cityMap.put("children", deptList);
            list.add(cityMap);
        }
        return list;
    }
}
