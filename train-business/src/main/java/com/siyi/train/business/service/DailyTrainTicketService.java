package com.siyi.train.business.service;

import com.siyi.train.business.dto.DailyTrainTicketQueryDto;
import com.siyi.train.business.dto.DailyTrainTicketSaveDto;
import com.siyi.train.business.pojo.DailyTrain;
import com.siyi.train.business.vo.DailyTrainTicketQueryVo;
import com.siyi.train.common.vo.PageVo;

import java.util.Date;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-15 17:31:31
* @Description:
* @Version 1.0.0
*/
public interface DailyTrainTicketService {

    void save(DailyTrainTicketSaveDto dto);

    PageVo<DailyTrainTicketQueryVo> queryList(DailyTrainTicketQueryDto dto);

    void delete(Long id);

    void genDaily(DailyTrain dailyTrain, Date date, String trainCode);
}
