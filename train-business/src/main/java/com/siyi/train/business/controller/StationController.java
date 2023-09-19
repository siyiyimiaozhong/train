package com.siyi.train.business.controller;

import com.siyi.train.business.service.StationService;
import com.siyi.train.business.vo.StationQueryVo;
import com.siyi.train.common.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/query-all")
    public Result<List<StationQueryVo>> queryList() {
        List<StationQueryVo> list = stationService.queryAll();
        return Result.success(list);
    }
}
