package org.xu.Utils;

import java.util.HashMap;
import java.util.Map;

public class DateUtils {

	static Map<String,String>  MONS = new HashMap<String,String>();
	
	static{
		MONS.put("Jan", "01");MONS.put("Feb", "02");MONS.put("Mar", "03");MONS.put("Apr", "04");
		MONS.put("May", "05");MONS.put("Jun", "06");MONS.put("Jul", "07");MONS.put("Aug", "08");
		MONS.put("Sep", "09");MONS.put("Oct", "10");MONS.put("Nov", "11");MONS.put("Dec", "12");
	}
	
	/*
	 * Septemper 20,2014   转换为      20140920
	 * 2014-09-20  转换为      20140920
	 */
	public static String Convert_Date(String origi)
	{

		if(origi.contains("-")){
			String[]  Dt_ars = origi.split("-");
		 
			String year = Dt_ars[0];
			
			String mon = Dt_ars[1] ;
			String dt = Dt_ars[2];
			
			
			return year+(mon.length()==1?"0"+mon:mon)+(dt.length()==1?"0"+dt:dt);
		
		}else{
			String[]  Dt_ars = origi.split(" ");
			String mon = Dt_ars[0];
			
			String[] Dt_ars_yd = Dt_ars[1].split(",");
			String dt = Dt_ars_yd[0] ;
			String year = Dt_ars_yd[1];
			
			
			String rank = MONS.get(mon.trim().substring(0, 3));
			
			
			return  year+rank+(dt.length()==1?"0"+dt:dt);
			
		}
	}
		/*
		 * 转换日期格式
		 * 	Septemper 20,2014   转换为      2014-09-20 
		 */
		public static String Convert_Date_line(String origi)
		{
			if(origi==null){
				return null;
			}
			
			if(!origi.contains("-")&&!origi.contains("/")){
				
				
				String[]  Dt_ars = origi.split(",");
				String  year  = Dt_ars[1].trim();
				
				String[] Dt_ars_yd = Dt_ars[0].trim().split(" ");
				
				System.out.println("dt_ars_ye = "+Dt_ars_yd[0]);
				String mon= Dt_ars_yd[0] ;
				String dt = Dt_ars_yd[1];
				
				
				String rank = MONS.get(mon.substring(0, 3) );
				
				
//				String[]  Dt_ars = origi.split(" ");
//				String mon = Dt_ars[0];
//				
//				String[] Dt_ars_yd = Dt_ars[1].split(",");
//				String dt = Dt_ars_yd[0] ;
//				String year = Dt_ars_yd[1];
//				
//				
//				String rank = MONS.get(mon.substring(0, 3) );
				
				
				return  year+"-"+rank+"-"+(dt.length()==1?"0"+dt:dt);
			}else{
				return origi;
			}
			
		}
}
