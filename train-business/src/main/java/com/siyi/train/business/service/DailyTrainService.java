package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.vo.DailyTrainQueryVo;

import java.util.Date;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-13 16:10:58
* @Description:
* @Version 1.0.0
*/
public interface DailyTrainService {

    void save(DailyTrainSaveDto dto);

    PageVo<DailyTrainQueryVo> queryList(DailyTrainQueryDto dto);

    void delete(Long id);

    /**
     * 生成某日所有车次信息，包括车次、车站、车厢、座位
     * @param date
     */
    void genDaily(Date date);
}
