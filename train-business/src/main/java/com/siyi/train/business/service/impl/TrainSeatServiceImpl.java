package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.TrainSeatQueryDto;
import com.siyi.train.business.dto.TrainSeatSaveDto;
import com.siyi.train.business.mapper.TrainSeatMapper;
import com.siyi.train.business.pojo.TrainSeat;
import com.siyi.train.business.pojo.TrainSeatExample;
import com.siyi.train.business.service.TrainSeatService;
import com.siyi.train.business.vo.TrainSeatQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.util.SnowUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private final TrainSeatMapper trainSeatMapper;

    public TrainSeatServiceImpl(TrainSeatMapper trainSeatMapper) {
        this.trainSeatMapper = trainSeatMapper;
    }

    @Override
    public void save(TrainSeatSaveDto dto) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(dto, TrainSeat.class);
        if (ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            this.trainSeatMapper.insert(trainSeat);
        } else {
            trainSeat.setUpdateTime(now);
            this.trainSeatMapper.updateByPrimaryKey(trainSeat);
        }
    }

    @Override
    public PageVo<TrainSeatQueryVo> queryList(TrainSeatQueryDto dto) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.setOrderByClause("id desc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<TrainSeat> trainSeatList = this.trainSeatMapper.selectByExample(trainSeatExample);

        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeatList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainSeatQueryVo> list = BeanUtil.copyToList(trainSeatList, TrainSeatQueryVo.class);

        PageVo<TrainSeatQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.trainSeatMapper.deleteByPrimaryKey(id);
    }

}
