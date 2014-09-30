package org.xu.pojo;

public abstract class ListeningAbs {

	@Override
	public String toString() {
		return "Listening [mo3=" + mo3 + ", content=" + content + ", title="
				+ title + ", time=" + time + "]";
	}
	protected String mo3;
	protected String content;
	protected String title;
	protected String time;
	
	
	public String getMo3() {
		return mo3;
	}
	
	public void setMo3(String mo3) {
		this.mo3 = mo3;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
