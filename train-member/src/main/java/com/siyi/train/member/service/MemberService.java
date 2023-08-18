package com.siyi.train.member.service;

import com.siyi.train.member.dto.MemberDto;

public interface MemberService {
    int count();

    /**
     * 根据手机号注册
     * @param dto
     * @return
     */
    long register(MemberDto dto);
}
