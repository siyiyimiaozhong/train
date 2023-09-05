package com.siyi.train.common.context;

import com.siyi.train.common.vo.MemberLoginVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: LoginMemberContext
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 18:08
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
public class LoginMemberContext {

    private static ThreadLocal<MemberLoginVo> member = new ThreadLocal<>();

    public static MemberLoginVo getMember() {
        return member.get();
    }

    public static void setMember(MemberLoginVo member) {
        LoginMemberContext.member.set(member);
    }

    public static Long getId() {
        try {
            return member.get().getId();
        } catch (Exception e) {
            log.error("获取登录会员信息异常", e);
            throw e;
        }
    }

}