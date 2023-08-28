package com.siyi.train.member.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.util.JwtUtil;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.member.dto.MemberDto;
import com.siyi.train.member.dto.MemberLoginDto;
import com.siyi.train.member.dto.MemberSendCodeDto;
import com.siyi.train.member.mapper.MemberMapper;
import com.siyi.train.member.pojo.Member;
import com.siyi.train.member.pojo.MemberExample;
import com.siyi.train.member.service.MemberService;
import com.siyi.train.member.vo.MemberLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: MemberServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 17:20
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
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
        Member member = selectByMobile(mobile);
        if (ObjectUtil.isNotNull(member)) {
            throw new BusinessException(ResultCode.MEMBER_MOBILE_EXIST);
        }

        member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        this.memberMapper.insert(member);
        return member.getId();
    }

    @Override
    public void sendCode(MemberSendCodeDto dto) {
        String mobile = dto.getMobile();
        Member member = selectByMobile(mobile);

        // 如果手机号不存在则插入记录
        if (ObjectUtil.isNull(member)) {
            log.info("【{}】不存在，插入一条记录", dto.getMobile());
            member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            this.memberMapper.insert(member);
        } else {
            log.info("【{}】存在，不插入记录", dto.getMobile());
        }

        // 生成验证码
        String code = RandomUtil.randomString(4);
        log.info("【{}】验证码为： {}", dto.getMobile(), code);

        // 保存短信记录

        // 对接短信通道

    }

    @Override
    public MemberLoginVo login(MemberLoginDto dto) {
        String mobile = dto.getMobile();
        String code = dto.getCode();
        Member member = selectByMobile(mobile);

        // 如果手机号不存在则提示
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException(ResultCode.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        String codeStr = "1234";
        if (!codeStr.equals(code)) {
            throw new BusinessException(ResultCode.MEMBER_MOBILE_CODE_ERROR);
        }

        return MemberLoginVo.builder()
                .id(member.getId())
                .mobile(member.getMobile())
                .token(JwtUtil.createToken(member.getId(), member.getMobile()))
                .build();
    }

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = this.memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(members)) {
            return null;
        } else {
            return members.get(0);
        }
    }
}
