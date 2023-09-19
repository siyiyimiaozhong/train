package com.siyi.train.business.controller;

import com.siyi.train.business.service.TrainService;
import com.siyi.train.business.vo.TrainQueryVo;
import com.siyi.train.common.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
    @GetMapping("/query-all")
    public Result<List<TrainQueryVo>> queryList() {
        List<TrainQueryVo> list = this.trainService.queryAll();
        return Result.success(list);
    }

}
