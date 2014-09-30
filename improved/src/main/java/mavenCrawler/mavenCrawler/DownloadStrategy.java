package mavenCrawler.mavenCrawler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DownloadStrategy {

	/*
	 * static 变量 方便 访问 只实例化一次 对象之间共享
	 */
	// private static Parser parser = new Parser();
	// private static NodeList nodeList = null;
	// private static NodeFilter filter = null;
	
	private static Logger logger = LoggerFactory.getLogger(DownloadStrategy.class);

	public static void download_3p(StringBuilder url, UrlSave saver) {
		try {

			Parser parser = new Parser();
			NodeList nodeList = null;
			NodeFilter filter = null;

			System.out.println("download-3p:--->URL---->" + url.toString());
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("utf-8");

			// 保存的对象
			Page page = new Page();

			filter = new HasAttributeFilter("class", "articleBody");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					// System.out.println("toPlainText--->"+nameNode.toPlainTextString());
					// //获取或有节点的文本

					// 为了方便处理，这里还是使用 div 的 标签格式吧
					System.out.println("toHtml--->" + nameNode.toHtml()); // 打印出所有闭合节点
					page.setContent(new StringBuilder(nameNode.toHtml()));
				}
			}

			// 获取听力标题
			filter = new HasAttributeFilter("id", "title");
			nodeList = parser.extractAllNodesThatMatch(filter);
			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				System.out.println("toPlainText--->"
						+ nameNode.toPlainTextString()); // 获取或有节点的文本
				page.setTitle(new StringBuilder(nameNode.toPlainTextString()));
			}
			parser.reset();

			// 获取mp3音频
			filter = new HasAttributeFilter("id", "mp3");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				Node nodeName = nodeList.elementAt(0);

				filter = new NodeClassFilter(LinkTag.class);
				parser.setResource(nodeName.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if (nodeList == null || nodeList.size() <= 0) {
					System.out.println("这里为空");
					return;
				}
				String link = null;
				for (int i = 0; i < nodeList.size(); i++) {
					LinkTag node = (LinkTag) nodeList.elementAt(i);

					System.out.println("Link is--->"
							+ (link = node.extractLink()));

				}
				System.out.println("MP3  Download");
				// 下载mp3
				Thread th = new Thread(
						new MP3(
								"http://stream.51voa.com/201112/de_capua_report_on_h5n1.mp3",
								"G://eclipseVOA//crawler//downloader//"));
				th.start();
				page.setMp3(new StringBuilder(
						"G://eclipseVOA//crawler//downloader//"
								+ link.substring(link.lastIndexOf("/") + 1)));
				System.out.println("article---String--->" + url.toString());
				System.out.println(" G://eclipseVOA//crawler//downloader//"
						+ link.substring(link.lastIndexOf("/") + 1));
			}
			parser.reset();

			// 获取听力文本
			System.out.println("article---String--->" + url.toString());
			// parser = new Parser("http://www.51voa.com"+url.toString());
			// //这个失败了。。。。。
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("class", "articleBody");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					// System.out.println("toPlainText--->"+nameNode.toPlainTextString());
					// //获取或有节点的文本

					// 为了方便处理，这里还是使用 div 的 标签格式吧
					System.out.println("toHtml--->" + nameNode.toHtml()); // 打印出所有闭合节点
					page.setContent(new StringBuilder(nameNode.toHtml()));
				}
			}
			saver.save(page);

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download_3p2(StringBuilder url, UrlSave saver, String tag) {

		try {

			Parser parser = new Parser();
			NodeList nodeList = null;
			NodeFilter filter = null;

			System.out.println("download-3p:--->URL---->" + url.toString());
			
			// 获取听力文本
			System.out.println("article---String--->" + url.toString());
			// parser = new Parser("http://www.51voa.com"+url.toString());
			// //这个失败了。。。。。
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					// System.out.println("toPlainText--->"+nameNode.toPlainTextString());
					// //获取或有节点的文本
					NodeList nodes = nameNode.getChildren();

					if (nodes != null && nodes.size() > 0) {

						filter = new TagNameFilter(tag);
						nodes = nodes.extractAllNodesThatMatch(filter);
						// 获取p级 标签块
						if (nodes != null && nodes.size() > 0) {
							// 为了方便处理，这里还是使用 div 的 标签格式吧
							for (int j = 0; j < nodes.size(); j++) {
								nameNode = nodes.elementAt(j);
								System.out.println("article toHtml--->"
										+ nameNode.toHtml()); // 打印出所有闭合节点
							}
						}
					}
					// page.setContent(new StringBuilder(nameNode.toHtml()));
				}
			}
			// saver.save(page);

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download_3p3(StringBuilder url, UrlSave saver, String tag) {

		try {

			Parser parser = new Parser();
			NodeList nodeList = null;
			NodeFilter filter = null;
			System.out.println("download-3p:--->URL---->" + url.toString());
			// parser.setURL("http://www.51voa.com"+url.toString());
			// parser.setEncoding("utf-8");
			//
			// //保存的对象
			// Page page = new Page();
			//
			// filter = new HasAttributeFilter("class", "articleBody");
			// nodeList = parser.extractAllNodesThatMatch(filter);
			// if (nodeList != null && nodeList.size() > 0) {
			// for (int i = 0; i < nodeList.size(); i++) {
			// Node nameNode = nodeList.elementAt(i);
			// //System.out.println("toPlainText--->"+nameNode.toPlainTextString());
			// //获取或有节点的文本
			//
			// //为了方便处理，这里还是使用 div 的 标签格式吧
			// System.out.println("toHtml--->" + nameNode.toHtml()); //
			// 打印出所有闭合节点
			// page.setContent(new StringBuilder(nameNode.toHtml()));
			// }
			// }
			//
			//
			//
			// //获取听力标题
			// filter = new HasAttributeFilter("id", "title");
			// NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			// //需要写入到数据库
			// if (nodeList != null && nodeList.size() > 0) {
			// Node nameNode = nodeList.elementAt(0);
			// System.out.println("toPlainText--->"
			// + nameNode.toPlainTextString()); // 获取或有节点的文本
			// page.setTitle(new StringBuilder(nameNode.toPlainTextString()));
			// }
			// parser.reset();
			//
			//
			// //获取mp3音频
			// filter = new HasAttributeFilter("id","mp3");
			// nodeList = parser.extractAllNodesThatMatch(filter);
			// if( nodeList!=null && nodeList.size()>0)
			// {
			// Node nodeName = nodeList.elementAt(0);
			//
			// filter = new NodeClassFilter(LinkTag.class);
			// parser.setResource(nodeName.toHtml());
			// nodeList = parser.extractAllNodesThatMatch(filter);
			// if(nodeList==null || nodeList.size()<=0){
			// System.out.println("这里为空");
			// return;
			// }
			// String link = null;
			// for(int i=0;i<nodeList.size();i++)
			// {
			// LinkTag node = (LinkTag) nodeList.elementAt(i);
			//
			// System.out.println("Link is--->"+(link =node.extractLink()));
			//
			// }
			// System.out.println("MP3  Download");
			// //下载mp3
			// Thread th = new Thread(new
			// MP3("http://stream.51voa.com/201112/de_capua_report_on_h5n1.mp3","G://eclipseVOA//crawler//downloader//"));
			// th.start();
			// page.setMp3(new
			// StringBuilder("G://eclipseVOA//crawler//downloader//"+link.substring(link.lastIndexOf("/")+1)));
			// System.out.println("article---String--->"+url.toString());
			// System.out.println(" G://eclipseVOA//crawler//downloader//"+link.substring(link.lastIndexOf("/")+1));
			// }
			// parser.reset();
			//
			//
			// 获取听力文本
			System.out.println("article---String--->" + url.toString());
			// parser = new Parser("http://www.51voa.com"+url.toString());
			// //这个失败了。。。。。
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					// System.out.println("toPlainText--->"+nameNode.toPlainTextString());
					// //获取或有节点的文本
					NodeList nodes = nameNode.getChildren();

					if (nodes != null && nodes.size() > 0) {
						// 为了方便处理，这里还是使用 div 的 标签格式吧
						for (int j = 0; j < nodes.size(); j++) {
							nameNode = nodes.elementAt(j);
							System.out.println("article toHtml--->"
									+ nameNode.toHtml()); // 打印出所有闭合节点
						}

					}
					System.out.println("循环N次");
					// page.setContent(new StringBuilder(nameNode.toHtml()));
				}
			}
			// saver.save(page);

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download_title(StringBuilder url, UrlSave saver) {
		Parser parser = new Parser();
		NodeList nodeList = null;
		NodeFilter filter = null;

		filter = new HasAttributeFilter("id", "title");
		try {
			nodeList = parser.extractAllNodesThatMatch(filter);

			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				System.out.println("toPlainText--->"
						+ nameNode.toPlainTextString()); // 获取或有节点的文本
			}
			parser.reset();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void download_all(StringBuilder url, UrlSave saver) {
		try {

			Parser parser = new Parser();
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");

			Page page = new Page();

			// 获取听力标题
			NodeFilter filter = new HasAttributeFilter("id", "title");
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				System.out.println("toPlainText--->"
						+ nameNode.toPlainTextString()); // 获取或有节点的文本
				page.setTitle(new StringBuilder(nameNode.toPlainTextString()));
			}
			parser.reset();

			// 获取听力的音频
			filter = new HasAttributeFilter("id", "mp3");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				
				Node nodeName = nodeList.elementAt(0);
				filter = new NodeClassFilter(LinkTag.class);
				parser.setResource(nodeName.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if (nodeList == null || nodeList.size() <= 0) {
					//应该写入日志
					System.out.println("这里为空");
					return;
				}
				String link = null;
				for (int i = 0; i < nodeList.size(); i++) {
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					//打印日志
					System.out.println("Link is--->"
							+ (link = node.extractLink()));
				}
				System.out.println("MP3  Download");
				// 下载mp3
				
//				Thread th = new Thread(
//						new MP3(
//								link,
//								"G://eclipseVOA//crawler//downloader//"));
//				th.start();
				page.setMp3(new StringBuilder(
						"G://eclipseVOA//crawler//downloader//"
								+ link.substring(link.lastIndexOf("/") + 1)));
//				System.out.println("article---String--->" + url.toString());
//				System.out.println(" G://eclipseVOA//crawler//downloader//"
//						+ link.substring(link.lastIndexOf("/") + 1));
			}
			parser.reset();
			
			// 获取听力文本
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					System.out.println(nameNode.toHtml());
					page.setContent(new StringBuilder(nameNode.toHtml()));	
				}
			}
			parser.reset();

			saver.save(page);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void download_all(StringBuilder url, UrlSave saver,Frontier frontier) {
		try {

			Parser parser = new Parser();
			System.out.println("parser---->"+url.toString());
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");

			Page page = new Page();

			// 获取听力标题
			NodeFilter filter = new HasAttributeFilter("id", "title");
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				System.out.println("toPlainText--->"
						+ nameNode.toPlainTextString()); // 获取或有节点的文本
				page.setTitle(new StringBuilder(nameNode.toPlainTextString()));
			}
			parser.reset();

			// 获取听力的音频
			filter = new HasAttributeFilter("id", "mp3");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				
				Node nodeName = nodeList.elementAt(0);
				filter = new NodeClassFilter(LinkTag.class);
				parser.setResource(nodeName.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if (nodeList == null || nodeList.size() <= 0) {
					//应该写入日志
					System.out.println("这里为空");
					return;
				}
				String link = null;
				for (int i = 0; i < nodeList.size(); i++) {
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					//打印日志
					System.out.println("Link is--->"
							+ (link = node.extractLink()));
				}
				System.out.println("MP3  Download");
				// 下载mp3
				frontier.putUrl(link);
//				Thread th = new Thread(
//						new MP3(
//								link,
//								"G://eclipseVOA//crawler//downloader//"));
//				th.start();
				page.setMp3(new StringBuilder(
						Utils.name
								+ link.substring(link.lastIndexOf("/") + 1)));
//				System.out.println("article---String--->" + url.toString());
//				System.out.println(" G://eclipseVOA//crawler//downloader//"
//						+ link.substring(link.lastIndexOf("/") + 1));
			}
			parser.reset();
			
			// 获取听力文本
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				
					Node nameNode = nodeList.elementAt(0);
					System.out.println(nameNode.toHtml());
					
					page.setContent(new StringBuilder(nameNode.toHtml()));	
				
					String content = nameNode.toHtml();
					
					parser.reset();
					parser.setResource(content);
					filter = new HasAttributeFilter("class", "datetime");
					nodeList = parser.extractAllNodesThatMatch(filter);
					
					if (nodeList != null && nodeList.size() > 0) {

						nameNode = nodeList.elementAt(0);


						String time = nameNode.toPlainTextString();
						page.setDate(new StringBuilder(time));
					}
					
					
			}
			parser.reset();

			saver.save(page);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void download_all2(StringBuilder url, UrlSave saver,Frontier frontier) {
		try {

			Parser parser = new Parser();
			System.out.println("parser---->"+url.toString());
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");

			Page page = new Page();

			// 获取听力标题
			NodeFilter filter = new HasAttributeFilter("id", "title");
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				System.out.println("toPlainText--->"
						+ nameNode.toPlainTextString()); // 获取或有节点的文本
				page.setTitle(new StringBuilder(nameNode.toPlainTextString()));
			}
			parser.reset();

			// 获取听力的音频
			filter = new HasAttributeFilter("id", "mp3");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				
				Node nodeName = nodeList.elementAt(0);
				filter = new NodeClassFilter(LinkTag.class);
				parser.setResource(nodeName.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if (nodeList == null || nodeList.size() <= 0) {
					//应该写入日志
					System.out.println("这里为空");
					return;
				}
				String link = null;
				for (int i = 0; i < nodeList.size(); i++) {
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					//打印日志
					System.out.println("Link is--->"
							+ (link = node.extractLink()));
				}
				System.out.println("MP3  Download");
				// 下载mp3
				frontier.putUrl(link);
//				Thread th = new Thread(
//						new MP3(
//								link,
//								"G://eclipseVOA//crawler//downloader//"));
//				th.start();
				page.setMp3(new StringBuilder(link));
//				System.out.println("article---String--->" + url.toString());
//				System.out.println(" G://eclipseVOA//crawler//downloader//"
//						+ link.substring(link.lastIndexOf("/") + 1));
			}
			parser.reset();
			
			// 获取听力文本
			parser.setURL("http://www.51voa.com" + url.toString());
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					System.out.println(nameNode.toHtml());
					page.setContent(new StringBuilder(nameNode.toHtml()));	
				}
			}
			parser.reset();

			saver.save(page);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void Adding(StringBuilder url, UrlSave saver,Frontier frontier)
	{
	
		
	}
	
	public static void main(String[] args) {

		// download_3p(new StringBuilder("G://eclipseVOA//crawler//web.txt"),new
		// MongoSave());
		// download_3p2(new StringBuilder(
		// "/VOA_Standard_English/ecoliteracy-aug-58186.html"),
		// new MongoSave(), "P");
		// download_3p3(
		// new StringBuilder(
		// "/VOA_Standard_English/obama-to-visit-malaysia-amid-mh-criticisms-55971.html"),
		// new MongoSave(), "BR");
		download_all(
				new StringBuilder(
						"/VOA_Standard_English/obama-to-visit-malaysia-amid-mh-criticisms-55971.html"),
				new MongoSave());
	}
}
