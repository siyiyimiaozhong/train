package com.siyi.train.business.service;

import com.siyi.train.business.pojo.TrainCarriage;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainCarriageQueryDto;
import com.siyi.train.business.dto.TrainCarriageSaveDto;
import com.siyi.train.business.vo.TrainCarriageQueryVo;

import java.util.List;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-11 16:48:42
* @Description:
* @Version 1.0.0
*/
public interface TrainCarriageService {

    void save(TrainCarriageSaveDto dto);

    PageVo<TrainCarriageQueryVo> queryList(TrainCarriageQueryDto dto);

    void delete(Long id);

    List<TrainCarriage> selectByTrainCode(String trainCode);
}
