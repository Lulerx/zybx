package com.post.zybx.controller;

import com.post.zybx.bean.TbModel;
import com.post.zybx.common.CommonResult;
import com.post.zybx.service.TbModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by Luler on 2023/1/5 17:01
 *
 * @description
 */
@RestController
@RequestMapping("/tbModel")
public class TbModelController {
    private static final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Autowired
    private TbModelService tbModelService;

    @GetMapping("/list")
    public CommonResult list(){
        List<TbModel> list = tbModelService.findAll();
        return new CommonResult(list, 0, "success");
    }


}
