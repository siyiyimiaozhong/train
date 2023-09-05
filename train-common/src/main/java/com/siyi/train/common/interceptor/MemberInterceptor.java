package com.siyi.train.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.siyi.train.common.context.LoginMemberContext;
import com.siyi.train.common.util.JwtUtil;
import com.siyi.train.common.vo.MemberLoginVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName: MemberInterceptor
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 18:14
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Component
public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取header的token参数
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            log.info("获取会员登录token：{}", token);
            JSONObject loginMember = JwtUtil.getJSONObject(token);
            log.info("当前登录会员：{}", loginMember);
            MemberLoginVo member = JSONUtil.toBean(loginMember, MemberLoginVo.class);
            LoginMemberContext.setMember(member);
        }
        return true;
    }

}

