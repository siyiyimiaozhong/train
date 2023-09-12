package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainSeatQueryDto;
import com.siyi.train.business.dto.TrainSeatSaveDto;
import com.siyi.train.business.vo.TrainSeatQueryVo;
import org.springframework.transaction.annotation.Transactional;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-11 16:59:39
* @Description:
* @Version 1.0.0
*/
public interface TrainSeatService {

    void save(TrainSeatSaveDto dto);

    PageVo<TrainSeatQueryVo> queryList(TrainSeatQueryDto dto);

    void delete(Long id);

    @Transactional
    void genTrainSeat(String trainCode);
}
