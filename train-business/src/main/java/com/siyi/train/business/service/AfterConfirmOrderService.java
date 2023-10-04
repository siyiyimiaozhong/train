package com.siyi.train.business.service;

import com.siyi.train.business.dto.ConfirmOrderTicketDto;
import com.siyi.train.business.pojo.ConfirmOrder;
import com.siyi.train.business.pojo.DailyTrainSeat;
import com.siyi.train.business.pojo.DailyTrainTicket;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: AfterConfirmOrderService
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/27 17:45
 * @Description:
 * @Version 1.0.0
 */
public interface AfterConfirmOrderService {
    @Transactional
    void afterDoConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList, List<ConfirmOrderTicketDto> tickets, ConfirmOrder confirmOrder);
}
