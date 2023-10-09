package com.siyi.train.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.common.dto.MemberTicketDto;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.member.dto.TicketQueryDto;
import com.siyi.train.member.mapper.TicketMapper;
import com.siyi.train.member.pojo.Ticket;
import com.siyi.train.member.pojo.TicketExample;
import com.siyi.train.member.service.TicketService;
import com.siyi.train.member.vo.TicketQueryVo;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Override
    public void save(MemberTicketDto dto) {
        log.info("seata全局事务ID save: {}", RootContext.getXID());
        DateTime now = DateTime.now();
        Ticket ticket = BeanUtil.copyProperties(dto, Ticket.class);
        ticket.setId(SnowUtil.getSnowflakeNextId());
        ticket.setCreateTime(now);
        ticket.setUpdateTime(now);
        this.ticketMapper.insert(ticket);
    }

    @Override
    public PageVo<TicketQueryVo> queryList(TicketQueryDto dto) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.setOrderByClause("id desc");
        TicketExample.Criteria criteria = ticketExample.createCriteria();
        if (ObjectUtil.isNotNull(dto.getMemberId())) {
            criteria.andMemberIdEqualTo(dto.getMemberId());
        }

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<Ticket> ticketList = this.ticketMapper.selectByExample(ticketExample);

        PageInfo<Ticket> pageInfo = new PageInfo<>(ticketList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TicketQueryVo> list = BeanUtil.copyToList(ticketList, TicketQueryVo.class);

        PageVo<TicketQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo; 
    } 
            
    @Override
    public void delete(Long id) {
        this.ticketMapper.deleteByPrimaryKey(id);
    }

}
