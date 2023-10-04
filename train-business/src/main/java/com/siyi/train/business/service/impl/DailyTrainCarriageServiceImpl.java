package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.SeatColEnum;
import com.siyi.train.business.dto.DailyTrainCarriageQueryDto;
import com.siyi.train.business.dto.DailyTrainCarriageSaveDto;
import com.siyi.train.business.mapper.DailyTrainCarriageMapper;
import com.siyi.train.business.pojo.DailyTrainCarriage;
import com.siyi.train.business.pojo.DailyTrainCarriageExample;
import com.siyi.train.business.pojo.TrainCarriage;
import com.siyi.train.business.service.DailyTrainCarriageService;
import com.siyi.train.business.service.TrainCarriageService;
import com.siyi.train.business.vo.DailyTrainCarriageQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DailyTrainCarriageServiceImpl implements DailyTrainCarriageService {

    private final DailyTrainCarriageMapper dailyTrainCarriageMapper;
    private final TrainCarriageService trainCarriageService;

    public DailyTrainCarriageServiceImpl(DailyTrainCarriageMapper dailyTrainCarriageMapper, TrainCarriageService trainCarriageService) {
        this.dailyTrainCarriageMapper = dailyTrainCarriageMapper;
        this.trainCarriageService = trainCarriageService;
    }

    @Override
    public void save(DailyTrainCarriageSaveDto dto) {
        DateTime now = DateTime.now();
        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(dto, DailyTrainCarriage.class);

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(dto.getSeatType());
        dailyTrainCarriage.setColCount(seatColEnums.size());
        dailyTrainCarriage.setSeatCount(dailyTrainCarriage.getColCount() * dailyTrainCarriage.getRowCount());

        if (ObjectUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            this.dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(now);
            this.dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);
        }
    }

    @Override
    public PageVo<DailyTrainCarriageQueryVo> queryList(DailyTrainCarriageQueryDto dto) {
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("date desc, train_code asc, `index` asc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        if (ObjectUtil.isNotNull(dto.getDate())) {
            criteria.andDateEqualTo(dto.getDate());
        }
        if (ObjectUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList = this.dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);

        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryVo> list = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryVo.class);

        PageVo<DailyTrainCarriageQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void genDaily(Date date, String trainCode) {
        log.info("生成日期【{}】车次【{}】的车厢信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的车厢信息
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);
        this.dailyTrainCarriageMapper.deleteByExample(dailyTrainCarriageExample);

        // 查出某车次的所有的车厢信息
        List<TrainCarriage> carriageList = this.trainCarriageService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(carriageList)) {
            log.info("该车次没有车厢基础数据，生成该车次的车厢信息结束");
            return;
        }

        for (TrainCarriage trainCarriage : carriageList) {
            DateTime now = DateTime.now();
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriage.setDate(date);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
        log.info("生成日期【{}】车次【{}】的车厢信息结束", DateUtil.formatDate(date), trainCode);
    }

    @Override
    public List<DailyTrainCarriage> selectByType(Date date, String trainCode, String seatType) {
        DailyTrainCarriageExample example = new DailyTrainCarriageExample();
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andSeatTypeEqualTo(seatType);
        return this.dailyTrainCarriageMapper.selectByExample(example);
    }
}
