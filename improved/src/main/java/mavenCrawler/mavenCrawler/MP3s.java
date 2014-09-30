package mavenCrawler.mavenCrawler;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MP3s  implements Runnable{
	
	

	private static final int BUFFER_SIZE = 4096;
    private String destUrl;
    private String fileName;
    
    /*
     * destUrl   获取mp3 的  源地址
     * fileName  
     */
    public MP3s(String destUrl,String fileName)
    {
        this.destUrl = destUrl;
    	int i = destUrl.lastIndexOf("/");
    	this.fileName = fileName + destUrl.substring(i+1);
    }
    
    public void run() {
        try {
            saveToFile(destUrl,fileName);
            System.out.println("文件："+destUrl+"下载完成，保存为"+fileName);
        } catch (IOException e) {
            System.out.println("文件下载失败，信息："+e.getMessage());
        }
    }

    /** *//**
     * 将网络文件保存为本地文件的方法
     * @param destUrl
     * @param fileName
     * @throws IOException
     */
    public void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpconn = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;

 
        // 获取网络输入流
        bis = new BufferedInputStream(WebClient.url2sTream(new StringBuilder(destUrl)));
        // 建立文件
        fos = new FileOutputStream(fileName);

        System.out.println("正在获取链接[" + destUrl + "]的内容\n将其保存为文件[" + fileName
                + "]");
        

        // 保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);
        fos.close();
        bis.close();
        
    }
 
    public static void main(String[]args)
    {
    	MP3s  mp3 = new MP3s("http://stream.51voa.com/201409/dehydration-is-top-killer-of-southern-arizona-migrants.mp3",Utils.name);
    	mp3.run();
    	
    }

}
