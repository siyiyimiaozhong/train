package com.siyi.train.member.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.member.dto.MemberDto;
import com.siyi.train.member.dto.MemberLoginDto;
import com.siyi.train.member.dto.MemberSendCodeDto;
import com.siyi.train.member.service.MemberService;
import com.siyi.train.member.vo.MemberLoginVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MemberController
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 18:15
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public Result<Long> register(@Valid @RequestBody MemberDto dto) {
        long id = this.memberService.register(dto);
        return Result.success(id);
    }

    @PostMapping("/send-code")
    public Result<String> sendCode(@Valid @RequestBody MemberSendCodeDto dto) {
        this.memberService.sendCode(dto);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<MemberLoginVo> login(@Valid @RequestBody MemberLoginDto dto) {
        MemberLoginVo memberLoginVo = this.memberService.login(dto);
        return Result.success(memberLoginVo);
    }
}
