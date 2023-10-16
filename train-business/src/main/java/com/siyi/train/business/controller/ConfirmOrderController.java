package com.siyi.train.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.service.ConfirmOrderService;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    private final ConfirmOrderService confirmOrderService;

    public ConfirmOrderController(ConfirmOrderService confirmOrderService) {
        this.confirmOrderService = confirmOrderService;
    }

    @SentinelResource(value = "confirmOrderDo", blockHandler = "doConfirmBlock")
    @PostMapping("/do")
    public Result<Object> save(@Valid @RequestBody ConfirmOrderDoDto dto) {
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
