package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.ConfirmOrderQueryDto;
import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.vo.ConfirmOrderQueryVo;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-19 17:42:00
* @Description:
* @Version 1.0.0
*/
public interface ConfirmOrderService {

    void save(ConfirmOrderDoDto dto);

    PageVo<ConfirmOrderQueryVo> queryList(ConfirmOrderQueryDto dto);

    void delete(Long id);

    void doConfirm(ConfirmOrderDoDto dto);
}
