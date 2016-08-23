package com.baidu.stock.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final String SEPARATOR = " | ";

    public static String getLogInfo(String module, String logKey, String logValue, String logInfo) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(module).append(logKey).append(SEPARATOR).append(logValue).append(SEPARATOR).append(logInfo);
        return buffer.toString();
    }

    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void main(String[] args) {
        // 数据处理模块 info defalut
        logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_PROCESSOR_MODULE, LogConstant.DEFAULT_INFO_KEY,
                LogConstant.DEFAULT_INFO_VALUE, "系统初始化正常"));

        // 资金流向模块 info defalut
        logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_FUNDFLOW_MODULE, LogConstant.DEFAULT_INFO_KEY,
                LogConstant.DEFAULT_INFO_VALUE, "系统初始化正常"));

        // 排行榜模块 info defalut
        logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_RANKLIST_MODULE, LogConstant.DEFAULT_INFO_KEY,
                LogConstant.DEFAULT_INFO_VALUE, "系统初始化正常"));

        // 快照模块 info defalut
        logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_SNAPSHOT_MODULE, LogConstant.DEFAULT_INFO_KEY,
                LogConstant.DEFAULT_INFO_VALUE, "系统初始化正常"));

        // 分时模块 info defalut
        logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_TIMELINE_MODULE, LogConstant.DEFAULT_INFO_KEY,
                LogConstant.DEFAULT_INFO_VALUE, "系统初始化正常"));

        // 分时模块 redis error
        logger.error(LogUtils.getLogInfo(LogConstant.LEVLE1_TIMELINE_MODULE, LogConstant.REDIS_ERROR_KEY,
                LogConstant.REDIS_ERROR_VALUE, "写入redis error,redis ip:127.0.0.1"));
    }
}