package org.xu.frontier;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class DBPool {

	/*
	 * pool 定义为 pool，共用 一个连接池 ，足够了
	 */
	private static JedisPool pool = null;

	protected String queue;
	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public DBPool (){
		
	}
	
	/*
	 * 
	 * 使用 static 方法，保证 JedisPool  一定会被实例化且被实例化一次，
	 * 当然也可以被 放在 构造函数里面+ synchronized(DBPool.class){ if(pool ==null).......}
	 * synchronized  保证  单线程 进入执行 
	 * 
	 */
	static{
		try {
			//避免多次初始化
					
					
				InputStream in = new BufferedInputStream(
						new FileInputStream(
								System.getProperty("user.dir")
										+ "//resources//redis.properties"));
				Properties props = new Properties();
				props.load(in);

				JedisPoolConfig config = new JedisPoolConfig();

				config.setMaxIdle(Integer.valueOf(props
						.getProperty("jedis.pool.maxIdle")));
				// config.setMaxTotal(500) //整个池最大值
				// 取代了这个  : config.setMaxActive(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
				//config.setMaxTotalPerKey(5) //每个key的最大
				
				config.setBlockWhenExhausted(true);	//资源耗尽 进行堵塞
			
				config.setMaxWaitMillis(Long.valueOf(props
						.getProperty("jedis.pool.maxWait")));

				config.setTestOnBorrow(Boolean.valueOf(props
						.getProperty("jedis.pool.testOnBorrow")));

				config.setTestOnReturn(Boolean.valueOf(props
						.getProperty("jedis.pool.testOnReturn")));

				pool = new JedisPool(config, props.getProperty("redis.ip"),
						Integer.valueOf(props.getProperty("redis.port")));
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 获得jedis对象 */
	public static Jedis getJedisObject() {

		return pool.getResource();

	}

	/** 归还jedis对象 */
	public static void recycleJedisOjbect(Jedis jedis) {

		pool.returnResource(jedis);

	}

	//从队列中  取出
	protected abstract void putQ(String val);
	
	//推进 队列
	protected abstract String  getQ();
}
