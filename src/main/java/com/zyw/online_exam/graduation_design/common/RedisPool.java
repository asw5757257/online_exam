package com.zyw.online_exam.graduation_design.common;

import com.zyw.online_exam.graduation_design.controller.QuestionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 11:38
 */
public class RedisPool {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private static JedisPool pool;
    private static Integer maxTotal = 300;
    private static Integer maxIdle = 50;
    private static Integer minIdle = 5;


    private static boolean testOnBorrow = false;
    private static boolean testOnReturn = true;

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config,"127.0.0.1",6379,10000);
    }

    static {
        initPool();
    }

    public static Jedis getJrdis(){
        return pool.getResource();
    }

    public static void close(Jedis jedis){
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("return redis resource exception", e);
        }
    }
}
