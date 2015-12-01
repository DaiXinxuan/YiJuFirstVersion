package differentjavabean;

public class HouseBean {
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getsArray() {
		return sArray;
	}
	public void setsArray(String[] sArray) {
		this.sArray = sArray;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	String title,sArray[]=new String[6],imageURL;
	public HouseBean(String title,String sArray[],String imageURL){
		this.title=title;
		this.sArray = sArray;
		this.imageURL = imageURL;
	}
}
