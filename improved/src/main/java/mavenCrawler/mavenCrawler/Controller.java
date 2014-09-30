package mavenCrawler.mavenCrawler;

public class Controller {

	
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
		
		//初始化  一些 对象
		/*
		 * 数据都放在 redis的 Queue的 队列中
		 */
		Frontier  frontier  = new DBFrontier("Queue");
		Frontier  mp3_front = new DBFrontier("mp3");
		MongoSave saver = new MongoSave();
		StringBuilder startUrl = new StringBuilder("http://www.51voa.com/VOA_Standard_1.html");
		
		//五个线程执行
		mp3Download();
//		Read(frontier,startUrl);	
//		pageDownload(frontier);
			
	}
	
	private static void Read(Frontier pageQueue,StringBuilder startUrl) { 
		// TODO Auto-generated method stub
		
		UrlReader reader = new UrlReader(startUrl);
		reader.setFrontier(pageQueue);
		reader.startReader();
	}

	private static void pageDownload(Frontier Queue) {
		// TODO Auto-generated method stub
		Downloader down = new Downloader(){
			@Override
			public void download(StringBuilder url) {
				// TODO Auto-generated method stub
				
				DownloadStrategy.download_all(url,new MongoSave("voas","listening"),new DBFrontier("mp3"));
			}
			
		};
		down.setFrontier(Queue);
		down.run();
	}

	//管理mp3的下载
	private static void mp3Download() {
		// TODO Auto-generated method stub
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
		(new Thread(new Mp3Downloader(new DBFrontier("mp3")))).start();
	
	}
	
}
