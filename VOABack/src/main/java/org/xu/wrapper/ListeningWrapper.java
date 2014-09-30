package org.xu.wrapper;

import org.xu.pojo.ListeningAbs;

public class ListeningWrapper extends ListeningAbs {

	public ListeningWrapper(ListeningAbs abs){
		this.mo3 = abs.getMo3().substring(abs.getMo3().lastIndexOf("/")+1);
		this.time = abs.getTime();
		this.content = abs.getContent();
		this.title = abs.getTitle();
	}
	
	
}
