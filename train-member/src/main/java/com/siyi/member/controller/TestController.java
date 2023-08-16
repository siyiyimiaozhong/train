package com.siyi.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/16 15:16
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/a")
    public String a() {
        return "a";
    }
}
