package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.TrainQueryDto;
import com.siyi.train.business.dto.TrainSaveDto;
import com.siyi.train.business.vo.TrainQueryVo;

import java.util.List;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-08 17:48:23
* @Description:
* @Version 1.0.0
*/
public interface TrainService {

    void save(TrainSaveDto dto);

    PageVo<TrainQueryVo> queryList(TrainQueryDto dto);

    void delete(Long id);

    List<TrainQueryVo> queryAll();
}
