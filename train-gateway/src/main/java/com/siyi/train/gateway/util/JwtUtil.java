package com.siyi.train.gateway.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JwtUtil
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/25 17:32
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
public class JwtUtil {
    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String KEY = "12306";

    public static String createToken(Long id, String mobile) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 内容
        payload.put("id", id);
        payload.put("mobile", mobile);
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }

    public static boolean validate(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
            // validate包含了verify
            boolean validate = jwt.validate(0);
            log.info("JWT token校验结果：{}", validate);
            return validate;
        } catch (Exception e) {
            log.error("JWT token校验异常", e);
            return false;
        }
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(KEY.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        log.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        String token = createToken(1L, "123");

        validate(token);

        getJSONObject(token);
    }
}
