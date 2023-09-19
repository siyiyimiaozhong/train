package com.siyi.train.business.controller.admin;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainTicketQueryDto;
import com.siyi.train.business.dto.DailyTrainTicketSaveDto;
import com.siyi.train.business.vo.DailyTrainTicketQueryVo;
import com.siyi.train.business.service.DailyTrainTicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-ticket")
public class DailyTrainTicketAdminController {

    private final DailyTrainTicketService dailyTrainTicketService;

    public DailyTrainTicketAdminController(DailyTrainTicketService dailyTrainTicketService) {
        this.dailyTrainTicketService = dailyTrainTicketService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody DailyTrainTicketSaveDto dto) {
        this.dailyTrainTicketService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainTicketQueryVo>> queryList(@Valid DailyTrainTicketQueryDto dto) {
        PageVo<DailyTrainTicketQueryVo> list = this.dailyTrainTicketService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.dailyTrainTicketService.delete(id);
        return Result.success();
    }

}
