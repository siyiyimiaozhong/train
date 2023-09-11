package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.TrainQueryDto;
import com.siyi.train.business.dto.TrainSaveDto;
import com.siyi.train.business.mapper.TrainMapper;
import com.siyi.train.business.pojo.Train;
import com.siyi.train.business.pojo.TrainExample;
import com.siyi.train.business.service.TrainService;
import com.siyi.train.business.vo.TrainQueryVo;
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
public class TrainServiceImpl implements TrainService {

    private final TrainMapper trainMapper;

    public TrainServiceImpl(TrainMapper trainMapper) {
        this.trainMapper = trainMapper;
    }

    @Override
    public void save(TrainSaveDto dto) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(dto, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            this.trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            this.trainMapper.updateByPrimaryKey(train);
        }
    }

    @Override
    public PageVo<TrainQueryVo> queryList(TrainQueryDto dto) {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<Train> trainList = this.trainMapper.selectByExample(trainExample);

        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryVo> list = BeanUtil.copyToList(trainList, TrainQueryVo.class);

        PageVo<TrainQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.trainMapper.deleteByPrimaryKey(id);
    }

}
