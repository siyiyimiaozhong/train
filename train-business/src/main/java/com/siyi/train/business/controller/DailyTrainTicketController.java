package com.siyi.train.business.controller;

import com.siyi.train.business.dto.DailyTrainTicketQueryDto;
import com.siyi.train.business.service.DailyTrainTicketService;
import com.siyi.train.business.vo.DailyTrainTicketQueryVo;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {

    private final DailyTrainTicketService dailyTrainTicketService;

    public DailyTrainTicketController(DailyTrainTicketService dailyTrainTicketService) {
        this.dailyTrainTicketService = dailyTrainTicketService;
    }

    @GetMapping("/query-list")
    public Result<PageVo<DailyTrainTicketQueryVo>> queryList(@Valid DailyTrainTicketQueryDto dto) {
        PageVo<DailyTrainTicketQueryVo> list = this.dailyTrainTicketService.queryList(dto);
        return Result.success(list);
    }

}
