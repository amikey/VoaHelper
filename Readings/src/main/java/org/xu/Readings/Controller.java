package org.xu.Readings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Controller {

	private static Logger logger = LoggerFactory.getLogger( Controller.class);
	
	public static void StartUri()
	{
		//应该 也能 设定  mongo数据库的集合名称
		UrlReader reader =new UrlReader(new StringBuilder("http://www.adreep.cn/wz/"));
		reader.setFrontier(new DBFrontier("Readings"));
//		reader.startReader();
		new Thread(reader).start();
	}
	/*
	 * urlreader  解码 不是很成功
	 * 
	 * 需要理解  runnable 和  thread  方面的   异同
	 * 
	 * start 方法  可以 多线程 交叉运行
	 * run  方法  则  各自为政
	 * 
	 * Runnable  避免了  thread  的 单继承的 局限，同时  有利于 资源的 共享（private  int  ticket 买票的例子）
	 */
	public static void main(String[]args)
	{
		
		
//		startDownload();

		System.out.println("开始下载啦");
		
//		StartUri();
		
		
		
	}
	private static void startDownload() {
		// TODO Auto-generated method stub
		
		Downloader down = new Downloader(new DBFrontier("Readings")," "){
			
			@Override
			public void download(String Url) {
				// TODO Auto-generated method stub
				//下载  url 中的文本内容 ，需要解析 
				logger.debug("url---->"+Url);
				String result = ParserStrategy.getP2(Url);
				
				System.out.println("url====="+Url);
				if(result!=null)
				{
					logger.debug(result);
					//readins is the collectionName
					SaveStrategy.dbSave("readings").update(Url, result);
					
				}else{
					//读取数据失败，放回数据，准备  后面  程序员的 检查
					this.frontier.putUrl(Url);
					logger.error("url 解析的 p节点  文本信息有问题，result = null");
				}
				
				
				return ;
				
			}
			
		};
		
		
		
		
		
		new Thread(down).start();
		new Thread(down).start();
		new Thread(down).start();
		new Thread(down).start();
		new Thread(down).start();
		new Thread(down).start();
		new Thread(down).start();
	}
}
