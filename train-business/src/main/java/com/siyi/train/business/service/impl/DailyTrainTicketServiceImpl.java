package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.SeatTypeEnum;
import com.siyi.train.business.constant.TrainTypeEnum;
import com.siyi.train.business.dto.DailyTrainTicketQueryDto;
import com.siyi.train.business.dto.DailyTrainTicketSaveDto;
import com.siyi.train.business.mapper.DailyTrainTicketMapper;
import com.siyi.train.business.pojo.DailyTrain;
import com.siyi.train.business.pojo.DailyTrainTicket;
import com.siyi.train.business.pojo.DailyTrainTicketExample;
import com.siyi.train.business.pojo.TrainStation;
import com.siyi.train.business.service.DailyTrainSeatService;
import com.siyi.train.business.service.DailyTrainTicketService;
import com.siyi.train.business.service.TrainStationService;
import com.siyi.train.business.vo.DailyTrainTicketQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DailyTrainTicketServiceImpl implements DailyTrainTicketService {

    private final DailyTrainTicketMapper dailyTrainTicketMapper;
    private final TrainStationService trainStationService;
    private final DailyTrainSeatService dailyTrainSeatService;

    public DailyTrainTicketServiceImpl(DailyTrainTicketMapper dailyTrainTicketMapper,
                                       TrainStationService trainStationService,
                                       DailyTrainSeatService dailyTrainSeatService) {
        this.dailyTrainTicketMapper = dailyTrainTicketMapper;
        this.trainStationService = trainStationService;
        this.dailyTrainSeatService = dailyTrainSeatService;
    }

    @Override
    public void save(DailyTrainTicketSaveDto dto) {
        DateTime now = DateTime.now();
        DailyTrainTicket dailyTrainTicket = BeanUtil.copyProperties(dto, DailyTrainTicket.class);
        if (ObjectUtil.isNull(dailyTrainTicket.getId())) {
            dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainTicket.setCreateTime(now);
            dailyTrainTicket.setUpdateTime(now);
            this.dailyTrainTicketMapper.insert(dailyTrainTicket);
        } else {
            dailyTrainTicket.setUpdateTime(now);
            this.dailyTrainTicketMapper.updateByPrimaryKey(dailyTrainTicket);
        }
    }

    @Override
    public PageVo<DailyTrainTicketQueryVo> queryList(DailyTrainTicketQueryDto dto) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.setOrderByClause("id desc");
        DailyTrainTicketExample.Criteria criteria = dailyTrainTicketExample.createCriteria();
        if (ObjUtil.isNotNull(dto.getDate())) {
            criteria.andDateEqualTo(dto.getDate());
        }
        if (ObjUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }
        if (ObjUtil.isNotEmpty(dto.getStart())) {
            criteria.andStartEqualTo(dto.getStart());
        }
        if (ObjUtil.isNotEmpty(dto.getEnd())) {
            criteria.andEndEqualTo(dto.getEnd());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<DailyTrainTicket> dailyTrainTicketList = this.dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);

        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTicketList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainTicketQueryVo> list = BeanUtil.copyToList(dailyTrainTicketList, DailyTrainTicketQueryVo.class);

        PageVo<DailyTrainTicketQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.dailyTrainTicketMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void genDaily(DailyTrain dailyTrain, Date date, String trainCode) {
        log.info("生成日期【{}】车次【{}】的余票信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的余票信息
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);
        this.dailyTrainTicketMapper.deleteByExample(dailyTrainTicketExample);

        // 查出某车次的所有的车站信息
        List<TrainStation> stationList = this.trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(stationList)) {
            log.info("该车次没有车站基础数据，生成该车次的余票信息结束");
            return;
        }

        DateTime now = DateTime.now();
        for (int i = 0; i < stationList.size(); i++) {
            // 得到出发站
            TrainStation trainStationStart = stationList.get(i);
            BigDecimal sumKM = BigDecimal.ZERO;
            for (int j = (i + 1); j < stationList.size(); j++) {
                TrainStation trainStationEnd = stationList.get(j);
                sumKM = sumKM.add(trainStationEnd.getKm());

                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setStart(trainStationStart.getName());
                dailyTrainTicket.setStartPinyin(trainStationStart.getNamePinyin());
                dailyTrainTicket.setStartTime(trainStationStart.getOutTime());
                dailyTrainTicket.setStartIndex(trainStationStart.getIndex());
                dailyTrainTicket.setEnd(trainStationEnd.getName());
                dailyTrainTicket.setEndPinyin(trainStationEnd.getNamePinyin());
                dailyTrainTicket.setEndTime(trainStationEnd.getInTime());
                dailyTrainTicket.setEndIndex(trainStationEnd.getIndex());
                int ydz = this.dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YDZ.getCode());
                int edz = this.dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.EDZ.getCode());
                int rw = this.dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.RW.getCode());
                int yw = this.dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YW.getCode());
                // 票价 = 里程之和 * 座位单价 * 车次类型系数
                String trainType = dailyTrain.getType();
                // 计算票价系数：TrainTypeEnum.priceRate
                BigDecimal priceRate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, trainType);
                BigDecimal ydzPrice = sumKM.multiply(SeatTypeEnum.YDZ.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal edzPrice = sumKM.multiply(SeatTypeEnum.EDZ.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal rwPrice = sumKM.multiply(SeatTypeEnum.RW.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal ywPrice = sumKM.multiply(SeatTypeEnum.YW.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP);
                dailyTrainTicket.setYdz(ydz);
                dailyTrainTicket.setYdzPrice(ydzPrice);
                dailyTrainTicket.setEdz(edz);
                dailyTrainTicket.setEdzPrice(edzPrice);
                dailyTrainTicket.setRw(rw);
                dailyTrainTicket.setRwPrice(rwPrice);
                dailyTrainTicket.setYw(yw);
                dailyTrainTicket.setYwPrice(ywPrice);
                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                this.dailyTrainTicketMapper.insert(dailyTrainTicket);
            }
        }
        log.info("生成日期【{}】车次【{}】的余票信息结束", DateUtil.formatDate(date), trainCode);

    }
}
