package com.post.zybx.controller;

import com.post.zybx.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * create by Luler on 2022/12/26 9:39
 *
 * @description
 */
@Controller
public class BaseController {

    @RequestMapping(value = "/")
    public String index(){
        return "login";
    }
    /**
     * 用于a标签超链接跳转另一个页面
     * @param page
     * @return
     */
    @RequestMapping(value = "/{page}/getPage")
    public String into(@PathVariable String page, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if (StringUtil.isEmpty(username)){
            return "login";
        }
        return page;
    }

}
