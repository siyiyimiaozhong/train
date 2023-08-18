package com.siyi.train.member.startup;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: MemberApplication
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/16 15:15
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@SpringBootApplication
@ComponentScan("com.siyi")
@MapperScan("com.siyi.train.member.mapper")
public class MemberApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MemberApplication.class, args);
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
