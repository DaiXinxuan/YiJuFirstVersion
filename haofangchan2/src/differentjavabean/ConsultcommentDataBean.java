package differentjavabean;

public class ConsultcommentDataBean {
	String name,text,image,date,rank;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConsultcommentDataBean(String name, String text, String image,
			String date, String rank) {
		this.name = name;
		this.text = text;
		this.image = image;
		this.date = date;
		this.rank = rank;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}
