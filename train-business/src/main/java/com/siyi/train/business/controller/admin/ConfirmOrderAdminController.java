package com.siyi.train.business.controller.admin;

import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.dto.ConfirmOrderQueryDto;
import com.siyi.train.business.service.ConfirmOrderService;
import com.siyi.train.business.vo.ConfirmOrderQueryVo;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderAdminController {

    private final ConfirmOrderService confirmOrderService;

    public ConfirmOrderAdminController(ConfirmOrderService confirmOrderService) {
        this.confirmOrderService = confirmOrderService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody ConfirmOrderDoDto dto) {
        this.confirmOrderService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<ConfirmOrderQueryVo>> queryList(@Valid ConfirmOrderQueryDto dto) {
        PageVo<ConfirmOrderQueryVo> list = this.confirmOrderService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.confirmOrderService.delete(id);
        return Result.success();
    }

}
