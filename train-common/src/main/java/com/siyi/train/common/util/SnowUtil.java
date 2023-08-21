package com.siyi.train.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @ClassName: SnowUtil
 * @Auther: Chengxin Zhang
 * @Date: 2023/8/18 22:34
 * @Description: 封装hutool雪花算法
 * @Version 1.0.0
 */
public class SnowUtil {
    /**
     * 数据中心
     */
    private static final long DATA_CENTER_ID = 1;
    /**
     * 机器标识
     */
    private static final long WORKER_ID = 1;

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextIdStr();
    }
}
