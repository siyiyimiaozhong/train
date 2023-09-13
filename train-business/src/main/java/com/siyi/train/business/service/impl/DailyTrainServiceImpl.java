package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.mapper.DailyTrainMapper;
import com.siyi.train.business.pojo.DailyTrain;
import com.siyi.train.business.pojo.DailyTrainExample;
import com.siyi.train.business.service.DailyTrainService;
import com.siyi.train.business.vo.DailyTrainQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyTrainServiceImpl implements DailyTrainService {

    private final DailyTrainMapper dailyTrainMapper;

    public DailyTrainServiceImpl(DailyTrainMapper dailyTrainMapper) {
        this.dailyTrainMapper = dailyTrainMapper;
    }

    @Override
    public void save(DailyTrainSaveDto dto) {
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(dto, DailyTrain.class);
        if (ObjectUtil.isNull(dailyTrain.getId())) {
            dailyTrain.setId(SnowUtil.getSnowflakeNextId());
            dailyTrain.setCreateTime(now);
            dailyTrain.setUpdateTime(now);
            this.dailyTrainMapper.insert(dailyTrain);
        } else {
            dailyTrain.setUpdateTime(now);
            this.dailyTrainMapper.updateByPrimaryKey(dailyTrain);
        }
    }

    @Override
    public PageVo<DailyTrainQueryVo> queryList(DailyTrainQueryDto dto) {
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        dailyTrainExample.setOrderByClause("date desc, code asc");
        DailyTrainExample.Criteria criteria = dailyTrainExample.createCriteria();
        if (ObjectUtil.isNotNull(dto.getDate())) {
            criteria.andDateEqualTo(dto.getDate());
        }
        if (ObjectUtil.isNotEmpty(dto.getCode())) {
            criteria.andCodeEqualTo(dto.getCode());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<DailyTrain> dailyTrainList = this.dailyTrainMapper.selectByExample(dailyTrainExample);

        PageInfo<DailyTrain> pageInfo = new PageInfo<>(dailyTrainList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainQueryVo> list = BeanUtil.copyToList(dailyTrainList, DailyTrainQueryVo.class);

        PageVo<DailyTrainQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.dailyTrainMapper.deleteByPrimaryKey(id);
    }

}
