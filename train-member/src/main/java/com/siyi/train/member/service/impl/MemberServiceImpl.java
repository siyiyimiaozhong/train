package com.siyi.train.member.service.impl;

import com.siyi.train.member.mapper.MemberMapper;
import com.siyi.train.member.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: MemberServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 17:20
 * @Description:
 * @Version 1.0.0
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public int count() {
        return Math.toIntExact(this.memberMapper.countByExample(null));
    }
}
