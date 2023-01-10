package com.post.zybx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by Luler on 2022/12/26 9:39
 *
 * @description
 */
@Controller
public class BaseController {

    @RequestMapping(value = "/")
    public String index(){
        return "indexShow";
    }
    /**
     * 用于a标签超链接跳转另一个页面
     * @param page
     * @return
     */
    @RequestMapping(value = "/{page}/getPage")
    public String into(@PathVariable String page){
        return page;
    }

}
