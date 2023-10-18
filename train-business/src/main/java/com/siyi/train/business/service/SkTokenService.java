package com.siyi.train.business.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.SkTokenQueryDto;
import com.siyi.train.business.dto.SkTokenSaveDto;
import com.siyi.train.business.vo.SkTokenQueryVo;

import java.util.Date;

/**
* @ClassName: PassengerService
* @Auther: Chengxin Zhang
* @Date: 2023-10-16 18:10:15
* @Description:
* @Version 1.0.0
*/
public interface SkTokenService {

    void save(SkTokenSaveDto dto);

    PageVo<SkTokenQueryVo> queryList(SkTokenQueryDto dto);

    void delete(Long id);

    /**
     * 校验令牌
     * @param date
     * @param trainCode
     * @param memberId
     * @return
     */
    boolean validSkToken(Date date, String trainCode, Long memberId);
}
