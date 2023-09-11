package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainStationQueryDto;
import com.siyi.train.business.dto.TrainStationSaveDto;
import com.siyi.train.business.vo.TrainStationQueryVo;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-11 14:47:24
* @Description:
* @Version 1.0.0
*/
public interface TrainStationService {

    void save(TrainStationSaveDto dto);

    PageVo<TrainStationQueryVo> queryList(TrainStationQueryDto dto);

    void delete(Long id);

}
