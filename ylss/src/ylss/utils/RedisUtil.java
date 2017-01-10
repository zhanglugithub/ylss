package ylss.utils;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "123.56.115.198";

	// Redis的端口号
	private static int PORT = 6379;

	// 访问密码
	private static String AUTH = "ylss_redis";

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static HashMap<String, Object> setLat(String phoneNo,
			Double latitude, Double longitude) {
		Jedis jedis = RedisUtil.getJedis();
		try {
			Map<String, String> weiZhi = new HashMap<String, String>();
			weiZhi.put("latitude", latitude + "");
			weiZhi.put("longitude", longitude + "");
			jedis.hmset(phoneNo + "", weiZhi);
			RedisUtil.returnResource(jedis);
			return resultMap.createResult(1, true);
		} catch (Exception e) {
			e.printStackTrace();
			RedisUtil.returnResource(jedis);
			return resultMap.createResult(0, false);
		}
	}

	public static HashMap<String, Object> getLat(String phoneNo) {
		Jedis jedis = RedisUtil.getJedis();
		try {
			Map<String, String> mget = jedis.hgetAll(phoneNo);
			RedisUtil.returnResource(jedis);
			return resultMap.createResult(1, mget);
		} catch (Exception e) {
			e.printStackTrace();
			RedisUtil.returnResource(jedis);
			return resultMap.createResult(0, false);
		}
	}
}
