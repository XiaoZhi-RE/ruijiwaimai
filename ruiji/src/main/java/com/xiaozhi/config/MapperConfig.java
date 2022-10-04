package com.xiaozhi.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 20232
 */
@Configuration
@MapperScan("com.xiaozhi.mapper")
public class MapperConfig {


    /**
     * 导入分页查询插件
     *
     * @return interceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //        MybatisPlusInterceptor
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //        添加适配器addInnerInterceptor
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


}
