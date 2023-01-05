package com.post.zybx.service.impl;

import com.post.zybx.bean.TbModel;
import com.post.zybx.mapper.TbModelMapper;
import com.post.zybx.service.TbModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by Luler on 2023/1/5 11:32
 *
 * @description
 */
@Service
public class TbModelServiceImpl implements TbModelService {

    @Resource
    private TbModelMapper tbModelMapper;


    @Override
    public List<TbModel> findAll() {
        List<TbModel> list = tbModelMapper.selectList(null);
        return list;
    }
}
