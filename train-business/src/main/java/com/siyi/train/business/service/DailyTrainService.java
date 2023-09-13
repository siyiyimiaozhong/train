package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.vo.DailyTrainQueryVo;

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

}
