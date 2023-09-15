package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.SeatColEnum;
import com.siyi.train.business.dto.TrainSeatQueryDto;
import com.siyi.train.business.dto.TrainSeatSaveDto;
import com.siyi.train.business.mapper.TrainSeatMapper;
import com.siyi.train.business.pojo.TrainCarriage;
import com.siyi.train.business.pojo.TrainSeat;
import com.siyi.train.business.pojo.TrainSeatExample;
import com.siyi.train.business.service.TrainCarriageService;
import com.siyi.train.business.service.TrainSeatService;
import com.siyi.train.business.vo.TrainSeatQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private final TrainSeatMapper trainSeatMapper;
    private final TrainCarriageService trainCarriageService;

    public TrainSeatServiceImpl(TrainSeatMapper trainSeatMapper, TrainCarriageService trainCarriageService) {
        this.trainSeatMapper = trainSeatMapper;
        this.trainCarriageService = trainCarriageService;
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
        trainSeatExample.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();

        if (ObjectUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }

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

    @Transactional
    @Override
    public void genTrainSeat(String trainCode) {
        DateTime now = DateTime.now();
        // 清空当前车次下的所有座位记录
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        this.trainSeatMapper.deleteByExample(trainSeatExample);

        // 查找当前车次下的所有的车厢
        List<TrainCarriage> carriageList = this.trainCarriageService.selectByTrainCode(trainCode);
        // 循环生成每个车厢的座位
        for (TrainCarriage trainCarriage : carriageList) {
            Integer rowCount = trainCarriage.getRowCount();
            String seatType = trainCarriage.getSeatType();
            int seatIndex = 1;
            // 根据车厢的座位类型，筛选出所有的列
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(seatType);
            for (int row = 1; row < rowCount; row++) {
                for (SeatColEnum seatColEnum : colEnumList) {
                    // 构造座位数据并保存
                    // 构造座位数据并保存数据库
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(SnowUtil.getSnowflakeNextId());
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarriageIndex(trainCarriage.getIndex());
                    trainSeat.setRow(StrUtil.fillBefore(String.valueOf(row), '0', 2));
                    trainSeat.setCol(seatColEnum.getCode());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setCarriageSeatIndex(seatIndex++);
                    trainSeat.setCreateTime(now);
                    trainSeat.setUpdateTime(now);
                    this.trainSeatMapper.insert(trainSeat);
                }
            }
        }
    }

    @Override
    public List<TrainSeat> selectByTrainCode(String trainCode) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.setOrderByClause("`id` asc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        return this.trainSeatMapper.selectByExample(trainSeatExample);
    }

}
