package com.siyi.train.business.controller.admin;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainCarriageQueryDto;
import com.siyi.train.business.dto.DailyTrainCarriageSaveDto;
import com.siyi.train.business.vo.DailyTrainCarriageQueryVo;
import com.siyi.train.business.service.DailyTrainCarriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyTrainCarriageAdminController {

    private final DailyTrainCarriageService dailyTrainCarriageService;

    public DailyTrainCarriageAdminController(DailyTrainCarriageService dailyTrainCarriageService) {
        this.dailyTrainCarriageService = dailyTrainCarriageService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody DailyTrainCarriageSaveDto dto) {
        this.dailyTrainCarriageService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainCarriageQueryVo>> queryList(@Valid DailyTrainCarriageQueryDto dto) {
        PageVo<DailyTrainCarriageQueryVo> list = this.dailyTrainCarriageService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.dailyTrainCarriageService.delete(id);
        return Result.success();
    }

}
