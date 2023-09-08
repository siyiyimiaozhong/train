package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.StationQueryDto;
import com.siyi.train.business.dto.StationSaveDto;
import com.siyi.train.business.mapper.StationMapper;
import com.siyi.train.business.pojo.Station;
import com.siyi.train.business.pojo.StationExample;
import com.siyi.train.business.service.StationService;
import com.siyi.train.business.vo.StationQueryVo;
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
public class StationServiceImpl implements StationService {

    private final StationMapper stationMapper;

    public StationServiceImpl(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Override
    public void save(StationSaveDto dto) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(dto, Station.class);
        if (ObjectUtil.isNull(station.getId())) {
            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            this.stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            this.stationMapper.updateByPrimaryKey(station);
        }
    }

    @Override
    public PageVo<StationQueryVo> queryList(StationQueryDto dto) {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria = stationExample.createCriteria();

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<Station> stationList = this.stationMapper.selectByExample(stationExample);

        PageInfo<Station> pageInfo = new PageInfo<>(stationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<StationQueryVo> list = BeanUtil.copyToList(stationList, StationQueryVo.class);

        PageVo<StationQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.stationMapper.deleteByPrimaryKey(id);
    }

}
