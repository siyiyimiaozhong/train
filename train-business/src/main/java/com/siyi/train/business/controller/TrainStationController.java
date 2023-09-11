package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainStationQueryDto;
import com.siyi.train.business.dto.TrainStationSaveDto;
import com.siyi.train.business.vo.TrainStationQueryVo;
import com.siyi.train.business.service.TrainStationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-station")
public class TrainStationController {

    private final TrainStationService trainStationService;

    public TrainStationController(TrainStationService trainStationService) {
        this.trainStationService = trainStationService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody TrainStationSaveDto dto) {
        this.trainStationService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<TrainStationQueryVo>> queryList(@Valid TrainStationQueryDto dto) {
        PageVo<TrainStationQueryVo> list = this.trainStationService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.trainStationService.delete(id);
        return Result.success();
    }

}
