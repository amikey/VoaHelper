package mavenCrawler.mavenCrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Downloader implements Runnable{

	private String directory =" G://eclipseVOA//MP3s//";  
	//使用桥接模型，定义与实现 相分离
	Frontier  frontier = null;

	//停止操作的信号量
	//true  表示停止
	boolean flag = false;
	
	
	
	private static  Logger logger = 
			LoggerFactory.getLogger( Downloader.class);
	
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Frontier getFrontier() {
		return frontier;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}
	
	
	public abstract void download(StringBuilder builder);

	public void run() {
		
		
		StringBuilder url = null;
		while(true)
		{
			//检测标识符,判断是否继续下载
			if(flag){
				break;
			}
			//获取可用的url 进行下载
			String  str  = frontier. getNext();
 			System.out.println("URL---->"+str);
			if(str!=null)
			{
				url = new StringBuilder(str);
				try{
					
					download(url);
					
				}catch(Exception e){
					logger.debug("download  exception"+e.toString());
				}

			}else{
				//轮询策略  查看 数据库 数据是否存在  ，  应该 设置一定的 时间长度，
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
