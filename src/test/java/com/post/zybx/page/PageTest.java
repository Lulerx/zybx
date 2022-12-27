package com.post.zybx.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.post.zybx.bean.UserAlert;
import com.post.zybx.mapper.UserAlertMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * create by Luler on 2022/12/26 16:30
 *
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageTest {

    @Resource
    private UserAlertMapper userAlertMapper;

    @Test
    public void testPage() {
        Page<UserAlert> page = new Page<>(1, 5);
        Page<UserAlert> userAlertPage = userAlertMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal()); //获取记录总数
    }

    /*@Test
    public void testPage(){
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);
        System.out.println("当前页数据:"+page.getRecords());
        System.out.println("总分页数量:"+page.getPages());
        System.out.println("总记录数量:"+page.getTotal());
        System.out.println("是否有下一页:"+page.hasNext());
        System.out.println("是否有上一页:"+page.hasPrevious());
    }*/

}
