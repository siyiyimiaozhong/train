package com.siyi.train.business.listener;

import com.alibaba.fastjson.JSON;
import com.siyi.train.business.dto.ConfirmOrderMQDto;
import com.siyi.train.business.service.ConfirmOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ConfirmOrderConsumer
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/24 15:17
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "default", topic = "CONFIRM_ORDER")
public class ConfirmOrderConsumer implements RocketMQListener<MessageExt> {

    private final ConfirmOrderService confirmOrderService;

    public ConfirmOrderConsumer(ConfirmOrderService confirmOrderService) {
        this.confirmOrderService = confirmOrderService;
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        ConfirmOrderMQDto dto = JSON.parseObject(new String(body), ConfirmOrderMQDto.class);
        MDC.put("LOG_ID", dto.getLogId());
        log.info("ROCKETMQ收到消息：{}", new String(body));
        confirmOrderService.doConfirm(dto);
    }
}
