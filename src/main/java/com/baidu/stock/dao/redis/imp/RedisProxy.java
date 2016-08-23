package com.baidu.stock.dao.redis.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import com.baidu.gushitong.redis.client.RedisPoolProxy;
import com.baidu.gushitong.redis.client.RedisPoolProxyConfig;
import com.baidu.stock.dao.redis.IRedis;
import com.baidu.stock.exception.RedisException;
import com.baidu.stock.log.LogConstant;
import com.baidu.stock.log.LogUtils;

/**
 * redis 实例类
 */

public class RedisProxy extends IRedis {

    private static Logger logger = LoggerFactory.getLogger(RedisProxy.class);
    // redis 代理类
    private static RedisPoolProxy redisPoolProxy;

    // redis 最大连接数
    private String redisMaxtotal;

    // 最小空闲连接数
    private String redisMinidle;

    // 获取redis连接时最大等待毫秒数
    private String redisMaxwaitmillis;

    // 连接池超时时间
    private String redisPooltimeout;

    // redis bns名称
    private String redisBns;

    // redis bns类型 0:ksarch2.0集群 1:ksarch3.0集群
    private String redisBnstype;

    // redis bns重试次数
    private String bnsRetryTimes;

    // redis bns重试次数
    private String bnsTimeout;

    // 原生redis服务器地址
    private String proxyHost;

    // 原生redis服务器端口
    private String proxyPort;

    public void init() {
        try {
            RedisPoolProxyConfig redisPoolConfig = new RedisPoolProxyConfig();
            redisPoolConfig.setMaxTotal(Integer.parseInt(redisMaxtotal));
            redisPoolConfig.setMinIdle(Integer.parseInt(redisMinidle));
            redisPoolConfig.setMaxWaitMillis(Integer.parseInt(redisMaxwaitmillis));
            redisPoolConfig.setPoolTimeOut(Integer.parseInt(redisPooltimeout));
            redisPoolConfig.setProxyBns(redisBns);
            redisPoolConfig.setBnsRetryTimes(Integer.parseInt(bnsRetryTimes));
            redisPoolConfig.setBnsTimeout(Integer.parseInt(bnsTimeout));
            redisPoolConfig.setProxyBnsType(Integer.parseInt(redisBnstype));
            redisPoolConfig.setProxyHost(proxyHost);
            redisPoolConfig.setProxyPort(Integer.parseInt(proxyPort));

            init(redisPoolConfig);
        } catch (Exception e) {
            throw new RedisException(" redis init error ", e);
        }
    }

    public void init(RedisPoolProxyConfig redisPoolConfig) {
        try {
            redisPoolProxy = new RedisPoolProxy(redisPoolConfig);

            logger.info(LogUtils.getLogInfo(LogConstant.LEVLE1_COMMONS_MODULE, LogConstant.DEFAULT_INFO_KEY,
                    LogConstant.DEFAULT_INFO_VALUE, "redis init success"));
        } catch (Exception e) {
            throw new RedisException(" redis init error ", e);
        }
    }

    public Map<byte[], byte[]> hgetAll(byte[] key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Map<byte[], byte[]> map = jedis.hgetAll(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return map;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis hgetAll error, param " + new String(key), e);
        }
    }

    public byte[] hget(byte[] key, byte[] field) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            byte[] value = jedis.hget(key, field);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return value;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis hget error, param " + new String(key), e);
        }
    }

    public void hset(byte[] key, byte[] field, byte[] value) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis hset error, param " + new String(key), e);
        }
    }

    public void hmset(byte[] key, Map<byte[], byte[]> map) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.hmset(key, map);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis hmset error, param " + new String(key), e);
        }
    }

    public byte[] get(byte[] key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            byte[] result = jedis.get(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis get error, param " + new String(key), e);
        }
    }

    public String get(String key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            String result = jedis.get(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis get error, param " + key, e);
        }
    }

    public void pipelineSet(Map<byte[], byte[]> map) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (Entry<byte[], byte[]> entry : map.entrySet()) {
                pipeline.set(entry.getKey(), entry.getValue());
            }
            pipeline.sync();
            map.clear();
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis pipeline set error ", e);
        }
    }

    public void pipelinezadd(Map<byte[], Map<Double, byte[]>> map) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (Entry<byte[], Map<Double, byte[]>> entry : map.entrySet()) {
                for (Entry<Double, byte[]> value : entry.getValue().entrySet()) {
                    pipeline.zadd(entry.getKey(), value.getKey(), value.getValue());
                }
            }
            pipeline.sync();
            map.clear();
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis pipeline zadd error ", e);
        }
    }

    public void set(byte[] key, byte[] value) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis set error, param " + new String(key), e);
        }
    }

    public void del(byte[] key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.del(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis del error, param " + new String(key), e);
        }
    }

    public void lpush(byte[] key, byte[] value) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.del(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis lpush error, param " + new String(key), e);
        }
    }

    public void zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.zadd(key, scoreMembers);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zadd map error, param " + new String(key), e);
        }
    }

    public void zadd(byte[] key, double score, byte[] member) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            jedis.zadd(key, score, member);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zadd member error, param " + new String(key), e);
        }
    }

    public Set<byte[]> zrange(byte[] key, long start, long end) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Set<byte[]> resultSet = jedis.zrange(key, start, end);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return resultSet;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zrange error, param " + new String(key), e);
        }
    }

    public Long zcard(byte[] key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Long result = jedis.zcard(key);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zcard error, param " + new String(key), e);
        }
    }

    public Long zremrangeByRank(byte[] key, long start, long end) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Long result = jedis.zremrangeByRank(key, start, end);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zremrangeByRank error, param " + new String(key), e);
        }
    }

    public Long zremrangeByScore(byte[] key, long start, long end) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Long result = jedis.zremrangeByScore(key, start, end);
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zremrangeByScore error, param " + new String(key), e);
        }
    }

    public Map<byte[], Double> zrangeWithScores(byte[] key, long start, long end) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        Map<byte[], Double> result = new HashMap<byte[], Double>();
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            Set<Tuple> resultSet = jedis.zrangeWithScores(key, start, end);
            for (Tuple tuple : resultSet) {
                result.put(tuple.getBinaryElement(), tuple.getScore());
            }
            redisPoolProxy.returnResource(jedisPool, jedis);
            return result;
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis zrangeWithScores error, param " + new String(key), e);
        }
    }

    public List<byte[]> mget(byte[]...keys) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        List<byte[]> resultList = new ArrayList<byte[]>();
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            resultList = jedis.mget(keys);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis mget error: ", e);
        }

        return resultList;
    }

    public List<String> mget(String...keys) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        List<String> resultList = new ArrayList<String>();
        try {
            jedisPool = redisPoolProxy.getJedisPool();
            jedis = jedisPool.getResource();
            resultList = jedis.mget(keys);
            redisPoolProxy.returnResource(jedisPool, jedis);
        } catch (Exception e) {
            redisPoolProxy.returnBrokenResource(jedisPool, jedis);
            throw new RedisException(jedis, " redis mget error: ", e);
        }

        return resultList;
    }

    public static RedisPoolProxy getRedisPoolProxy() {
        return redisPoolProxy;
    }

    public static void setRedisPoolProxy(RedisPoolProxy redisPoolProxy) {
        RedisProxy.redisPoolProxy = redisPoolProxy;
    }
}