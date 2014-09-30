package org.xu.functions;

import org.junit.Test;
import org.xu.Utils.DateUtils;

public class TestDate {

	@Test
	public void testComp()
	{
		
		String ago = "2013-12-12";
		String now =  "2012-01-08";
		ago = ago.replace('-', '0');
		now = now.replace('-', '0');
	
		System.out.println("ago  == "+ago);
		System.out.println("noew == "+now);
		if(Integer.parseInt(ago)>Integer.parseInt(now))
		{
			System.out.println("OK");
		}
	}
}
