package org.xu.DownloadMethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.frontier.Frontier;



public class Mp3Downloader implements Runnable {

	//存储mp3来源地址的队列
	private Frontier frontier = null;
	
	//MP3目录
	private String direcory = "G://eclipseVOA//crawler//downloader//";
	
	//日志
	public static Logger logger = LoggerFactory.getLogger(Mp3Downloader.class);
	
	public String getDirecory() {
		return direcory;
	}

	public void setDirecory(String direcory) {
		this.direcory = direcory;
	}

	public Frontier getFrontier() {
		return frontier;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	public Mp3Downloader() {

	}

	public Mp3Downloader(Frontier frontier) {
		this.frontier = frontier;
	}

	public Mp3Downloader(Frontier frontier,String directory) {
		this.frontier = frontier;
		this.direcory = directory;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			String str = frontier.getNext();
			logger.info("需要下载的mp3 URL:" + str);
			if (str != null) {
				MP3 mus = new MP3(str, direcory);
				mus.run();
			} else {
				// 轮询策略 查看 数据库 数据是否存在 ， 应该 设置一定的 时间长度，
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("数据库为空，等待轮询,Thread 失败:"+e.toString());	
				}
			}
		}

	}
}
