package com.post.zybx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.dto.CommonResult;
import com.post.zybx.service.UserAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by Luler on 2022/12/26 16:48
 *
 * @description
 */
@RestController
@RequestMapping("/alertData")
public class UserAlertController {
    private static final Logger logger = LoggerFactory.getLogger(UserAlertController.class);

    @Autowired
    private UserAlertService userAlertService;

    @GetMapping("/list")
    public CommonResult list(@RequestParam("curr") long curr,
                             @RequestParam("size") long size){
        if (curr == 0) curr = 1;
        if (size == 0) size = 5;
        Page<UserAlert> pageVo = userAlertService.findPageVo(curr, size);
        return new CommonResult(pageVo, 0, "success");
    }


}
