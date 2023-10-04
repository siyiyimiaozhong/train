package com.siyi.train.business.startup;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: BusinessApplication
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/6 15:38
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@ComponentScan("com.siyi")
@EnableFeignClients("com.siyi.train.business.feign")
@MapperScan("com.siyi.train.business.mapper")
@SpringBootApplication
public class BusinessApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BusinessApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        log.info("项目启动成功!");
        log.info("项目地址：{}", getProjectAddress(environment));
    }

    /**
     * 获取项目地址
     * @param environment
     * @return
     */
    private static String getProjectAddress(ConfigurableEnvironment environment) {
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        List<String> ips = new LinkedList<>();
        ips.add("127.0.0.1");
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ips.add(inetAddress.getHostAddress());
        } catch (Exception e) {
        }
        return ips.stream()
                .map(ip -> String.format("http://%s:%s%s", ip, port, null == contextPath ? "" : contextPath))
                .collect(Collectors.joining(" "));
    }
}
