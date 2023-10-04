package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.DailyTrainSeatQueryDto;
import com.siyi.train.business.dto.DailyTrainSeatSaveDto;
import com.siyi.train.business.mapper.DailyTrainSeatMapper;
import com.siyi.train.business.pojo.DailyTrainSeat;
import com.siyi.train.business.pojo.DailyTrainSeatExample;
import com.siyi.train.business.pojo.TrainSeat;
import com.siyi.train.business.pojo.TrainStation;
import com.siyi.train.business.service.DailyTrainSeatService;
import com.siyi.train.business.service.TrainSeatService;
import com.siyi.train.business.service.TrainStationService;
import com.siyi.train.business.vo.DailyTrainSeatQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DailyTrainSeatServiceImpl implements DailyTrainSeatService {

    private final DailyTrainSeatMapper dailyTrainSeatMapper;
    private final TrainSeatService trainSeatService;
    private final TrainStationService trainStationService;

    public DailyTrainSeatServiceImpl(DailyTrainSeatMapper dailyTrainSeatMapper,
                                     TrainSeatService trainSeatService,
                                     TrainStationService trainStationService) {
        this.dailyTrainSeatMapper = dailyTrainSeatMapper;
        this.trainSeatService = trainSeatService;
        this.trainStationService = trainStationService;
    }

    @Override
    public void save(DailyTrainSeatSaveDto dto) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(dto, DailyTrainSeat.class);
        if (ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            this.dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            this.dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }
    }

    @Override
    public PageVo<DailyTrainSeatQueryVo> queryList(DailyTrainSeatQueryDto dto) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("date desc, train_code asc, carriage_index asc, carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();
        if (ObjectUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = this.dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);

        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryVo> list = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryVo.class);

        PageVo<DailyTrainSeatQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void genDaily(Date date, String trainCode) {
        log.info("生成日期【{}】车次【{}】的座位信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的座位信息
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);
        dailyTrainSeatMapper.deleteByExample(dailyTrainSeatExample);

        List<TrainStation> stationList = this.trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("", '0', stationList.size() - 1);

        // 查出某车次的所有的座位信息
        List<TrainSeat> seatList = this.trainSeatService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(seatList)) {
            log.info("该车次没有座位基础数据，生成该车次的座位信息结束");
            return;
        }

        for (TrainSeat trainSeat : seatList) {
            DateTime now = DateTime.now();
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
        log.info("生成日期【{}】车次【{}】的座位信息结束", DateUtil.formatDate(date), trainCode);
    }

    @Override
    public int countSeat(Date date, String trainCode, String seatType) {
        DailyTrainSeatExample example = new DailyTrainSeatExample();
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andSeatTypeEqualTo(seatType);
        long l = this.dailyTrainSeatMapper.countByExample(example);
        if (l == 0L) {
            return -1;
        }
        return (int) l;
    }

    @Override
    public List<DailyTrainSeat> selectByCarriage(Date date, String trainCode, Integer carriageIndex) {
        DailyTrainSeatExample example = new DailyTrainSeatExample();
        example.setOrderByClause("carriage_seat_index asc");
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andCarriageIndexEqualTo(carriageIndex);
        return this.dailyTrainSeatMapper.selectByExample(example);
    }
}
