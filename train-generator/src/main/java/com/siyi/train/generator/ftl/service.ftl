package com.siyi.train.${module}.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.${module}.dto.${Domain}QueryDto;
import com.siyi.train.${module}.dto.${Domain}SaveDto;
import com.siyi.train.${module}.vo.${Domain}QueryVo;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: ${generatorTime}
* @Description:
* @Version 1.0.0
*/
public interface ${Domain}Service {

    void save(${Domain}SaveDto dto);

    PageVo<${Domain}QueryVo> queryList(${Domain}QueryDto dto);

    void delete(Long id);

}
