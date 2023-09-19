package com.siyi.train.business.controller.admin;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.vo.DailyTrainQueryVo;
import com.siyi.train.business.service.DailyTrainService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainAdminController {

    private final DailyTrainService dailyTrainService;

    public DailyTrainAdminController(DailyTrainService dailyTrainService) {
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

    @GetMapping("/gen-daily/{date}")
    public Result<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        this.dailyTrainService.genDaily(date);
        return Result.success();
    }

}
