package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainSeatQueryDto;
import com.siyi.train.business.dto.TrainSeatSaveDto;
import com.siyi.train.business.vo.TrainSeatQueryVo;
import com.siyi.train.business.service.TrainSeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-seat")
public class TrainSeatController {

    private final TrainSeatService trainSeatService;

    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody TrainSeatSaveDto dto) {
        this.trainSeatService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<TrainSeatQueryVo>> queryList(@Valid TrainSeatQueryDto dto) {
        PageVo<TrainSeatQueryVo> list = this.trainSeatService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.trainSeatService.delete(id);
        return Result.success();
    }

}
