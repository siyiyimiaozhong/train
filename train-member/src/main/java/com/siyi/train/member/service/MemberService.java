package com.siyi.train.member.service;

import com.siyi.train.member.dto.MemberDto;
import com.siyi.train.member.dto.MemberLoginDto;
import com.siyi.train.member.dto.MemberSendCodeDto;
import com.siyi.train.member.vo.MemberLoginVo;

public interface MemberService {
    int count();

    /**
     * 根据手机号注册
     * @param dto
     * @return
     */
    long register(MemberDto dto);

    /**
     * 发送验证码
     * @param dto
     */
    void sendCode(MemberSendCodeDto dto);

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    MemberLoginVo login(MemberLoginDto dto);
}
