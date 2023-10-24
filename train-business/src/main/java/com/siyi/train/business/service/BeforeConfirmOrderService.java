package com.siyi.train.business.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.siyi.train.business.dto.ConfirmOrderDoDto;

/**
 * @ClassName: BeforeConfirmOrderService
 * @Auther: Chengxin Zhang
 * @Date: 2023/10/24 14:32
 * @Description:
 * @Version 1.0.0
 */
public interface BeforeConfirmOrderService {
    @SentinelResource(value = "beforeDoConfirm", blockHandler = "beforeDoConfirmBlock")
    Long beforeDoConfirm(ConfirmOrderDoDto dto);
}
