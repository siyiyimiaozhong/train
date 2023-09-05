package com.siyi.train.member.service;

import com.siyi.train.common.vo.PageVo;
import com.siyi.train.member.dto.PassengerQueryDto;
import com.siyi.train.member.dto.PassengerSaveDto;
import com.siyi.train.member.vo.PassengerQueryVo;

/**
 * @ClassName: PassengerService
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 14:34
 * @Description:
 * @Version 1.0.0
 */
public interface PassengerService {
    void save(PassengerSaveDto dto);

    PageVo<PassengerQueryVo> queryList(PassengerQueryDto dto);

    void delete(Long id);
}
