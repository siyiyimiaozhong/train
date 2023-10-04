package com.siyi.train.business.controller;

import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.service.ConfirmOrderService;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    private final ConfirmOrderService confirmOrderService;

    public ConfirmOrderController(ConfirmOrderService confirmOrderService) {
        this.confirmOrderService = confirmOrderService;
    }

    @PostMapping("/do")
    public Result<Object> save(@Valid @RequestBody ConfirmOrderDoDto dto) {
        this.confirmOrderService.doConfirm(dto);
        return Result.success();
    }

}
