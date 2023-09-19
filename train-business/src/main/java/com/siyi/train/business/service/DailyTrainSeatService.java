package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainSeatQueryDto;
import com.siyi.train.business.dto.DailyTrainSeatSaveDto;
import com.siyi.train.business.vo.DailyTrainSeatQueryVo;

import java.util.Date;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-13 17:50:23
* @Description:
* @Version 1.0.0
*/
public interface DailyTrainSeatService {

    void save(DailyTrainSeatSaveDto dto);

    PageVo<DailyTrainSeatQueryVo> queryList(DailyTrainSeatQueryDto dto);

    void delete(Long id);

    void genDaily(Date date, String code);

    int countSeat(Date date, String trainCode, String code);
}
