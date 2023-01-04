package com.post.zybx.controller;

import com.post.zybx.dto.CommonPage;
import com.post.zybx.dto.CommonResult;
import com.post.zybx.service.AlertCountService;
import com.post.zybx.service.TbDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/30 9:35
 *
 * @description
 */
@RestController
@RequestMapping("/alertCount")
public class AlertCountController {
    private static final Logger logger = LoggerFactory.getLogger(AlertCountController.class);

    @Autowired
    private AlertCountService countService;

    @Autowired
    private TbDeptService tbDeptService;

    @GetMapping("/modelCount")
    public CommonResult modelCount(CommonPage page) {
        CommonPage commonPage = countService.countModelPage(page);

        return new CommonResult(commonPage,0,"success");
    }


    @GetMapping("/deptCount")
    public CommonResult deptCount(CommonPage page) {
        CommonPage commonPage = countService.countDeptPage(page);

        return new CommonResult(commonPage,0,"success");
    }


    @GetMapping("/searchDeptCount")
    public CommonResult searchDeptCount(CommonPage page,
                                        @RequestParam("cityName") String cityName,
                                        @RequestParam("distName") String distName,
                                        @RequestParam("modelId") int modelId) {
        Map<String, Object> map = new HashMap<>();
        map.put("cityName", cityName);
        map.put("distName", distName);
        map.put("modelId", modelId);

        CommonPage commonPage = countService.searchCountDeptPage(page, map);

        return new CommonResult(commonPage,0,"success");
    }


    @GetMapping("/treeDept")
    public CommonResult TreeDept(){
        List list = tbDeptService.getTreeDept();
        return CommonResult.success(list);
    }

}
