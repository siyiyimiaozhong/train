package com.siyi.train.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.service.ConfirmOrderService;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    private final ConfirmOrderService confirmOrderService;
    private final StringRedisTemplate redisTemplate;
    @Value("${spring.profiles.active}")
    private String env;

    public ConfirmOrderController(ConfirmOrderService confirmOrderService, StringRedisTemplate redisTemplate) {
        this.confirmOrderService = confirmOrderService;
        this.redisTemplate = redisTemplate;
    }

    @SentinelResource(value = "confirmOrderDo", blockHandler = "doConfirmBlock")
    @PostMapping("/do")
    public Result<Object> save(@Valid @RequestBody ConfirmOrderDoDto dto) {

        if (!env.equals("dev")) {
            // 图形验证码校验
            String imageCodeToken = dto.getImageCodeToken();
            String imageCode = dto.getImageCode();
            String imageCodeRedis = this.redisTemplate.opsForValue().get(imageCodeToken);
            log.info("从redis中获取到的验证码：{}", imageCodeRedis);
            if (ObjectUtils.isEmpty(imageCodeRedis)) {
                return Result.error(ResultCode.MEMBER_VERIFICATION_CODE_EXPIRED);
            }
            // 验证码校验，大小写忽略，提升体验，比如Oo Vv Ww容易混
            if (!imageCodeRedis.equalsIgnoreCase(imageCode)) {
                return Result.error(ResultCode.MEMBER_VERIFICATION_CODE_ERROR);
            } else {
                // 验证通过后，移除验证码
                this.redisTemplate.delete(imageCodeToken);
            }
        }

        this.confirmOrderService.doConfirm(dto);
        return Result.success();
    }


    /**
     * 降级方法，需要包换限流方法的所有参数和BlockException参数
     * @param dto
     * @param e
     */
    public Result<Object> doConfirmBlock(ConfirmOrderDoDto dto, BlockException e) {
        log.info("购票请求被限流： {}", dto);
//        throw new BusinessException(ResultCode.BUSINESS_CONFIRM_ORDER_FLOW_EXCEPTION);
        return Result.error(ResultCode.BUSINESS_CONFIRM_ORDER_FLOW_EXCEPTION);
    }
}
