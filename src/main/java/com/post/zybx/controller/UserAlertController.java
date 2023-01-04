package com.post.zybx.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.dto.CommonResult;
import com.post.zybx.dto.CommonPage;
import com.post.zybx.service.PageQuerier;
import com.post.zybx.service.UserAlertService;
import com.post.zybx.service.impl.ExcelExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private PageQuerier<UserAlert> pageQuerier;

    /**
     * 列表查询
     * @param myPage 分页
     * @return
     */
    @GetMapping("/list")
    public CommonResult list(CommonPage myPage){
        Page<UserAlert> pageVo = userAlertService.findPageVo(myPage);
        return new CommonResult(pageVo, 0, "success");
    }


    /**
     * 搜索查询功能
     */
    @GetMapping("/search")
    public CommonResult searchList(CommonPage commonPage,
                                   @RequestParam("treeValue") String treeValue ,
                                   @RequestParam("status") Integer status ,
                                   @RequestParam("modelId") Integer modelId ){
        Map<String, Object> map = new HashMap<>();
        map.put("treeValue",treeValue);
        map.put("status",status);
        map.put("modelId",modelId);
        Page<UserAlert> list = userAlertService.findPageByMap(commonPage, map);
        return new CommonResult(list, 0, "success");
    }


    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        String fileName = null;
        try {
            fileName = URLEncoder.encode("模型预警数据", "UTF-8").replaceAll("\\+", "%20");
            new ExcelExporter<UserAlert>(this.pageQuerier).write(response,fileName,UserAlert.class);
        } catch (Exception e) {
            logger.error("=============下载出错：{}",e.getMessage());
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }

    }


}
