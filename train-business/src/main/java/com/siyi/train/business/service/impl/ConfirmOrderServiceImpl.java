package com.siyi.train.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.business.constant.ConfirmOrderStatusEnum;
import com.siyi.train.business.constant.RedisKeyPreEnum;
import com.siyi.train.business.constant.SeatColEnum;
import com.siyi.train.business.constant.SeatTypeEnum;
import com.siyi.train.business.dto.ConfirmOrderDoDto;
import com.siyi.train.business.dto.ConfirmOrderMQDto;
import com.siyi.train.business.dto.ConfirmOrderQueryDto;
import com.siyi.train.business.dto.ConfirmOrderTicketDto;
import com.siyi.train.business.mapper.ConfirmOrderMapper;
import com.siyi.train.business.pojo.*;
import com.siyi.train.business.service.*;
import com.siyi.train.business.vo.ConfirmOrderQueryVo;
import com.siyi.train.common.constant.ResultCode;
import com.siyi.train.common.exception.BusinessException;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.common.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {

    private final ConfirmOrderMapper confirmOrderMapper;
    private final DailyTrainTicketService dailyTrainTicketService;
    private final DailyTrainCarriageService dailyTrainCarriageService;
    private final DailyTrainSeatService dailyTrainSeatService;
    private final AfterConfirmOrderService afterConfirmOrderService;
    private final RedissonClient redissonClient;

    public ConfirmOrderServiceImpl(ConfirmOrderMapper confirmOrderMapper,
                                   DailyTrainTicketService dailyTrainTicketService,
                                   DailyTrainCarriageService dailyTrainCarriageService,
                                   DailyTrainSeatService dailyTrainSeatService,
                                   AfterConfirmOrderService afterConfirmOrderService,
                                   RedissonClient redissonClient) {
        this.confirmOrderMapper = confirmOrderMapper;
        this.dailyTrainTicketService = dailyTrainTicketService;
        this.dailyTrainCarriageService = dailyTrainCarriageService;
        this.dailyTrainSeatService = dailyTrainSeatService;
        this.afterConfirmOrderService = afterConfirmOrderService;
        this.redissonClient = redissonClient;
    }

    @Override
    public void save(ConfirmOrderDoDto dto) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(dto, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            this.confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            this.confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }
    }

    @Override
    public PageVo<ConfirmOrderQueryVo> queryList(ConfirmOrderQueryDto dto) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        log.info("查询页码：{}", dto.getPage());
        log.info("每页条数：{}", dto.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<ConfirmOrder> confirmOrderList = this.confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryVo> list = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryVo.class);

        PageVo<ConfirmOrderQueryVo> pageVo = new PageVo<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo;
    }

    @Override
    public void delete(Long id) {
        this.confirmOrderMapper.deleteByPrimaryKey(id);
    }

    @SentinelResource(value = "doConfirm", blockHandler = "doConfirmBlock")
    @Override
    public void doConfirm(ConfirmOrderMQDto dto) {
        // 省略业务数据校验，如：车次是否存在，余票是否存在，车次是否在有效期内，tickets条数>0，同乘客同车次是否已买过

        // 校验令牌余量
//        boolean validSkToken = this.skTokenService.validSkToken(dto.getDate(), dto.getTrainCode(), LoginMemberContext.getId());
//        if (validSkToken) {
//            log.info("令牌校验通过");
//        } else {
//            log.info("令牌校验不通过");
//            throw new BusinessException(ResultCode.CONFIRM_ORDER_SK_TOKEN_FAIL);
//        }

        String lockKey = RedisKeyPreEnum.CONFIRM_ORDER + "-" + DateUtil.formatDate(dto.getDate()) + "-" + dto.getTrainCode();
        RLock lock = null;
        try {
            // 使用redisson，自带看门狗
            lock = redissonClient.getLock(lockKey);

            // 红锁的写法
            // RedissonRedLock redissonRedLock = new RedissonRedLock(lock, lock, lock);
            // boolean tryLock1 = redissonRedLock.tryLock(0, TimeUnit.SECONDS);

            /**
             waitTime – the maximum time to acquire the lock 等待获取锁时间(最大尝试获得锁的时间)，超时返回false
             leaseTime – lease time 锁时长，即n秒后自动释放锁
             time unit – time unit 时间单位
             */
            // boolean tryLock = lock.tryLock(30, 10, TimeUnit.SECONDS); // 不带看门狗
            boolean tryLock = lock.tryLock(0, TimeUnit.SECONDS); // 带看门狗
            if (tryLock) {
                log.info("恭喜，抢到锁了！");
                // 可以把下面这段放开，只用一个线程来测试，看看redisson的看门狗效果
                // for (int i = 0; i < 30; i++) {
                //     Long expire = redisTemplate.opsForValue().getOperations().getExpire(lockKey);
                //     log.info("锁过期时间还有：{}", expire);
                //     Thread.sleep(1000);
                // }
            } else {
                // 只是没抢到锁，并不知道票抢完了没，所以提示稍候再试
                log.info("很遗憾，没抢到锁");
                return;
            }

//            Date date = dto.getDate();
//            String trainCode = dto.getTrainCode();
//            String start = dto.getStart();
//            String end = dto.getEnd();
//            List<ConfirmOrderTicketDto> tickets = dto.getTickets();

//            // 保存确认订单表，状态初始
//            DateTime now = DateTime.now();
//            ConfirmOrder confirmOrder = new ConfirmOrder();
//            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
//            confirmOrder.setCreateTime(now);
//            confirmOrder.setUpdateTime(now);
//            confirmOrder.setMemberId(LoginMemberContext.getId());
//            confirmOrder.setDate(now);
//            confirmOrder.setTrainCode(trainCode);
//            confirmOrder.setStart(start);
//            confirmOrder.setEnd(end);
//            confirmOrder.setDailyTrainTicketId(dto.getDailyTrainTicketId());
//            confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
//            confirmOrder.setTickets(JSON.toJSONString(tickets));
//            this.confirmOrderMapper.insert(confirmOrder);

            while (true) {
                // 取确认订单表的记录，同日期车次，状态是I，分页处理，每次取N条
                ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
                confirmOrderExample.setOrderByClause("id asc");
                ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();
                criteria.andDateEqualTo(dto.getDate())
                        .andTrainCodeEqualTo(dto.getTrainCode())
                        .andStatusEqualTo(ConfirmOrderStatusEnum.INIT.getCode());
                PageHelper.startPage(1, 5);
                List<ConfirmOrder> list = confirmOrderMapper.selectByExampleWithBLOBs(confirmOrderExample);

                if (CollUtil.isEmpty(list)) {
                    log.info("没有需要处理的订单，结束循环");
                    break;
                } else {
                    log.info("本次处理{}条订单", list.size());
                }

                // 一条一条的卖
                list.forEach(confirmOrder -> {
                    try {
                        this.sell(confirmOrder);
                    } catch (BusinessException e) {
                        if (e.getResultCode().equals(ResultCode.CONFIRM_ORDER_TICKET_COUNT_ERROR)) {
                            log.info("本订单余票不足，继续售卖下一个订单");
                            confirmOrder.setStatus(ConfirmOrderStatusEnum.EMPTY.getCode());
                            this.updateStatus(confirmOrder);
                        } else {
                            throw e;
                        }
                    }
                });
            }
//            log.info("购票流程结束，释放锁！lockKey：{}", lockKey);
//            this.redisTemplate.delete(lockKey);
        } catch (InterruptedException e) {
            log.error("购票异常", e);
        } finally {
            // try finally不能包含加锁的那段代码，否则加锁失败会走到finally里，从而释放别的线程的锁
            log.info("购票流程结束，释放锁！lockKey：{}", lockKey);
            log.info("购票流程结束，释放锁！");
            if (null != lock && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 更新状态
     * @param confirmOrder
     */
    private void updateStatus(ConfirmOrder confirmOrder) {
        ConfirmOrder confirmOrderForUpdate = new ConfirmOrder();
        confirmOrderForUpdate.setId(confirmOrder.getId());
        confirmOrderForUpdate.setUpdateTime(new Date());
        confirmOrderForUpdate.setStatus(confirmOrder.getStatus());
        confirmOrderMapper.updateByPrimaryKeySelective(confirmOrderForUpdate);
    }

    /**
     * 售票
     *
     * @param confirmOrder
     */
    private void sell(ConfirmOrder confirmOrder) {
        // 为了演示排队效果，每次出票增加200毫秒延时
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 构造ConfirmOrderDoDto
        ConfirmOrderDoDto dto = new ConfirmOrderDoDto();
        dto.setMemberId(confirmOrder.getMemberId());
        dto.setDate(confirmOrder.getDate());
        dto.setTrainCode(confirmOrder.getTrainCode());
        dto.setStart(confirmOrder.getStart());
        dto.setEnd(confirmOrder.getEnd());
        dto.setDailyTrainTicketId(confirmOrder.getDailyTrainTicketId());
        dto.setTickets(JSON.parseArray(confirmOrder.getTickets(), ConfirmOrderTicketDto.class));
        dto.setImageCode("");
        dto.setImageCodeToken("");
        dto.setLogId("");

        // 省略业务数据校验，如：车次是否存在，余票是否存在，车次是否在有效期内，tickets条数>0，同乘客同车次是否已买过

        // 将订单设置成处理中，避免重复处理
        log.info("将确认订单更新成处理中，避免重复处理，confirm_order.id: {}", confirmOrder.getId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.PENDING.getCode());
        this.updateStatus(confirmOrder);

        Date date = dto.getDate();
        String trainCode = dto.getTrainCode();
        String start = dto.getStart();
        String end = dto.getEnd();
        List<ConfirmOrderTicketDto> tickets = dto.getTickets();
        //
        // // 保存确认订单表，状态初始
        // DateTime now = DateTime.now();
        // ConfirmOrder confirmOrder = new ConfirmOrder();
        // confirmOrder.setId(SnowUtil.getSnowflakeNextId());
        // confirmOrder.setCreateTime(now);
        // confirmOrder.setUpdateTime(now);
        // confirmOrder.setMemberId(dto.getMemberId());
        // confirmOrder.setDate(date);
        // confirmOrder.setTrainCode(trainCode);
        // confirmOrder.setStart(start);
        // confirmOrder.setEnd(end);
        // confirmOrder.setDailyTrainTicketId(dto.getDailyTrainTicketId());
        // confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        // confirmOrder.setTickets(JSON.toJSONString(tickets));
        // confirmOrderMapper.insert(confirmOrder);

        // // 从数据库里查出订单
        // ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        // confirmOrderExample.setOrderByClause("id asc");
        // ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();
        // criteria.andDateEqualTo(req.getDate())
        //         .andTrainCodeEqualTo(req.getTrainCode())
        //         .andStatusEqualTo(ConfirmOrderStatusEnum.INIT.getCode());
        // List<ConfirmOrder> list = confirmOrderMapper.selectByExampleWithBLOBs(confirmOrderExample);
        // ConfirmOrder confirmOrder;
        // if (CollUtil.isEmpty(list)) {
        //     log.info("找不到原始订单，结束");
        //     return;
        // } else {
        //     log.info("本次处理{}条确认订单", list.size());
        //     confirmOrder = list.get(0);
        // }

        // 查出余票记录，需要得到真实的库存
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
        log.info("查出余票记录：{}", dailyTrainTicket);

        // 预扣减余票数量，并判断余票是否足够
        reduceTickets(dto, dailyTrainTicket);

        // 最终的选座结果
        List<DailyTrainSeat> finalSeatList = new ArrayList<>();
        // 计算相对第一个座位的偏移值
        // 比如选择的是C1,D2，则偏移值是：[0,5]
        // 比如选择的是A1,B1,C1，则偏移值是：[0,1,2]
        ConfirmOrderTicketDto ticketDto0 = tickets.get(0);
        if (StrUtil.isNotBlank(ticketDto0.getSeat())) {
            log.info("本次购票有选座");
            // 查出本次选座的座位类型都有哪些列，用于计算所选座位与第一个座位的偏离值
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(ticketDto0.getSeatTypeCode());
            log.info("本次选座的座位类型包含的列：{}", colEnumList);

            // 组成和前端两排选座一样的列表，用于作参照的座位列表，例：referSeatList = {A1, C1, D1, F1, A2, C2, D2, F2}
            List<String> referSeatList = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                for (SeatColEnum seatColEnum : colEnumList) {
                    referSeatList.add(seatColEnum.getCode() + i);
                }
            }
            log.info("用于作参照的两排座位：{}", referSeatList);

            List<Integer> offsetList = new ArrayList<>();
            // 绝对偏移值，即：在参照座位列表中的位置
            List<Integer> aboluteOffsetList = new ArrayList<>();
            for (ConfirmOrderTicketDto ticketDto : tickets) {
                int index = referSeatList.indexOf(ticketDto.getSeat());
                aboluteOffsetList.add(index);
            }
            log.info("计算得到所有座位的绝对偏移值：{}", aboluteOffsetList);
            for (Integer index : aboluteOffsetList) {
                int offset = index - aboluteOffsetList.get(0);
                offsetList.add(offset);
            }
            log.info("计算得到所有座位的相对第一个座位的偏移值：{}", offsetList);

            getSeat(finalSeatList,
                    date,
                    trainCode,
                    ticketDto0.getSeatTypeCode(),
                    ticketDto0.getSeat().split("")[0], // 从A1得到A
                    offsetList,
                    dailyTrainTicket.getStartIndex(),
                    dailyTrainTicket.getEndIndex()
            );

        } else {
            log.info("本次购票没有选座");
            for (ConfirmOrderTicketDto ticketDto : tickets) {
                getSeat(finalSeatList,
                        date,
                        trainCode,
                        ticketDto.getSeatTypeCode(),
                        null,
                        null,
                        dailyTrainTicket.getStartIndex(),
                        dailyTrainTicket.getEndIndex()
                );
            }
        }

        log.info("最终选座：{}", finalSeatList);

        // 选中座位后事务处理：
        // 座位表修改售卖情况sell；
        // 余票详情表修改余票；
        // 为会员增加购票记录
        // 更新确认订单为成功
        try {
            afterConfirmOrderService.afterDoConfirm(dailyTrainTicket, finalSeatList, tickets, confirmOrder);
        } catch (Exception e) {
            log.error("保存购票信息失败", e);
            throw new BusinessException(ResultCode.CONFIRM_ORDER_EXCEPTION);
        }
    }

    /**
     * 挑座位，如果有选座，则一次性挑完，如果无选座，则一个一个挑
     *
     * @param finalSeatList
     * @param date
     * @param trainCode
     * @param seatType
     * @param column
     * @param offsetList
     */
    private void getSeat(List<DailyTrainSeat> finalSeatList, Date date, String trainCode, String seatType, String column, List<Integer> offsetList, Integer startIndex, Integer endIndex) {
        List<DailyTrainSeat> getSeatList;

        List<DailyTrainCarriage> carriageList = this.dailyTrainCarriageService.selectByType(date, trainCode, seatType);
        log.info("共查出{}个符合条件的车厢", carriageList.size());

        // 一个车厢一个车厢的获取座位数据
        for (DailyTrainCarriage dailyTrainCarriage : carriageList) {
            getSeatList = new ArrayList<>();
            log.info("开始从车厢{}选座", dailyTrainCarriage.getIndex());
            List<DailyTrainSeat> seatList = this.dailyTrainSeatService.selectByCarriage(date, trainCode, dailyTrainCarriage.getIndex());
            log.info("车厢{}的座位数:{}", dailyTrainCarriage.getIndex(), seatList.size());

            for (int i = 0; i < seatList.size(); i++) {
                DailyTrainSeat dailyTrainSeat = seatList.get(i);
                Integer seatIndex = dailyTrainSeat.getCarriageSeatIndex();
                String col = dailyTrainSeat.getCol();

                // 判断当前座位不能被选中过
                boolean alreadyChooseFlag = false;
                for (DailyTrainSeat finalSeat : finalSeatList) {
                    if (finalSeat.getId().equals(dailyTrainSeat.getId())) {
                        alreadyChooseFlag = true;
                        break;
                    }
                }
                if (alreadyChooseFlag) {
                    log.info("座位{}被选中过，不能重复选中，继续判断下一个座位", seatIndex);
                    continue;
                }

                // 判断column，有值的话要比对序号
                if (StrUtil.isBlank(column)) {
                    log.info("无选座");
                } else {
                    if (!column.equals(col)) {
                        log.info("座位{}列值不对，继续判断下一个座位，当前列值：{}，目标列值：{}", seatIndex, col, column);
                        continue;
                    }
                }

                boolean isChoose = this.calSell(dailyTrainSeat, startIndex, endIndex);
                if (isChoose) {
                    log.info("选中座位");
                    getSeatList.add(dailyTrainSeat);
                } else {
                    continue;
                }

                boolean isGetAllOffsetSeat = true;

                // 根据offset选剩下的座位
                if (CollUtil.isNotEmpty(offsetList)) {
                    log.info("有偏移值：{}，校验偏移的座位是否可选", offsetList);
                    // 从索引1开始，索引0就是当前已选中的票
                    for (int j = 1; j < offsetList.size(); j++) {
                        Integer offset = offsetList.get(j);
                        // 座位在库的索引是从1开始
//                        int nextIndex = seatIndex + offset - 1;
                        int nextIndex = i + offset;

                        // 有选座时，一定是在同一个车厢
                        if (nextIndex >= seatList.size()) {
                            log.info("座位{}不可选，偏移后的索引超出了这个车厢的座位数", nextIndex);
                            isGetAllOffsetSeat = false;
                            break;
                        }

                        DailyTrainSeat nextDailyTrainSeat = seatList.get(nextIndex);
                        boolean isChooseNext = this.calSell(nextDailyTrainSeat, startIndex, endIndex);
                        if (isChooseNext) {
                            log.info("座位{}被选中", nextDailyTrainSeat.getCarriageSeatIndex());
                            getSeatList.add(nextDailyTrainSeat);
                        } else {
                            log.info("座位{}不可选", nextDailyTrainSeat.getCarriageSeatIndex());
                            isGetAllOffsetSeat = false;
                            break;
                        }
                    }
                }

                if (!isGetAllOffsetSeat) {
                    getSeatList = new ArrayList<>();
                    continue;
                }

                // 保存选好的座位
                finalSeatList.addAll(getSeatList);
                return;
            }
        }
    }

    /**
     * 计算某座位在区间内是否可卖
     * 例：sell=10001,本次购买区间站1-4，则区间已售000
     * 全部是0，表示这个区间可买，只要有1，就表示区间内已售过票
     * <p>
     * 选中后，要计算购票后的sell，比如原来是10001,本次购买区间站1-4
     * 方案：构造本次购票造成的售卖信息01110，和原sell 10001按位或，最终得到11111
     *
     * @param dailyTrainSeat
     * @param startIndex
     * @param endIndex
     */
    private boolean calSell(DailyTrainSeat dailyTrainSeat, Integer startIndex, Integer endIndex) {
        String sell = dailyTrainSeat.getSell();
        String sellPart = sell.substring(startIndex - 1, endIndex - 1);
        if (Integer.parseInt(sellPart) > 0) {
            log.info("座位{}在本次车站区间{}~{}已售过票，不可选中该座位", dailyTrainSeat.getCarriageSeatIndex(), startIndex - 1, endIndex - 1);
            return false;
        } else {
            log.info("座位{}在本次车站区间{}~{}未售过票，可选中该座位", dailyTrainSeat.getCarriageSeatIndex(), startIndex - 1, endIndex - 1);
            // 111
            String curSell = sellPart.replace('0', '1');
            // 0111
            curSell = StrUtil.fillBefore(curSell, '0', endIndex - 1);
            // 01110
            curSell = StrUtil.fillAfter(curSell, '0', sell.length());

            // 当前区间售票信息curSell 01110与库里的已售信息sell 00001按位或，即可得到该座位卖出此票后的售票详情
            int newSellInt = NumberUtil.binaryToInt(curSell) | NumberUtil.binaryToInt(sell);
            String newSell = StrUtil.fillBefore(NumberUtil.getBinaryStr(newSellInt), '0', sell.length());
            log.info("座位{}被选中，原售票信息：{},车站区间：{}~{}, 即：{}, 最终售票信息：{}", dailyTrainSeat.getCarriageSeatIndex(), sell, startIndex, endIndex, curSell, newSell);
            dailyTrainSeat.setSell(newSell);

            return true;
        }
    }

    /**
     * 扣减余票数量，并判断余票是否足够
     *
     * @param dto
     * @param dailyTrainTicket
     */
    private void reduceTickets(ConfirmOrderDoDto dto, DailyTrainTicket dailyTrainTicket) {
        for (ConfirmOrderTicketDto ticket : dto.getTickets()) {
            String seatTypeCode = ticket.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatTypeCode);
            switch (seatTypeEnum) {
                case YDZ -> {
                    int countLeft = dailyTrainTicket.getYdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(ResultCode.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYdz(countLeft);
                }
                case EDZ -> {
                    int countLeft = dailyTrainTicket.getEdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(ResultCode.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setEdz(countLeft);
                }
                case RW -> {
                    int countLeft = dailyTrainTicket.getRw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(ResultCode.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setRw(countLeft);
                }
                case YW -> {
                    int countLeft = dailyTrainTicket.getYw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(ResultCode.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYw(countLeft);
                }
            }
        }
    }

    /**
     * 查询前面有几个人在排队
     * @param id
     */
    @Override
    public Integer queryLineCount(Long id) {
        ConfirmOrder confirmOrder = confirmOrderMapper.selectByPrimaryKey(id);
        ConfirmOrderStatusEnum statusEnum = EnumUtil.getBy(ConfirmOrderStatusEnum::getCode, confirmOrder.getStatus());
        int result = switch (statusEnum) {
            case PENDING -> 0; // 排队0
            case SUCCESS -> -1; // 成功
            case FAILURE -> -2; // 失败
            case EMPTY -> -3; // 无票
            case CANCEL -> -4; // 取消
            case INIT -> 999; // 需要查表得到实际排队数量
        };

        if (result == 999) {
            // 排在第几位，下面的写法：where a=1 and (b=1 or c=1) 等价于 where (a=1 and b=1) or (a=1 and c=1)
            ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
            confirmOrderExample.or().andDateEqualTo(confirmOrder.getDate())
                    .andTrainCodeEqualTo(confirmOrder.getTrainCode())
                    .andCreateTimeLessThan(confirmOrder.getCreateTime())
                    .andStatusEqualTo(ConfirmOrderStatusEnum.INIT.getCode());
            confirmOrderExample.or().andDateEqualTo(confirmOrder.getDate())
                    .andTrainCodeEqualTo(confirmOrder.getTrainCode())
                    .andCreateTimeLessThan(confirmOrder.getCreateTime())
                    .andStatusEqualTo(ConfirmOrderStatusEnum.PENDING.getCode());
            return Math.toIntExact(confirmOrderMapper.countByExample(confirmOrderExample));
        } else {
            return result;
        }
    }

    /**
     * 取消排队，只有I状态才能取消排队，所以按状态更新
     * @param id
     */
    @Override
    public Integer cancel(Long id) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();
        criteria.andIdEqualTo(id).andStatusEqualTo(ConfirmOrderStatusEnum.INIT.getCode());
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setStatus(ConfirmOrderStatusEnum.CANCEL.getCode());
        return confirmOrderMapper.updateByExampleSelective(confirmOrder, confirmOrderExample);
    }

    /**
     * 降级方法，需要包换限流方法的所有参数和BlockException参数
     *
     * @param dto
     * @param e
     */
    public void doConfirmBlock(ConfirmOrderDoDto dto, BlockException e) {
        log.info("购票请求被限流： {}", dto);
        throw new BusinessException(ResultCode.CONFIRM_ORDER_LOCK_FAIL);
    }
}
