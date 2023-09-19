package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.DailyTrainQueryDto;
import com.siyi.train.business.dto.DailyTrainSaveDto;
import com.siyi.train.business.mapper.DailyTrainMapper;
import com.siyi.train.business.pojo.DailyTrain;
import com.siyi.train.business.pojo.DailyTrainExample;
import com.siyi.train.business.pojo.Train;
import com.siyi.train.business.service.*;
import com.siyi.train.business.vo.DailyTrainQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DailyTrainServiceImpl implements DailyTrainService {

    private final DailyTrainMapper dailyTrainMapper;
    private final TrainService trainService;
    private final DailyTrainStationService dailyTrainStationService;
    private final DailyTrainCarriageService dailyTrainCarriageService;
    private final DailyTrainSeatService dailyTrainSeatService;
    private final DailyTrainTicketService dailyTrainTicketService;

    public DailyTrainServiceImpl(DailyTrainMapper dailyTrainMapper,
                                 TrainService trainService,
                                 DailyTrainStationService dailyTrainStationService,
                                 DailyTrainCarriageService dailyTrainCarriageService,
                                 DailyTrainSeatService dailyTrainSeatService,
                                 DailyTrainTicketService dailyTrainTicketService) {
        this.dailyTrainMapper = dailyTrainMapper;
        this.trainService = trainService;
        this.dailyTrainStationService = dailyTrainStationService;
        this.dailyTrainCarriageService = dailyTrainCarriageService;
        this.dailyTrainSeatService = dailyTrainSeatService;
        this.dailyTrainTicketService = dailyTrainTicketService;
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

    @Transactional
    @Override
    public void genDaily(Date date) {
        List<Train> trainList = trainService.selectAll();
        if (CollUtil.isEmpty(trainList)) {
            log.info("没有车次基础数据，任务结束");
            return;
        }

        for (Train train : trainList) {
            genDailyTrain(date, train);
        }
    }

    public void genDailyTrain(Date date, Train train) {
        log.info("生成日期【{}】车次【{}】的信息开始", DateUtil.formatDate(date), train.getCode());
        // 删除该车次已有的数据
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        dailyTrainExample.createCriteria()
                .andDateEqualTo(date)
                .andCodeEqualTo(train.getCode());
        dailyTrainMapper.deleteByExample(dailyTrainExample);

        // 生成该车次的数据
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(train, DailyTrain.class);
        dailyTrain.setId(SnowUtil.getSnowflakeNextId());
        dailyTrain.setCreateTime(now);
        dailyTrain.setUpdateTime(now);
        dailyTrain.setDate(date);
        dailyTrainMapper.insert(dailyTrain);

        // 生成该车次的车站数据
        dailyTrainStationService.genDaily(date, train.getCode());

        // 生成该车次的车厢数据
        dailyTrainCarriageService.genDaily(date, train.getCode());

        // 生成该车次的座位数据
        dailyTrainSeatService.genDaily(date, train.getCode());

        // 生成车次的余票数据
        this.dailyTrainTicketService.genDaily(dailyTrain, date, train.getCode());

        log.info("生成日期【{}】车次【{}】的信息结束", DateUtil.formatDate(date), train.getCode());
    }

}
