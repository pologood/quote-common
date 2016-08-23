package com.baidu.stock.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 接口类
 * 
 * @author zhangxinyu03
 *
 */

public abstract class IRedis {

    /**
     * 初始化redis连接池
     */
    public abstract void init();

    /**
     * 获取map所有的数据
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract Map<byte[], byte[]> hgetAll(byte[] key);

    /**
     * 获取value
     * 
     * @param key
     * @return
     */
    public abstract byte[] get(byte[] key);

    /**
     * 获取value
     * 
     * @param key
     * @return
     */
    public abstract String get(String key);

    /**
     * 获取map filed所有的数据
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract byte[] hget(byte[] key, byte[] field);

    /**
     * 删除key
     * 
     * @param key
     */
    public abstract void del(byte[] key);

    /**
     * @param key
     * @param value
     */
    public abstract void lpush(byte[] key, byte[] value);

    /**
     * @param key
     * @param value
     */
    public abstract void set(byte[] key, byte[] value);

    /**
     * @param key
     * @param scoreMembers
     */
    public abstract void zadd(byte[] key, Map<byte[], Double> scoreMembers);

    /**
     * @param key
     * @param score
     * @param member
     */
    public abstract void zadd(byte[] key, double score, byte[] member);

    /**
     * @param key
     * @param map
     */
    public abstract void hmset(byte[] key, Map<byte[], byte[]> map);

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract Set<byte[]> zrange(byte[] key, long start, long end);

    /**
     * @param key
     * @return
     */
    public abstract Long zcard(byte[] key);

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract Long zremrangeByRank(byte[] key, long start, long end);

    /**
     * @param map
     */
    public abstract void pipelinezadd(Map<byte[], Map<Double, byte[]>> map);

    /**
     * @param map
     */
    public abstract void pipelineSet(Map<byte[], byte[]> map);

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract Long zremrangeByScore(byte[] key, long start, long end);

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public abstract Map<byte[], Double> zrangeWithScores(byte[] key, long start, long end);

    /**
     * @param key
     * @param field
     * @param value
     */
    public abstract void hset(byte[] key, byte[] field, byte[] value);

    /**
     * @param keys
     * @return
     */
    public abstract List<String> mget(String...keys);

    /**
     * @param keys
     * @return
     */
    public abstract List<byte[]> mget(byte[]...keys);
}