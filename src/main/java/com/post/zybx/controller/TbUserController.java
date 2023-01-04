package com.post.zybx.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.TbUser;
import com.post.zybx.dto.CommonResult;
import com.post.zybx.dto.CommonPage;
import com.post.zybx.listener.ExcelListener;
import com.post.zybx.service.TbUserService;
import com.post.zybx.service.UserAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by Luler on 2022/12/27 10:47
 *
 * @description
 */
@RestController
@RequestMapping("/tbUser")
public class TbUserController {
    private static final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Autowired
    private TbUserService tbUserService;

    @Resource
    private UserAlertService userAlertService;

    @Value("${web.upload-path}")
    private String uploadPath;

    @GetMapping("/list")
    public CommonResult list(CommonPage commonPage){
        Page<TbUser> pageVo = tbUserService.findPageVo(commonPage);
        return new CommonResult(pageVo, 0, "success");
    }


    @GetMapping("/addBath")
    public CommonResult addBath(){
        long startTime = System.currentTimeMillis();

        for (int j = 0; j < 10; j++) {
            List<TbUser> list = new ArrayList<>();

            for (int i = 0; i < 10000; i++) {
                TbUser user = new TbUser();
                user.setPhone("13023456100");
                user.setOrderNum("76440000057"+ j+i);

                list.add(user);
            }

        tbUserService.inertListVo(list);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("函数执行时间：" + (endTime - startTime) + "ms");
        return CommonResult.success(null);

    }

    @PostMapping("/fileUpload")
    public CommonResult upload(MultipartFile file) {
        long startTime = System.currentTimeMillis();
        if (!file.isEmpty()) {
            //存储文件的路径 + 文件分隔符 （'/'）+ 文件名
            File filePath = new File(uploadPath + File.separator + file.getOriginalFilename());
            //路径不存在就创建一个
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //保存文件
            try {
                file.transferTo(filePath);
                logger.info("文件上传成功！");
            } catch (IOException e) {
                logger.error("文件上传时保存文件出错：{}", e.getMessage());
            }
            //Excel 文件读取，通过 Listener 进行存储操作
            EasyExcel.read(filePath, TbUser.class,new ExcelListener(tbUserService)).sheet().doRead();

            filePath.delete();
            long endTime = System.currentTimeMillis();
            System.out.println("函数执行时间：" + (endTime - startTime) + "ms");

            return CommonResult.success("success");
        }else {
            return new CommonResult(null, 500, "文件不存在");
        }
    }


    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意有同学反应使用swagger会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), TbUser.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(tbUserService.findAllList());
        } catch (Exception e) {
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


    @PostMapping("/alertBuild")
    public CommonResult alertBuild() {
        long startTime = System.currentTimeMillis();
        logger.info("===========开始生成预警数据");
        //这里包括将预警状态0-->1，生成新的预警
        tbUserService.buildAlert();
        logger.info("============开始更新旧预警状态");
        //这里是预警数据对碰，生成 status=2 的数据
        userAlertService.alertCheck();
        logger.info("============预警已生成！");
        long endTime = System.currentTimeMillis();
        System.out.println("生成预警总耗时：" + (endTime - startTime) + "ms");
        return CommonResult.success(null);
    }


}
