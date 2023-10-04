package com.siyi.train.member.service;

import com.siyi.train.common.dto.MemberTicketDto;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.member.dto.TicketQueryDto;
import com.siyi.train.member.vo.TicketQueryVo;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-29 18:29:54
* @Description:
* @Version 1.0.0
*/
public interface TicketService {

    void save(MemberTicketDto dto);

    PageVo<TicketQueryVo> queryList(TicketQueryDto dto);

    void delete(Long id);

}
