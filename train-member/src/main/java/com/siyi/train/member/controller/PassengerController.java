package com.siyi.train.member.controller;

import com.siyi.train.common.context.LoginMemberContext;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import com.siyi.train.member.dto.PassengerQueryDto;
import com.siyi.train.member.dto.PassengerSaveDto;
import com.siyi.train.member.service.PassengerService;
import com.siyi.train.member.vo.PassengerQueryVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: PassengerController
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 14:58
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody PassengerSaveDto dto) {
        this.passengerService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<Object> queryList(@Valid PassengerQueryDto dto) {
        dto.setMemberId(LoginMemberContext.getId());
        PageVo<PassengerQueryVo> pageVo = this.passengerService.queryList(dto);
        return Result.success(pageVo);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable("id") Long id) {
        this.passengerService.delete(id);
        return Result.success();
    }
}
