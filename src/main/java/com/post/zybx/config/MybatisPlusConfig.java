package com.post.zybx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by Luler on 2022/12/26 16:19
 *
 * @description 分页插件拦截器配置
 */
@Configuration
@MapperScan("com.post.zybx.mapper")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//创建乐观锁拦截器 OptimisticLockerInnerInterceptor
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); //插件分页拦截器，我的是mysql
        return interceptor;
    }
}
