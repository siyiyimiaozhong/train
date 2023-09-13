package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainSeatQueryDto;
import com.siyi.train.business.dto.DailyTrainSeatSaveDto;
import com.siyi.train.business.vo.DailyTrainSeatQueryVo;
import com.siyi.train.business.service.DailyTrainSeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatController {

    private final DailyTrainSeatService dailyTrainSeatService;

    public DailyTrainSeatController(DailyTrainSeatService dailyTrainSeatService) {
        this.dailyTrainSeatService = dailyTrainSeatService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody DailyTrainSeatSaveDto dto) {
        this.dailyTrainSeatService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainSeatQueryVo>> queryList(@Valid DailyTrainSeatQueryDto dto) {
        PageVo<DailyTrainSeatQueryVo> list = this.dailyTrainSeatService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.dailyTrainSeatService.delete(id);
        return Result.success();
    }

}
