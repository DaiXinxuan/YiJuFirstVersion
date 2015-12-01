package differentjavabean;

import org.litepal.crud.DataSupport;

public class LatestNewsTable extends DataSupport{
	
private String time;
private String photopath;
private String content;
private String name;
private String activityid;
private Boolean isRead;
private int type;
private String html;
private String proid;

public String getProid() {
	return proid;
}

public void setProid(String proid) {
	this.proid = proid;
}

public String getHtml() {
	return html;
}

public void setHtml(String html) {
	this.html = html;
}

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}

public Boolean getIsRead() {
	return isRead;
}

public void setIsRead(Boolean isRead) {
	this.isRead = isRead;
}

public String getActivityid() {
	return activityid;
}

public void setActivityid(String activityid) {
	this.activityid = activityid;
}

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

public String getPhotopath() {
	return photopath;
}

public void setPhotopath(String photopath) {
	this.photopath = photopath;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
}
