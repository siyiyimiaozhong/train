package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainStationQueryDto;
import com.siyi.train.business.dto.DailyTrainStationSaveDto;
import com.siyi.train.business.vo.DailyTrainStationQueryVo;

import java.util.Date;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-13 17:04:44
* @Description:
* @Version 1.0.0
*/
public interface DailyTrainStationService {

    void save(DailyTrainStationSaveDto dto);

    PageVo<DailyTrainStationQueryVo> queryList(DailyTrainStationQueryDto dto);

    void delete(Long id);

    void genDaily(Date date, String code);
}
