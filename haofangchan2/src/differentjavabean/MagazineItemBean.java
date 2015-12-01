package differentjavabean;

public class MagazineItemBean {
	String title,text,img;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public MagazineItemBean(String title, String text, String img) {
		this.title = title;
		this.text = text;
		this.img = img;
	}
	
}
