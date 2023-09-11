package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainCarriageQueryDto;
import com.siyi.train.business.dto.TrainCarriageSaveDto;
import com.siyi.train.business.vo.TrainCarriageQueryVo;
import com.siyi.train.business.service.TrainCarriageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageController {

    private final TrainCarriageService trainCarriageService;

    public TrainCarriageController(TrainCarriageService trainCarriageService) {
        this.trainCarriageService = trainCarriageService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody TrainCarriageSaveDto dto) {
        this.trainCarriageService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<TrainCarriageQueryVo>> queryList(@Valid TrainCarriageQueryDto dto) {
        PageVo<TrainCarriageQueryVo> list = this.trainCarriageService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.trainCarriageService.delete(id);
        return Result.success();
    }

}
