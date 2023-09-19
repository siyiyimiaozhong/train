package com.siyi.train.business.controller.admin;

import com.siyi.train.business.dto.StationQueryDto;
import com.siyi.train.business.dto.StationSaveDto;
import com.siyi.train.business.service.StationService;
import com.siyi.train.business.vo.StationQueryVo;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/station")
public class StationAdminController {

    private final StationService stationService;

    public StationAdminController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody StationSaveDto dto) {
        this.stationService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<StationQueryVo>> queryList(@Valid StationQueryDto dto) {
        PageVo<StationQueryVo> list = this.stationService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.stationService.delete(id);
        return Result.success();
    }

    @GetMapping("/query-all")
    public Result<List<StationQueryVo>> queryList() {
        List<StationQueryVo> list = stationService.queryAll();
        return Result.success(list);
    }
}
