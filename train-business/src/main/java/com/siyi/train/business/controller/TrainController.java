package com.siyi.train.business.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainQueryDto;
import com.siyi.train.business.dto.TrainSaveDto;
import com.siyi.train.business.vo.TrainQueryVo;
import com.siyi.train.business.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody TrainSaveDto dto) {
        this.trainService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<TrainQueryVo>> queryList(@Valid TrainQueryDto dto) {
        PageVo<TrainQueryVo> list = this.trainService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.trainService.delete(id);
        return Result.success();
    }

}
