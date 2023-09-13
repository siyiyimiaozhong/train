package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.vo.DailyTrainQueryVo;
import com.siyi.train.business.service.DailyTrainService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainController {

    private final DailyTrainService dailyTrainService;

    public DailyTrainController(DailyTrainService dailyTrainService) {
        this.dailyTrainService = dailyTrainService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody DailyTrainSaveDto dto) {
        this.dailyTrainService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainQueryVo>> queryList(@Valid DailyTrainQueryDto dto) {
        PageVo<DailyTrainQueryVo> list = this.dailyTrainService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.dailyTrainService.delete(id);
        return Result.success();
    }

}
