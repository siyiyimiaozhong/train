package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainStationQueryDto;
import com.siyi.train.business.dto.DailyTrainStationSaveDto;
import com.siyi.train.business.vo.DailyTrainStationQueryVo;
import com.siyi.train.business.service.DailyTrainStationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationController {

    private final DailyTrainStationService dailyTrainStationService;

    public DailyTrainStationController(DailyTrainStationService dailyTrainStationService) {
        this.dailyTrainStationService = dailyTrainStationService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody DailyTrainStationSaveDto dto) {
        this.dailyTrainStationService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainStationQueryVo>> queryList(@Valid DailyTrainStationQueryDto dto) {
        PageVo<DailyTrainStationQueryVo> list = this.dailyTrainStationService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.dailyTrainStationService.delete(id);
        return Result.success();
    }

}
