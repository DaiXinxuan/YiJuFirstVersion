package differentjavabean;

public class ChatItemBean {
	String text,time,head;
	boolean isComMeg = true;

	public ChatItemBean(String text, String time, String head , boolean isComMeg) {
		this.text = text;
		this.time = time;
		this.head = head;
		this.isComMeg=isComMeg;
	}

	public ChatItemBean() {
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

}
