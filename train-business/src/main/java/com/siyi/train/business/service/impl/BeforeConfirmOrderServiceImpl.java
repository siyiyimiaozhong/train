package com.siyi.train.business.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.siyi.train.business.constant.ConfirmOrderStatusEnum;
import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.dto.ConfirmOrderMQDto;
import com.siyi.train.business.dto.ConfirmOrderTicketDto;
import com.siyi.train.business.mapper.ConfirmOrderMapper;
import com.siyi.train.business.pojo.ConfirmOrder;
import com.siyi.train.business.service.BeforeConfirmOrderService;
import com.siyi.train.business.service.ConfirmOrderService;
import com.siyi.train.business.service.SkTokenService;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.context.LoginMemberContext;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.util.SnowUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: BeforeConfirmOrderServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/24 14:32
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Service
public class BeforeConfirmOrderServiceImpl implements BeforeConfirmOrderService {

    private final ConfirmOrderMapper confirmOrderMapper;
    private final SkTokenService skTokenService;
    private final ConfirmOrderService confirmOrderService;
    private final RocketMQTemplate rocketMQTemplate;

    public BeforeConfirmOrderServiceImpl(ConfirmOrderMapper confirmOrderMapper,
                                         SkTokenService skTokenService,
                                         ConfirmOrderService confirmOrderService,
                                         RocketMQTemplate rocketMQTemplate) {
        this.confirmOrderMapper = confirmOrderMapper;
        this.skTokenService = skTokenService;
        this.confirmOrderService = confirmOrderService;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @SentinelResource(value = "beforeDoConfirm", blockHandler = "beforeDoConfirmBlock")
    @Override
    public Long beforeDoConfirm(ConfirmOrderDoDto dto) {
        Long id = null;
        // 根据前端传值，加入排队人数
        for (int i = 0; i < dto.getLineNumber() + 1; i++) {
            dto.setMemberId(LoginMemberContext.getId());
            // 校验令牌余量
            boolean validSkToken = skTokenService.validSkToken(dto.getDate(), dto.getTrainCode(), LoginMemberContext.getId());
            if (validSkToken) {
                log.info("令牌校验通过");
            } else {
                log.info("令牌校验不通过");
                throw new BusinessException(ResultCode.CONFIRM_ORDER_SK_TOKEN_FAIL);
            }

            Date date = dto.getDate();
            String trainCode = dto.getTrainCode();
            String start = dto.getStart();
            String end = dto.getEnd();
            List<ConfirmOrderTicketDto> tickets = dto.getTickets();

            // 保存确认订单表，状态初始
            DateTime now = DateTime.now();
            ConfirmOrder confirmOrder = new ConfirmOrder();
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrder.setMemberId(dto.getMemberId());
            confirmOrder.setDate(date);
            confirmOrder.setTrainCode(trainCode);
            confirmOrder.setStart(start);
            confirmOrder.setEnd(end);
            confirmOrder.setDailyTrainTicketId(dto.getDailyTrainTicketId());
            confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
            confirmOrder.setTickets(JSON.toJSONString(tickets));
            confirmOrderMapper.insert(confirmOrder);

            // 发送MQ排队购票
            ConfirmOrderMQDto confirmOrderMQDto = new ConfirmOrderMQDto();
            confirmOrderMQDto.setDate(dto.getDate());
            confirmOrderMQDto.setTrainCode(dto.getTrainCode());
            confirmOrderMQDto.setLogId(MDC.get("log_ID"));
            String reqJson = JSON.toJSONString(confirmOrderMQDto);
            // log.info("排队购票，发送mq开始，消息：{}", reqJson);
            // this.rocketMQTemplate.convertAndSend(RocketMQTopicEnum.CONFIRM_ORDER.getCode(), reqJson);
            // log.info("排队购票，发送mq结束");
            this.confirmOrderService.doConfirm(confirmOrderMQDto);
            id = confirmOrder.getId();
        }
        return id;
    }

    /**
     * 降级方法，需包含限流方法的所有参数和BlockException参数
     * @param dto
     * @param e
     */
    public void beforeDoConfirmBlock(ConfirmOrderDoDto dto, BlockException e) {
        log.info("购票请求被限流：{}", dto);
        throw new BusinessException(ResultCode.CONFIRM_ORDER_FLOW_EXCEPTION);
    }
}
