package org.xu.functions;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TestFun {

	
	public static void main(String[] args)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        //���ڵ��ַ�����ת��
        String today = df.format(new Date());
        
        System.out.println("��ǰʱ����:"+today);
	}
}
