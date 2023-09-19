package com.siyi.train.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.common.context.LoginMemberContext;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.member.dto.PassengerQueryDto;
import com.siyi.train.member.dto.PassengerSaveDto;
import com.siyi.train.member.mapper.PassengerMapper;
import com.siyi.train.member.pojo.Passenger;
import com.siyi.train.member.pojo.PassengerExample;
import com.siyi.train.member.service.PassengerService;
import com.siyi.train.member.vo.PassengerQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: PassengerServiceImpl
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/29 14:35
 * @Description:
 * @Version 1.0.0
 */
@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerMapper passengerMapper) {
        this.passengerMapper = passengerMapper;
    }

    @Override
    public void save(PassengerSaveDto dto) {
        DateTime now = DateTime.now();

        Passenger passenger = new Passenger();
        passenger.setName(dto.getName());
        passenger.setType(dto.getType());
        passenger.setIdCard(dto.getIdCard());
        passenger.setMemberId(LoginMemberContext.getId());

        if (ObjectUtil.isNull(dto.getId())) {
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passenger.setId(SnowUtil.getSnowflakeNextId());
            this.passengerMapper.insert(passenger);
        } else {
            passenger.setId(dto.getId());
            passenger.setUpdateTime(now);
            this.passengerMapper.updateByPrimaryKey(passenger);
        }

    }

    @Override
    public PageVo<PassengerQueryVo> queryList(PassengerQueryDto dto) {
        PassengerExample example = new PassengerExample();
        example.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = example.createCriteria();
        if (ObjectUtil.isNotNull(dto.getMemberId())) {
            criteria.andMemberIdEqualTo(dto.getMemberId());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(example);

        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryVo> list = BeanUtil.copyToList(passengerList, PassengerQueryVo.class);

        PageVo<PassengerQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo;
    }

    @Override
    public void delete(Long id) {
        this.passengerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PassengerQueryVo> queryMine() {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("name asc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        criteria.andMemberIdEqualTo(LoginMemberContext.getId());
        List<Passenger> list = passengerMapper.selectByExample(passengerExample);
        return BeanUtil.copyToList(list, PassengerQueryVo.class);
    }
}
