package com.siyi.train.member.controller;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import com.siyi.train.member.dto.TicketQueryDto;
import com.siyi.train.member.service.TicketService;
import com.siyi.train.member.vo.TicketQueryVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/query-list")
    public Result<PageVo<TicketQueryVo>> queryList(@Valid TicketQueryDto dto) {
        PageVo<TicketQueryVo> list = this.ticketService.queryList(dto);
        return Result.success(list);
    }

}
