package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.SeatColEnum;
import com.siyi.train.business.dto.TrainCarriageQueryDto;
import com.siyi.train.business.dto.TrainCarriageSaveDto;
import com.siyi.train.business.mapper.TrainCarriageMapper;
import com.siyi.train.business.pojo.TrainCarriage;
import com.siyi.train.business.pojo.TrainCarriageExample;
import com.siyi.train.business.service.TrainCarriageService;
import com.siyi.train.business.vo.TrainCarriageQueryVo;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrainCarriageServiceImpl implements TrainCarriageService {

    private final TrainCarriageMapper trainCarriageMapper;

    public TrainCarriageServiceImpl(TrainCarriageMapper trainCarriageMapper) {
        this.trainCarriageMapper = trainCarriageMapper;
    }

    @Override
    public void save(TrainCarriageSaveDto dto) {
        DateTime now = DateTime.now();
        TrainCarriage trainCarriage = BeanUtil.copyProperties(dto, TrainCarriage.class);

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(dto.getSeatType());
        trainCarriage.setColCount(seatColEnums.size());
        trainCarriage.setSeatCount(trainCarriage.getColCount() * trainCarriage.getRowCount());

        if (ObjectUtil.isNull(trainCarriage.getId())) {
            // 保存之前，先校验唯一键是否存在
            TrainCarriage trainCarriageDB = this.selectByUnique(dto.getTrainCode(), dto.getIndex());
            if (ObjectUtil.isNotEmpty(trainCarriageDB)) {
                throw new BusinessException(ResultCode.BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR);
            }

            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            this.trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            this.trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }
    }

    @Override
    public PageVo<TrainCarriageQueryVo> queryList(TrainCarriageQueryDto dto) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("train_code asc, `index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();

        if (ObjectUtil.isNotEmpty(dto.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dto.getTrainCode());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<TrainCarriage> trainCarriageList = this.trainCarriageMapper.selectByExample(trainCarriageExample);

        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriageList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryVo> list = BeanUtil.copyToList(trainCarriageList, TrainCarriageQueryVo.class);

        PageVo<TrainCarriageQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.trainCarriageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("`index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        return this.trainCarriageMapper.selectByExample(trainCarriageExample);
    }

    private TrainCarriage selectByUnique(String trainCode, Integer index) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andIndexEqualTo(index);
        List<TrainCarriage> list = trainCarriageMapper.selectByExample(trainCarriageExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
