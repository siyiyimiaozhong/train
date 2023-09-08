package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.StationQueryDto;
import com.siyi.train.business.dto.StationSaveDto;
import com.siyi.train.business.vo.StationQueryVo;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-09-08 16:38:49
* @Description:
* @Version 1.0.0
*/
public interface StationService {

    void save(StationSaveDto dto);

    PageVo<StationQueryVo> queryList(StationQueryDto dto);

    void delete(Long id);

}
