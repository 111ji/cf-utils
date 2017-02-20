package cn.careerforce.util;

import cn.careerforce.config.Configuration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Redis Client 工具类
 *
 * @author yangdh <br/>
 * @year: 2016
 * @since 0.0.1 <br/>
 */
public class JedisUtil
{
    /**
     * Redis 集群入口地址
     */
    private static final String REDIS_IP = "redis.server";

    /**
     * Redis 端口
     */
    private static final String REDIS_PORT = "redis.port";

    /**
     * Redis 连接池最多连接数
     */
    private static final String REDIS_MAXTOTAL = "redis.maxtotal";

    /**
     * Redis 连接池最多空闲连接数
     */
    private static final String REDIS_MAXIDLE = "redis.maxidle";

    /**
     * Redis 连接池最小空闲连接数
     */
    private static final String REDIS_MINIDLE = "redis.minidle";


    /**
     * Redis 集群Master-Cluster
     */
    private static JedisCluster jedisCluster;

    static
    {
        if (null == jedisCluster)
        {
            // Redis 集群节点配置
            Set<HostAndPort> jedisClusterClusterNodes = new HashSet<HostAndPort>();
            jedisClusterClusterNodes.add(new HostAndPort(Configuration.getValue(REDIS_IP, "221.122.113.132"), Configuration.getValue(REDIS_PORT, 6379)));

            // Redis 连接池配置
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();

            // Redis Pool MaxTotal, MaxIdle, MinIdle
            config.setMaxTotal(Configuration.getValue(REDIS_MAXTOTAL, 200));
            config.setMaxIdle(Configuration.getValue(REDIS_MAXIDLE, 10));
            config.setMinIdle(Configuration.getValue(REDIS_MINIDLE, 0));

            // 初始化
            try
            {
                jedisCluster = new JedisCluster(jedisClusterClusterNodes, config);
            }
            catch (Exception ignored)
            {

            }
        }
    }


    private static JedisPool pool = null;
    static
    {
        String redisServer = Configuration.getValue("redis.server", "221.122.113.132");
        try
        {
            pool = new JedisPool(new JedisPoolConfig(), redisServer);
        }
        catch (Exception ignored)
        {

        }
    }


    public static boolean setRedisObject(String key, String value, Integer expire)
    {
        try
        {
            jedisCluster.set(key, value);
            if (expire != null && expire != -1)
                jedisCluster.expire(key, expire);

            return true;
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                jedis.set(key, value);
                if (expire != null && expire != -1)
                    jedis.expire(key, expire);

                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
    }

    public static String getRedisObject(String key)
    {

        try
        {
            return jedisCluster.get(key);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.get(key);
            }
            catch (Exception ex)
            {
                return null;
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
    }

    public static boolean removeRedisObject(String key)
    {

        try
        {
            jedisCluster.del(key);
            return true;
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                jedis.del(key);
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
    }


    /**
     * 创建计数器
     *
     * @param key    键
     * @param expire {expire}秒后过期
     * @return 数值
     */
    public static Long incrRedisObject(String key, Integer expire)
    {

        try
        {
            Long res = jedisCluster.incr(key);
            if (expire != null && expire != -1)
                jedisCluster.expire(key, expire);

            return res;
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                Long res = jedis.incr(key);
                if (expire != null && expire != -1)
                    jedis.expire(key, expire);

                return res;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return -1L;
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
    }


    // >>>>>>>>>>>>>>>>>>>>>>> List redis存储 >>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 从 List 尾部添加一个元素（如序列不存在，则先创建，如已存在同名Key而非序列，则返回错误）
     *
     * @param key   List名称
     * @param value 值
     * @return Long
     * @category List（列表）
     */
    public static Long rpush(String key, String value)
    {
        try
        {
            return jedisCluster.rpush(key, value);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.rpush(key, value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * List长度
     *
     * @param key List名称
     * @return Long List长度
     * @category List（列表）
     */
    public static Long llen(String key)
    {

        try
        {
            return jedisCluster.llen(key);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.llen(key);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }

        return null;
    }

    /**
     * 获得List名称为key的从start到end范围内的值
     *
     * @param key   List名称
     * @param start 起始位置
     * @param end   结束位置
     * @return List<String> 值列表
     */
    public static List<String> lrange(String key, Long start, Long end)
    {

        try
        {
            return jedisCluster.lrange(key, start, end);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.lrange(key, start, end);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 获得List名称为key的第一个元素，同时移出List
     *
     * @param key List名称
     * @return String List顶部第一个元素
     * @category List（列表）
     */
    public static String lpop(String key)
    {

        try
        {
            return jedisCluster.lpop(key);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.lpop(key);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 删除List名称某个范围（start to end ）之外的数据
     *
     * @param key   List名称
     * @param start 开始位置
     * @param end   结束位置
     * @category List（列表）
     */
    public static boolean ltrim(String key, Long start, Long end)
    {

        try
        {
            jedisCluster.ltrim(key, start, end);
            return true;
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                jedis.ltrim(key, start, end);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }
        return false;
    }

    /**
     * 替换List中指定位置的元素，List不存在或是index超出范围，返回false
     *
     * @param key   List名称
     * @param index 指定位置（0开始）
     * @param value 替换值
     * @return boolean
     * @category List（列表）
     */
    public static boolean lset(String key, Long index, String value)
    {

        try
        {
            jedisCluster.lset(key, index, value);
            return true;
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                jedis.lset(key, index, value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }

        return false;
    }

    /**
     * 返回List中指定位置的元素，index超出范围，返回null
     *
     * @param key   List名称
     * @param index 指定位置
     * @return String
     * @category List（列表）
     */
    public static String lindex(String key, Long index)
    {

        try
        {
            return jedisCluster.lindex(key, index);
        }
        catch (Exception eex)
        {
            Jedis jedis = pool.getResource();
            try
            {
                return jedis.lindex(key, index);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // jedis 实例使用完毕，关闭连接
                if (jedis != null)
                {
                    jedis.close();
                }
            }
        }

        return null;
    }

    public static void main(String args[]) throws Exception
    {
        JedisUtil.setRedisObject("1", "12", 60);
    }

}
