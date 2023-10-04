package com.siyi.train.member.controller.admin;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.vo.Result;
import com.siyi.train.member.dto.TicketQueryDto;
import com.siyi.train.member.service.TicketService;
import com.siyi.train.member.vo.TicketQueryVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TicketAdminController
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/29 18:35
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    private final TicketService ticketService;

    public TicketAdminController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/query-list")
    public Result<PageVo<TicketQueryVo>> queryList(@Valid TicketQueryDto dto) {
        PageVo<TicketQueryVo> list = ticketService.queryList(dto);
        return Result.success(list);
    }

}
