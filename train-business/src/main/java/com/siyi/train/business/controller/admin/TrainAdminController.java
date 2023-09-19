package com.siyi.train.business.controller.admin;

import com.siyi.train.business.service.TrainSeatService;
import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainQueryDto;
import com.siyi.train.business.dto.TrainSaveDto;
import com.siyi.train.business.vo.TrainQueryVo;
import com.siyi.train.business.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {

    private final TrainService trainService;
    private final TrainSeatService trainSeatService;

    public TrainAdminController(TrainService trainService, TrainSeatService trainSeatService) {
        this.trainService = trainService;
        this.trainSeatService = trainSeatService;
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

    @GetMapping("/query-all")
    public Result<List<TrainQueryVo>> queryList() {
        List<TrainQueryVo> list = this.trainService.queryAll();
        return Result.success(list);
    }

    @GetMapping("/gen-seat/{trainCode}")
    public Result<Object> genSeat(@PathVariable String trainCode) {
        this.trainSeatService.genTrainSeat(trainCode);
        return Result.success();
    }

}
