package com.siyi.train.business.service;

import com.siyi.train.business.pojo.DailyTrainCarriage;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainCarriageQueryDto;
import com.siyi.train.business.dto.DailyTrainCarriageSaveDto;
import com.siyi.train.business.vo.DailyTrainCarriageQueryVo;

import java.util.Date;
import java.util.List;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-13 17:18:37
* @Description:
* @Version 1.0.0
*/
public interface DailyTrainCarriageService {

    void save(DailyTrainCarriageSaveDto dto);

    PageVo<DailyTrainCarriageQueryVo> queryList(DailyTrainCarriageQueryDto dto);

    void delete(Long id);

    void genDaily(Date date, String code);

    List<DailyTrainCarriage> selectByType(Date date, String trainCode, String seatType);
}
