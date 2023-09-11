package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.TrainStationQueryDto;
import com.siyi.train.business.dto.TrainStationSaveDto;
import com.siyi.train.business.mapper.TrainStationMapper;
import com.siyi.train.business.pojo.TrainStation;
import com.siyi.train.business.pojo.TrainStationExample;
import com.siyi.train.business.service.TrainStationService;
import com.siyi.train.business.vo.TrainStationQueryVo;
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
public class TrainStationServiceImpl implements TrainStationService {

    private final TrainStationMapper trainStationMapper;

    public TrainStationServiceImpl(TrainStationMapper trainStationMapper) {
        this.trainStationMapper = trainStationMapper;
    }

    @Override
    public void save(TrainStationSaveDto dto) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(dto, TrainStation.class);
        if (ObjectUtil.isNull(trainStation.getId())) {
            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            this.trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            this.trainStationMapper.updateByPrimaryKey(trainStation);
        }
    }

    @Override
    public PageVo<TrainStationQueryVo> queryList(TrainStationQueryDto dto) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.setOrderByClause("id desc");
        TrainStationExample.Criteria criteria = trainStationExample.createCriteria();

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<TrainStation> trainStationList = this.trainStationMapper.selectByExample(trainStationExample);

        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryVo> list = BeanUtil.copyToList(trainStationList, TrainStationQueryVo.class);

        PageVo<TrainStationQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.trainStationMapper.deleteByPrimaryKey(id);
    }

}
