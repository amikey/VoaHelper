package mavenCrawler.mavenCrawler;


import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MP3 implements Runnable{

	private static final int BUFFER_SIZE = 4096;
    private String destUrl;
    private String fileName;
    
    /*
     * destUrl   获取mp3 的  源地址
     * fileName  
     */
    public MP3(String destUrl,String fileName)
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

        // 建立链接
        url = new URL(destUrl);
        httpconn = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpconn.connect();
        // 获取网络输入流
        bis = new BufferedInputStream(httpconn.getInputStream());
        // 建立文件
        fos = new FileOutputStream(fileName);

        System.out.println("正在获取链接[" + destUrl + "]的内容\n将其保存为文件[" + fileName
                + "]");
        

        // 保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);
        fos.close();
        bis.close();
        httpconn.disconnect();
    }
 
    public static void main(String[]args)
    {
    	MP3  mp3 = new MP3("http://stream.51voa.com/201409/ukrainian-leader-to-seek-greater-us-support-on-visit.mp3",Utils.name);
    	mp3.run();
    	
    }
}
