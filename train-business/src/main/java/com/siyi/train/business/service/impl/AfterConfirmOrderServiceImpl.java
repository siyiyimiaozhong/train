package com.siyi.train.business.service.impl;

import com.siyi.train.business.constant.ConfirmOrderStatusEnum;
import com.siyi.train.business.dto.ConfirmOrderTicketDto;
import com.siyi.train.business.feign.MemberFeign;
import com.siyi.train.business.mapper.ConfirmOrderMapper;
import com.siyi.train.business.mapper.DailyTrainSeatMapper;
import com.siyi.train.business.mapper.cust.DailyTrainTicketMapperCust;
import com.siyi.train.business.pojo.ConfirmOrder;
import com.siyi.train.business.pojo.DailyTrainSeat;
import com.siyi.train.business.pojo.DailyTrainTicket;
import com.siyi.train.business.service.AfterConfirmOrderService;
import com.siyi.train.common.dto.MemberTicketDto;
import com.siyi.train.common.vo.Result;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: AfterConfirmOrderServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/9/27 17:45
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Service
public class AfterConfirmOrderServiceImpl implements AfterConfirmOrderService {

    private final DailyTrainSeatMapper dailyTrainSeatMapper;
    private final DailyTrainTicketMapperCust dailyTrainTicketMapperCust;
    private final MemberFeign memberFeign;
    private final ConfirmOrderMapper confirmOrderMapper;

    public AfterConfirmOrderServiceImpl(DailyTrainSeatMapper dailyTrainSeatMapper,
                                        DailyTrainTicketMapperCust dailyTrainTicketMapperCust,
                                        MemberFeign memberFeign,
                                        ConfirmOrderMapper confirmOrderMapper) {
        this.dailyTrainSeatMapper = dailyTrainSeatMapper;
        this.dailyTrainTicketMapperCust = dailyTrainTicketMapperCust;
        this.memberFeign = memberFeign;
        this.confirmOrderMapper = confirmOrderMapper;
    }

    /**
     * 选中座位后事务处理：
     *  座位表修改售卖情况sell；
     *  余票详情表修改余票；
     *  为会员增加购票记录
     *  更新确认订单为成功
     */
//    @Transactional
    @GlobalTransactional
    @Override
    public void afterDoConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList, List<ConfirmOrderTicketDto> tickets, ConfirmOrder confirmOrder) {
        log.info("seata全局事务ID:{}", RootContext.getXID());
        for (int j = 0; j < finalSeatList.size(); j++) {
            DailyTrainSeat dailyTrainSeat = finalSeatList.get(j);
            DailyTrainSeat seatForUpdate = new DailyTrainSeat();
            seatForUpdate.setId(dailyTrainSeat.getId());
            seatForUpdate.setSell(dailyTrainSeat.getSell());
            this.dailyTrainSeatMapper.updateByPrimaryKeySelective(seatForUpdate);

            // 计算这个站卖出去后，影响了哪些站的余票库存
            // 影响的库存：本次选座之前没卖过票的，和本次购买的区间有交集的区间
            // 假设10个站，本次买4~7站
            // 原售：001000001
            // 购买：000011100
            // 新售：001011101
            // 影响：XXX11111X
            // Integer startIndex = 4;
            // Integer endIndex = 7;
            // Integer minStartIndex = startIndex - 往前碰到的最后一个0;
            // Integer maxStartIndex = endIndex - 1;
            // Integer minEndIndex = startIndex + 1;
            // Integer maxEndIndex = endIndex + 往后碰到的最后一个0;
            Integer startIndex = dailyTrainTicket.getStartIndex(); // 3
            Integer endIndex = dailyTrainTicket.getEndIndex(); // 5
            char[] chars = seatForUpdate.getSell().toCharArray(); // 01 11 10
            Integer maxStartIndex = endIndex - 1;
            Integer minEndIndex = startIndex + 1;
            Integer minStartIndex = 1;
            for (int i = startIndex - 1; i > 0; i--) {
                char aChar = chars[i - 1];
                if (aChar == '1') {
                    minStartIndex = i + 1;
                    break;
                }
            }
            log.info("影响出发站区间：" + minStartIndex + "-" + maxStartIndex);

            Integer maxEndIndex = seatForUpdate.getSell().length() + 1; // 7
            for (int i = endIndex - 1; i < seatForUpdate.getSell().length(); i++) {
                char aChar = chars[i];
                if (aChar == '1') {
                    maxEndIndex = i + 1;
                    break;
                }
            }
            log.info("影响到达站区间：" + minEndIndex + "-" + maxEndIndex);

            this.dailyTrainTicketMapperCust.updateCountBySell(
                    dailyTrainSeat.getDate(),
                    dailyTrainSeat.getTrainCode(),
                    dailyTrainSeat.getSeatType(),
                    minStartIndex,
                    maxStartIndex,
                    minEndIndex,
                    maxEndIndex);

            // 调用会员服务接口，为会员增加一张车票
            MemberTicketDto memberTicketDto = new MemberTicketDto();
            memberTicketDto.setMemberId(confirmOrder.getMemberId());
            memberTicketDto.setPassengerId(tickets.get(j).getPassengerId());
            memberTicketDto.setPassengerName(tickets.get(j).getPassengerName());
            memberTicketDto.setTrainDate(dailyTrainTicket.getDate());
            memberTicketDto.setTrainCode(dailyTrainTicket.getTrainCode());
            memberTicketDto.setCarriageIndex(dailyTrainSeat.getCarriageIndex());
            memberTicketDto.setSeatRow(dailyTrainSeat.getRow());
            memberTicketDto.setSeatCol(dailyTrainSeat.getCol());
            memberTicketDto.setStartStation(dailyTrainTicket.getStart());
            memberTicketDto.setStartTime(dailyTrainTicket.getStartTime());
            memberTicketDto.setEndStation(dailyTrainTicket.getEnd());
            memberTicketDto.setEndTime(dailyTrainTicket.getEndTime());
            memberTicketDto.setSeatType(dailyTrainSeat.getSeatType());
            Result<Object> result = this.memberFeign.save(memberTicketDto);
            log.info("调用member接口，返回：{}", result);

            // 更新订单状态为成功
            ConfirmOrder confirmOrderForUpdate = new ConfirmOrder();
            confirmOrderForUpdate.setId(confirmOrder.getId());
            confirmOrderForUpdate.setUpdateTime(new Date());
            confirmOrderForUpdate.setStatus(ConfirmOrderStatusEnum.SUCCESS.getCode());
            this.confirmOrderMapper.updateByPrimaryKeySelective(confirmOrderForUpdate);
        }
    }
}
