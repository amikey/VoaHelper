package org.xu.entrance;

import org.xu.DownloadMethods.DownloadStrategy;
import org.xu.DownloadMethods.Downloader;
import org.xu.DownloadMethods.MP3;
import org.xu.DownloadMethods.Mp3Downloader;
import org.xu.SaveMethods.MongoSave;
import org.xu.frontier.DBFrontier;
import org.xu.reader.UrlReader;

public class Controller {

		private  static void startDownn(){
			
			int down_thread_count=5;
			//下载的线程 启动
			
			
			for(int i=0;i<down_thread_count ;i++)
			{
				Downloader down =new Downloader() {
					
					@Override
					public void download(String url) {
						// TODO Auto-generated method stub
						DownloadStrategy.download_all(url, new MongoSave(), new DBFrontier("mp3s"),"G://eclipseVOA//crawler//downloader//");
					}
				};
				down.setFrontier(new DBFrontier("Queues"));
				(new Thread(down)).start();
			}
		}
		
		private static void startMP3()
		{
			/*
			 * 同时启动10个线程 存在易失性
			 * 一旦线程停，线程中残留的 几个 未完全下载的 url 会被 抛弃，
			 *new DBFrontier("mp3"),"G://eclipseVOA//crawler//downloader//"
			 */
			//下载MP3的线程数量
			int mp3_thread_count = 10;
			//下载mp3的县曾启动，一旦有数据（mp3URL，就进行下载）
			for(int i=0;i<mp3_thread_count;i++)
			{
				Downloader downer = new Downloader(new DBFrontier("mp3"),"G://eclipseVOA//crawler//downloader//"){
					@Override
					public void download(String url) {
						// TODO Auto-generated method stub
						MP3 mus = new MP3(url, "G://eclipseVOA//crawler//downloader//");
						mus.run();
					}
					
				};
				
				(new Thread(downer)).start();
			}
		}
		
		private static void startUrl(){
			
			//新文档的操作
//			(new Thread(new UrlReader(new StringBuilder("http://www.51voa.com/VOA_Standard_1.html")))).start();
			//旧文档的读取
			(new Thread(new UrlReader(new StringBuilder("http://www.51voa.com/VOA_Standard_1_archiver.html")))).start();
			
		}
		//用这个进行操作
		public static  void main(String[]args)
		{
			startMP3();
			startDownn();
			startUrl();
			
		}
}
