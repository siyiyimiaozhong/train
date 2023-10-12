package com.siyi.train.member.config;

import com.siyi.train.common.interceptor.LogInterceptor;
import com.siyi.train.common.interceptor.MemberInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MemberMvcConfig
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 18:17
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Configuration
public class MemberMvcConfig implements WebMvcConfigurer {

    private final MemberInterceptor memberInterceptor;
    private final LogInterceptor logInterceptor;

    public MemberMvcConfig(MemberInterceptor memberInterceptor, LogInterceptor logInterceptor) {
        this.memberInterceptor = memberInterceptor;
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.logInterceptor);

        registry.addInterceptor(this.memberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/member/send-code",
                        "/member/login"
                );
    }
}
