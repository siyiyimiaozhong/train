package com.siyi.train.member.controller.feign;

import com.siyi.train.common.dto.MemberTicketDto;
import com.siyi.train.common.vo.Result;
import com.siyi.train.member.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: FeignTicketController
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/29 18:37
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/feign/ticket")
public class FeignTicketController {

    private final TicketService ticketService;

    public FeignTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody MemberTicketDto dto) throws Exception {
        this.ticketService.save(dto);
        return Result.success();
    }

}
