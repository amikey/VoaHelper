package org.xu.pojo;

public class VOAbs {
	
	private String  title;
	private String  publish_time;
	private String  collect_time;
	private String intro;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(String collect_time) {
		this.collect_time = collect_time;
	}
	@Override
	public String toString() {
		return "VOAbs [title=" + title + ", publish_time=" + publish_time
				+ ", collect_time=" + collect_time + ", intro=" + intro + "]";
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public VOAbs(String title, String publish_time, String collect_time,
			String intro) {
		super();
		this.title = title;
		this.publish_time = publish_time;
		this.collect_time = collect_time;
		this.intro = intro;
	}
	
	

}
