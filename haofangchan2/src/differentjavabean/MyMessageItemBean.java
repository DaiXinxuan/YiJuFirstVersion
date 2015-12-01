package differentjavabean;

public class MyMessageItemBean {
	String name,detail,time,img;

	public MyMessageItemBean(String name, String detail, String time, String img) {
		this.name = name;
		this.detail = detail;
		this.time = time;
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
