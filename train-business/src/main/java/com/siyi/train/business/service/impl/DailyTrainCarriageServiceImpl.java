package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.SeatColEnum;
import com.siyi.train.business.dto.DailyTrainCarriageQueryDto;
import com.siyi.train.business.dto.DailyTrainCarriageSaveDto;
import com.siyi.train.business.mapper.DailyTrainCarriageMapper;
import com.siyi.train.business.pojo.DailyTrainCarriage;
import com.siyi.train.business.pojo.DailyTrainCarriageExample;
import com.siyi.train.business.service.DailyTrainCarriageService;
import com.siyi.train.business.vo.DailyTrainCarriageQueryVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyTrainCarriageServiceImpl implements DailyTrainCarriageService {

    private final DailyTrainCarriageMapper dailyTrainCarriageMapper;

    public DailyTrainCarriageServiceImpl(DailyTrainCarriageMapper dailyTrainCarriageMapper) {
        this.dailyTrainCarriageMapper = dailyTrainCarriageMapper;
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

}
