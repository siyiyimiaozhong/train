package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.DailyTrainStationQueryDto;
import com.siyi.train.business.dto.DailyTrainStationSaveDto;
import com.siyi.train.business.mapper.DailyTrainStationMapper;
import com.siyi.train.business.pojo.DailyTrainStation;
import com.siyi.train.business.pojo.DailyTrainStationExample;
import com.siyi.train.business.service.DailyTrainStationService;
import com.siyi.train.business.vo.DailyTrainStationQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyTrainStationServiceImpl implements DailyTrainStationService {

    private final DailyTrainStationMapper dailyTrainStationMapper;

    public DailyTrainStationServiceImpl(DailyTrainStationMapper dailyTrainStationMapper) {
        this.dailyTrainStationMapper = dailyTrainStationMapper;
    }

    @Override
    public void save(DailyTrainStationSaveDto dto) {
        DateTime now = DateTime.now();
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(dto, DailyTrainStation.class);
        if (ObjectUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            this.dailyTrainStationMapper.insert(dailyTrainStation);
        } else {
            dailyTrainStation.setUpdateTime(now);
            this.dailyTrainStationMapper.updateByPrimaryKey(dailyTrainStation);
        }
    }

    @Override
    public PageVo<DailyTrainStationQueryVo> queryList(DailyTrainStationQueryDto dto) {
        DailyTrainStationExample dailyTrainStationExample = new DailyTrainStationExample();
        dailyTrainStationExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainStationExample.Criteria criteria = dailyTrainStationExample.createCriteria();
        if (ObjectUtil.isNotNull(dto.getDate())) {
            criteria.andDateEqualTo(dto.getDate());
        }
        if (ObjectUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<DailyTrainStation> dailyTrainStationList = this.dailyTrainStationMapper.selectByExample(dailyTrainStationExample);

        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainStationQueryVo> list = BeanUtil.copyToList(dailyTrainStationList, DailyTrainStationQueryVo.class);

        PageVo<DailyTrainStationQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.dailyTrainStationMapper.deleteByPrimaryKey(id);
    }

}
