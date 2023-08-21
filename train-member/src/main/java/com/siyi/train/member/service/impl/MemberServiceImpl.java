package com.siyi.train.member.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.member.dto.MemberDto;
import com.siyi.train.member.mapper.MemberMapper;
import com.siyi.train.member.pojo.Member;
import com.siyi.train.member.pojo.MemberExample;
import com.siyi.train.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public long register(MemberDto dto) {
        String mobile = dto.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = this.memberMapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException(ResultCode.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        this.memberMapper.insert(member);
        return member.getId();
    }
}
