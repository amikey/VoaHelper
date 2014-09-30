package mavenCrawler.mavenCrawler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class AddingDB {

	private static Logger logger = LoggerFactory.getLogger(AddingDB.class);

	private static String date_ori = "";
	private static void Read(Frontier pageQueue, StringBuilder startUrl) {
		// TODO Auto-generated method stub
		UrlAdding reader = new UrlAdding(startUrl);
		reader.setFrontier(pageQueue);
		reader.startReader();
	}

	private static void pageDownload(Frontier Queue) {
		// TODO Auto-generated method stub
		Downloader down = new Downloader() {
			@Override
			public void download(StringBuilder url) {
				// TODO Auto-generated method stub
				// 修改 系在策略
				// 应该可以 比较时间，看情况 停止运行
				//返回true  要下载
				if(compare(url))
				{
					DownloadStrategy.download_all(url, new MongoSave("test3","pages"),
							new DBFrontier("addingmp3"));
				}else{
//					throw new Exception("");
					try {
						new Thread().sleep(1000000000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.flag = true;
					
				}
			}

		};
		down.setFrontier(Queue);
		down.run();
	}

	// return true:表示 需要下载
	public static boolean compare(StringBuilder url) {
		
		try 
		{
			
			Parser parser = new Parser();
			System.out.println("parser---->" + url.toString());
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");

			Page page = new Page();

			NodeFilter filter = new HasAttributeFilter("class", "datetime");
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);

		
			// SPAN class=datetime
			if (nodeList != null && nodeList.size() > 0) {

				Node nameNode = nodeList.elementAt(0);

				System.out.println(nameNode.toHtml());

				String time_href_Val = nameNode.toPlainTextString();

				// mongo中最新的数据
				String origi = DateUtils.Convert_Date(date_ori);

				System.out.println("网页日期:"+time_href_Val);
				// 比较日期
				int val_ori = 0 ;
				int val_href = 0 ;
				
				
//					origi = DateUtils.Convert_Date(origi);
					time_href_Val = DateUtils.Convert_Date(time_href_Val);
					
					System.out.println("转换后的网页日期:"+time_href_Val);
					System.out.println("转换后的本地日期:"+origi);
					
					val_ori = Integer.parseInt(origi);
					val_href =  Integer.parseInt(time_href_Val);
				

				if ( val_ori < 	val_href ) {
					// 进行单个页面的下载
					return true;
				}
				System.out.println("这里结束啦");
			}
		} catch (Exception e) {
			logger.debug("donwload error:" + e.toString());
		}
		return false;
	}

	// 管理mp3的下载
	private static void mp3Download(Frontier frontier) {
		// TODO Auto-generated method stub
		(new Thread(new Mp3Downloader(frontier))).start();
		(new Thread(new Mp3Downloader(frontier))).start();
		
		
	}

	public static void main(String[] args) {
		// 循环
			// 读取起始地址的所有页面有效超来接
			Frontier frontier = new DBFrontier("AddingQueue");
			Frontier mp3_front = new DBFrontier("addingmp3");
			MongoSave saver = new MongoSave();
			StringBuilder startUrl = new StringBuilder(
					"http://www.51voa.com/VOA_Standard_1.html");

			//抽取最新的日期
			DB db  = MongoPool.getConn("voas");
			DBCollection pages =  db.getCollection("listening");
			//用一个并不存在的列  进行逆序，就是 按  date的 正序
			DBObject obj = 	pages.find().sort(new BasicDBObject("time",-1)).one();
			
			if(obj!=null){    
				date_ori =  (String) obj.get("date");
			}
			System.out.println("罪行日期: "+date_ori);
			
			// MP3的下载
//			mp3Download(mp3_front);
			// 存储有效连接到相关的队列中
//			Read(frontier, startUrl);

//			 从相关队列中 下载
			pageDownload(frontier);

			//链接数据库，得到最新日趋			
			
//			System.out.println("pageDownload end");
//				testCompare();
		
		
	}
	
	
	public static void testCompare(){
		
		
		System.out.println("比较函数结果:"+compare(new StringBuilder("/VOA_Standard_English/obama-islamic-state-strategy-draws-global-focus-58494.html")));		
		
	}
	
	public static  void testpageDownload()
	{
		
	}
}
