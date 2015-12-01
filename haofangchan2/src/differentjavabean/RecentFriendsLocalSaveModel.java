package differentjavabean;

import org.litepal.crud.DataSupport;

public class RecentFriendsLocalSaveModel extends DataSupport{
 
	private String payuserid;
	private String headphotopath;
	private String nickname;
	private String hxusername;
	private int isVip;
	private String myHeadPhoto;
	
	public String getPayuserid() {
		return payuserid;
	}
	public void setPayuserid(String payuserid) {
		this.payuserid = payuserid;
	}
	public String getHeadphotopath() {
		return headphotopath;
	}
	public void setHeadphotopath(String headphotopath) {
		this.headphotopath = headphotopath;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHxusername() {
		return hxusername;
	}
	public void setHxusername(String hxusername) {
		this.hxusername = hxusername;
	}
	public int getIsVip() {
		return isVip;
	}
	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}
	public String getMyHeadPhoto() {
		return myHeadPhoto;
	}
	public void setMyHeadPhoto(String myHeadPhoto) {
		this.myHeadPhoto = myHeadPhoto;
	}
	
	
}
