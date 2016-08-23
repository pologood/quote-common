package com.baidu.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.stock.log.LogConstant;
import com.baidu.stock.log.LogUtils;

import redis.clients.jedis.Jedis;

/**
 * redis 异常类
 * 
 */
public class RedisException extends RuntimeException {

    private static final long serialVersionUID = -926138142805620640L;
    private static Logger logger = LoggerFactory.getLogger(RedisException.class);

    public RedisException(String msg) {
        super(msg);
    }

    public RedisException() {
        super();
    }

    public RedisException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        logger.error(LogUtils.getLogInfo(LogConstant.LEVLE1_COMMONS_MODULE, LogConstant.REDIS_ERROR_KEY,
                LogConstant.REDIS_ERROR_VALUE, arg0), arg0);
    }

    public RedisException(Throwable arg0) {
        super(arg0);

        logger.error(LogUtils.getLogInfo(LogConstant.LEVLE1_COMMONS_MODULE, LogConstant.REDIS_ERROR_KEY,
                LogConstant.REDIS_ERROR_VALUE, ""), arg0);
    }

    public RedisException(Jedis jedis, String arg0, Throwable arg1) {
        super(arg0);
        StringBuffer ipinfo = new StringBuffer();
        if (jedis != null) {
            ipinfo.append("redis server ip: ");
            ipinfo.append(jedis.getClient().getHost());
        }

        logger.error(LogUtils.getLogInfo(LogConstant.LEVLE1_COMMONS_MODULE, LogConstant.REDIS_ERROR_KEY,
                LogConstant.REDIS_ERROR_VALUE, ipinfo.toString() + "  " + arg0), arg1);
    }

}
