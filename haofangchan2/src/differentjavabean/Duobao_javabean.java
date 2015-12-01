package differentjavabean;

public class Duobao_javabean {
	String imageURL,name,content,time;
	public Duobao_javabean(String imageURL,String name,String content,String time){
		this.imageURL = imageURL;
		this.name=name;
		this.content=content;
		this.time=time;
	}
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
