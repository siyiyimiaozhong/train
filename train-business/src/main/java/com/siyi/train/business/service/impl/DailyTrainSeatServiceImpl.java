package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.dto.DailyTrainSeatQueryDto;
import com.siyi.train.business.dto.DailyTrainSeatSaveDto;
import com.siyi.train.business.mapper.DailyTrainSeatMapper;
import com.siyi.train.business.pojo.DailyTrainSeat;
import com.siyi.train.business.pojo.DailyTrainSeatExample;
import com.siyi.train.business.service.DailyTrainSeatService;
import com.siyi.train.business.vo.DailyTrainSeatQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyTrainSeatServiceImpl implements DailyTrainSeatService {

    private final DailyTrainSeatMapper dailyTrainSeatMapper;

    public DailyTrainSeatServiceImpl(DailyTrainSeatMapper dailyTrainSeatMapper) {
        this.dailyTrainSeatMapper = dailyTrainSeatMapper;
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

}
