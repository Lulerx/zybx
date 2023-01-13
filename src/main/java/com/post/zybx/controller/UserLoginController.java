package com.post.zybx.controller;

import com.post.zybx.bean.TbLogin;
import com.post.zybx.common.CommonResult;
import com.post.zybx.service.TbLoginService;
import com.post.zybx.utils.DateUtil;
import com.post.zybx.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * create by Luler on 2023/1/12 11:16
 *
 * @description
 */
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    @Qualifier("tbLoginService")
    private TbLoginService loginService;

    @Value("${reg.password}")
    private String regPwd;

    @PostMapping("/reg")
    public CommonResult register(@RequestBody TbLogin login){
        CommonResult result = loginService.register(login);
        return result;
    }

    @PostMapping(value = "/login", consumes = "application/json" )
    public CommonResult login(@RequestBody TbLogin login, HttpServletRequest request) {
        HttpSession session = request.getSession();

        CommonResult<TbLogin> result = loginService.userLogin(login);
        if (result.getStatus() == 200){
            session.setAttribute("username", result.getData().getNickname());
            String today = DateUtil.formatDate(new Date());
            session.setAttribute("today", today);
        }
        return result;
    }


    @GetMapping("/logout")
    public CommonResult logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("today");
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            logger.error("================退出登录发生异常，{}", e.getMessage());
        }
        return new CommonResult(null, 200, "退出成功");
    }

    @PostMapping(value = "/regVerify" )
    public CommonResult regVerify(@RequestParam String password){
        if (StringUtil.isEmpty(password)){
            return new CommonResult(null, 500, "口令不能为空");
        }
        if (password.equals(regPwd)){
            return new CommonResult(null, 200, "口令验证成功");
        }else {
            return new CommonResult(null, 500, "请输入正确的口令");
        }
    }

}
