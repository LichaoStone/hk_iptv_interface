package com.hicon.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;

/**
 * Redis工具类 工具类里面对外提供的方法和变量多为:静态
 *
 */
 public class RedisUtil {
        private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

        private static Jedis jedis;               // 连接
        private static String host;               // 主机号,默认是本地
        private static int port;                  // 端口号
        private static String password ;          // redis密码 认证
        private static int timeout = 10000;       // 设置默认超时时间10s


        private static JedisPool jedisPool;       // redis池
        private static JedisPoolConfig poolConfig = new JedisPoolConfig();


        /**
         * 初始化redis
         */
       static {
            initRedis();
        }

        /**
         * 默认构造方法重新,并且是私有的《所以不能够实例化
         */
        private RedisUtil() {

        }

        /**
         * 初始化redis操作
         */
        private static void initRedis() {

            //最大活跃数
            poolConfig.setMaxTotal(Integer.parseInt(PropertiesUtil.getValue("redis_max_active")));
            // 控制一个jedisPool最多有多少个状态为idle(空闲的)的jedis实例
            poolConfig.setMaxIdle(Integer.parseInt(PropertiesUtil.getValue("redis_max_idle")));
            // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
            poolConfig.setMaxWaitMillis(Integer.parseInt(PropertiesUtil.getValue("redis_max_wait")));
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
            poolConfig.setTestOnBorrow(Boolean.parseBoolean(PropertiesUtil.getValue("redis_test_on_borrow")));


            String proHost = PropertiesUtil.getValue("redis.host");
            String proPort = PropertiesUtil.getValue("redis.port");
            String proPassword = PropertiesUtil.getValue("redis.password");

            // 设置变量
            host = proHost;
            port = Integer.parseInt(proPort);
            password = proPassword;
        }

        /**
         * 获取jedis实例
         *
         * @return
         */
        public static Jedis getJedis() {
            jedisPool = new JedisPool(poolConfig, host, port, timeout);
            return jedisPool.getResource();
        }


        /*
         * 将String设置到redis中
         * @param key
         * @param value
         */
        public static void setString(String key, String value) {
            getJedis().set(key, value);
        }
        /**
         * 通过键获取String
         * @param key
         * @return
         */
        public static String getString(String key){
        	Jedis jedis = getJedis();
        	try {
				return jedis.get(key);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				 returnResource(jedis);
			}
            return null;
        }

        /**
         * 将List数组存放进redis中
         * @param key
         * @param list
         * @param isHead
         */
        public static void setList(String key,List<String> list,boolean isHead){
        String[] arr = new String[list.size()];
        list.toArray(arr);
        //判断是否从 头开始插入
        if(isHead){
            getJedis().lpush(key, arr);
        }else{
            getJedis().rpush(key, arr);
        }
        }

        /**
         * 获取redis中数据(对于对象的list方法1：把List转换成JSON，存储到Redis，取出来的时候，再把JSON转换成List)
         * @param key 键
         * @param start 开始
         * @param end 结束
         * @return
         */
        public static List<String> getList(String key,long start,long end){
            return getJedis().lrange(key, start, end);
        }

        /**
         * 
         * @Title: ServantObject   
         * @Description: 存储对象，序列化  
         * @param: @param key
         * @param: @param object      
         * @return: void      
         * @throws 
         * @author: banbu
         */
        public static void setObject(byte[] key ,Object object) {
        	byte[] objectByte = SerializeUtil.serialize(object);
        	try {
        		Jedis jedis = getJedis();
            	jedis.set(key, objectByte);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				returnResource(jedis);
			}
        	
        }
        /**
         * 
         * @Title: getObject   
         * @Description: 获得对象 
         * @param: @param key
         * @param: @return      
         * @return: Object      
         * @throws 
         * @author: banbu
         */
        public static Object getObject(byte[] key) {
        	Jedis jedis = getJedis() ;
        	byte[] objectByte = jedis.get(key);
        	try {
				return SerializeUtil.deserialize(objectByte);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				 returnResource(jedis);
			}
        	return null ;
        }
        /**
         * 
         * @Title: deleteObjectByKey   
         * @Description: 删除对象  
         * @param: @param key      
         * @return: void      
         * @throws 
         * @author: banbu
         */
        public static void deleteObjectByKey(byte[] key) {
        	try {
        		Jedis jedis = getJedis() ;
            	jedis.del(key);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				returnResource(jedis);
			}
        	
        }
        /**
         * 
         * @Title: deleteObjectByKey   
         * @Description: 删除对象 
         * @param: @param key      
         * @return: void      
         * @throws 
         * @author: banbu
         */
        public static void deleteObjectByKey(String key) {
        	try {
        		Jedis jedis = getJedis() ;
            	jedis.del(key);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				returnResource(jedis);
			}
        	
        }
        /**
         * 
         * @Title: exists   
         * @Description: 检查key是否存在 
         * @param: @param key
         * @param: @return      
         * @return: Boolean      
         * @throws 
         * @author: banbu
         */
        public static Boolean exists(String key) {
        	boolean flag = false ;
        	try {
        		Jedis jedis = getJedis() ;
            	flag = jedis.exists(key);
            	return flag ;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				returnResource(jedis);
			}
        	return flag ;
        }
        /**
         * 
         * @Title: exists   
         * @Description: 检查key是否存在  
         * @param: @param key
         * @param: @return      
         * @return: Boolean      
         * @throws 
         * @author: banbu
         */
        public static Boolean exists(byte[] key) {
        	boolean flag = false ;
        	try {
        		Jedis jedis = getJedis() ;
            	flag = jedis.exists(key);
            	return flag ;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				returnResource(jedis);
			}
        	return flag ;
        }
    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedis.close();
        }
    }

    /**
     * <p>
     * 通过key同时设置 hash的多个field
     * </p>
     *
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public static String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hmset(key, hash);
            
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    public static void expire(String key,int seconds) {
    	Jedis jedis = null;
        try {
            jedis = getJedis();
            // 执行
            jedis.expire(key, seconds);
            
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
    }
    /**
     * <p>
     * 通过key 和 field 获取指定的 value
     * </p>
     *
     * @param key
     * @param field
     * @return 没有返回null
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hget(key, field);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
     * </p>
     *
     * @param key
     * @param fields
     *            可以使 一个String 也可以是 String数组
     * @return
     */
    public static List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hmget(key, fields);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key给指定的field的value加上给定的值
     * </p>
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hincrBy(key, field, value);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 设置key value并制定这个键值的有效期
     * </p>
     *
     * @param key
     * @param value
     * @param seconds
     *            单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public static String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.setex(key, seconds, value);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key和field判断是否有指定的value存在
     * </p>
     *
     * @param key
     * @param field
     * @return
     */
    public static Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean res = false;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hexists(key, field);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key
     * @return 加值后的结果
     */
    public static Long incr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.incr(key);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }


    /**
     * <p>
     * 向redis存入key和value,并释放连接资源
     * </p>
     * <p>
     * 如果key已经存在 则覆盖
     * </p>
     *
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            // 执行
            String result = jedis.set(key, value);
            return result;
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * <p>
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     * </p>
     *
     * @param key
     * @param integer
     * @return
     */
    public static Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.incrBy(key, integer);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }


    /**
     * <p>
     * 通过key获取所有的field和value
     * </p>
     *
     * @param key
     * @return
     */
    public static Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hgetAll(key);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }


    /**
     * <p>
     * 通过key给field设置指定的值,如果key不存在,则先创建
     * </p>
     *
     * @param key
     * @param field
     *            字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public static Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.hset(key, field, value);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
            throw new JedisConnectionException("redis连接失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }
    public static String setex(byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            // 执行
            res = jedis.setex(key, seconds, value);
            
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return res;
    }
    
    public static long setnx(String key, String value) {
        Jedis jedis = null;
        long count = 0;
        try {
            jedis = getJedis();
            // 执行
           count = jedis.setnx(key, value);
        } catch (JedisConnectionException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return count;
    }

    public static void main(String[] args) {
            System.out.println(RedisUtil.getString("test"));
        }
 }
