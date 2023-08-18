package com.siyi.train.member.controller;

import com.siyi.train.member.service.MemberService;
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
@RequestMapping("/")
public class TestController {

    private final MemberService memberService;

    public TestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/test")
    public int a() {
        return this.memberService.count();
    }
}
