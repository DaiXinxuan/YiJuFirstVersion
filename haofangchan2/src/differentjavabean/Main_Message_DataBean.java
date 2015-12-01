package differentjavabean;

public class Main_Message_DataBean {
	String text;
	String personNumber;
	boolean isInterested;
	public Main_Message_DataBean(String text,String personNumber,boolean isInterested){
		this.text = text;
		this.personNumber = personNumber;
		this.isInterested = isInterested;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	public boolean isInterested() {
		return isInterested;
	}
	public void setInterested(boolean isInterested) {
		this.isInterested = isInterested;
	}
}
