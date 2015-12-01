package differentjavabean;

public class LoginBean {
	private boolean state;
	private String payid;
	private String hxpassword;
	private String hxusername;
	
	
	public String getHxusername() {
		return hxusername;
	}
	public void setHxusername(String hxusername) {
		this.hxusername = hxusername;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getPayid() {
		return payid;
	}
	public void setPayid(String payid) {
		this.payid = payid;
	}
	public String getHxpassword() {
		return hxpassword;
	}
	public void setHxpassword(String hxpassword) {
		this.hxpassword = hxpassword;
	}
}
