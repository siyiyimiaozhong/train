package com.siyi.train.business.config;

import com.siyi.train.common.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: BusinessMvcConfig
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/14 15:27
 * @Description:
 * @Version 1.0.0
 */
@Configuration
public class BusinessMvcConfig implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;

    public BusinessMvcConfig(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.logInterceptor)
                .addPathPatterns("/**");
    }
}
