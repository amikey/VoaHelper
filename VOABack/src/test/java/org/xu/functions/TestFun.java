package org.xu.functions;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TestFun {

	
	public static void main(String[] args)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        //日期到字符串的转换
        String today = df.format(new Date());
        
        System.out.println("当前时间是:"+today);
	}
}
