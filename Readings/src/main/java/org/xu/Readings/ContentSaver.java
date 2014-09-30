package org.xu.Readings;

//保存网页内容的接口
public interface ContentSaver {

	public void save(Object obj);
	
	public void  update(Object origi,Object modi);
}
