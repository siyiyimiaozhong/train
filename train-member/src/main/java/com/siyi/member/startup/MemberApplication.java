package com.siyi.member.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: MemberApplication
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/16 15:15
 * @Description:
 * @Version 1.0.0
 */
@SpringBootApplication
@ComponentScan("com.siyi")
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
