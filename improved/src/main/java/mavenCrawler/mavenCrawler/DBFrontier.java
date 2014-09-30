package mavenCrawler.mavenCrawler;

import redis.clients.jedis.Jedis;

/*
 * 这里需要优化
 * 这里每次  get  put  都需要  获取  和 返还  jedis ,性能 上的 太差
 * 优化方案:
 * 	1.批处理共用 jedis ，一次批处理 保证 jedis 使用和 返回 一次、
 * 
 * 连接池的好处:
 * 				较少链接创建时间	（已经实例化好了）
 * 				简化编程模型 （只需要 获取 和 归还 链接  即可，不需要  自己 进行创建 等 ）
 * 				受控的资源访问(保证  资源使用的合理性)
 */
public class DBFrontier extends DBPool implements Frontier{

	
	
	public  DBFrontier(String key)
	{
		//init();
		queue = key;
	}
	
	//synchronized
	public  String getNext() {
		// TODO Auto-generated method stub
		return getQ();
	}

	

	public void putUrl(String url) {
		// TODO Auto-generated method stub
		putQ(url);
	}

	@Override
	protected void putQ(String val) {
		// TODO Auto-generated method stub
		Jedis jedis = getJedisObject();
		jedis.rpush(queue, val);
		recycleJedisOjbect(jedis);
	}

	@Override
	protected String getQ() {
		// TODO Auto-generated method stub
		Jedis jedis = getJedisObject();
		String builder =  jedis.lpop(queue);
		recycleJedisOjbect(jedis);
		System.out.println("getQ---->"+builder);
		return  builder;
	}
	


}
