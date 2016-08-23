package com.baidu.stock.log;

/**
 * 统一定义异常信息 所有的异常信息的编码 包括debug info warn error 日志字典
 * 
 * @author baidu
 *
 */
public class LogConstant {

    /**
     * 数据处理模块 10 001 xxxxx 快照模块 10 002 xxxxx 分时模块 10 003 xxxxx 资金流向模块 10 004 xxxxx 排行榜模块 10 005 xxxxx
     * 
     * 行情源依赖数据模块 10 006 xxxxx
     * 
     * 例如 10 006 xxxxx 10 代表行情源 006 代表模块 xxxxx 定义 00000 默认值 00001
     **/
    // 数据处理模块
    public static final String LEVLE1_PROCESSOR_MODULE = "10001";
    // 快照模块
    public static final String LEVLE1_SNAPSHOT_MODULE = "10002";
    // 分时模块
    public static final String LEVLE1_TIMELINE_MODULE = "10003";
    // 资金流向模块
    public static final String LEVLE1_FUNDFLOW_MODULE = "10004";
    // 排行榜模块
    public static final String LEVLE1_RANKLIST_MODULE = "10005";
    // 基础数据模块
    public static final String MATERIAL_MODULE = "10006";
    // 公共数据模块
    public static final String LEVLE1_COMMONS_MODULE = "10007";

    // debug 默认值 key
    public static final String DEFAULT_DEBUG_KEY = "00000";
    // debug 默认值 value
    public static final String DEFAULT_DEBUG_VALUE = "[debug default]";

    // info 默认值 key
    public static final String DEFAULT_INFO_KEY = "02000";
    // info 默认值 value
    public static final String DEFAULT_INFO_VALUE = "[info default]";

    // warning 默认值 key
    public static final String DEFAULT_WARN_KEY = "03000";
    // warning 默认值 value
    public static final String DEFAULT_WARN_VALUE = "[warn default]";

    // error 默认值 key
    public static final String DEFAULT_ERROR_KEY = "05000";
    // error 默认值 value
    public static final String DEFAULT_ERROR_VALUE = "[error default]";

    // 行情源不更新 key
    public static final String QUOTE_FILE_ERROR_KEY = "05004";
    // 行情源不更新 value
    public static final String QUOTE_FILE_ERROR_VALUE = "[quote file update error]";

    // MQ key MQ 异常
    public static final String MQ_ERROR_KEY = "05005";
    // MQ value MQ 异常
    public static final String MQ_ERROR_VALUE = "[mq error]";

    // REDIS key 异常
    public static final String REDIS_ERROR_KEY = "05006";
    // REDIS value 异常
    public static final String REDIS_ERROR_VALUE = "[redis error]";

    // 初始化 异常
    public static final String INIT_ERROR_KEY = "05007";
    // 初始化 异常
    public static final String INIT_ERROR_VALUE = "[init error]";

    // 任务堆积 异常
    public static final String TASK_OVER_ERROR_KEY = "05008";
    // 任务堆积 异常
    public static final String TASK_OVER_ERROR_VALUE = "[task over error]";

    // master slave 切换 异常
    public static final String MASTER_SLAVE_SWITCH_ERROR_KEY = "05009";
    // master slave 切换 异常
    public static final String MASTER_SLAVE_SWITCH_ERROR_VALUE = "[master slave error]";

    // mysql access 异常
    public static final String MYSQL_ACCESS_ERROR_KEY = "05010";
    // mysql access 异常
    public static final String MYSQL_ACCESS_ERROR_VALUE = "[mysql access error]";
}
