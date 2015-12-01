package differentjavabean;

import org.litepal.crud.DataSupport;

import android.R.bool;
import android.os.Parcel;
import android.os.Parcelable;

public class MyfriendActivity_javabean extends DataSupport implements Parcelable {
	private String payuserid;
	private String headphotopath;
	private String nickname;
	private String hxusername;
	private int isVip;
	private String myHeadPhoto;
   
	public MyfriendActivity_javabean(){
		
	}
	
	public String getMyHeadPhoto() {
		return myHeadPhoto;
	}
	public void setMyHeadPhoto(String myHeadPhoto) {
		this.myHeadPhoto = myHeadPhoto;
	}
	
	public int getIsVip() {
		return isVip;
	}
	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

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
	
	
	 public void writeToParcel(Parcel dest, int flags) {  
	        dest.writeString(payuserid);  
	        dest.writeString(headphotopath);  
	        dest.writeString(nickname);  
	        dest.writeString(myHeadPhoto); 
	        dest.writeString(hxusername); 
	        dest.writeInt(isVip);
	    }  
	      
	    public static final Parcelable.Creator<MyfriendActivity_javabean> CREATOR = new Creator<MyfriendActivity_javabean>()  
	    {  
	  
	        @Override  
	        public MyfriendActivity_javabean createFromParcel(Parcel source) {  
	            return new MyfriendActivity_javabean(source);  
	        }  
	  
	        @Override  
	        public MyfriendActivity_javabean[] newArray(int size) {  
	            return new MyfriendActivity_javabean[size];  
	        }  
	          
	    };  
	      
	    private MyfriendActivity_javabean(Parcel dest)  
	    {  
	        this.payuserid = dest.readString();  
	        this.headphotopath = dest.readString();  
	        this.nickname = dest.readString();  
	        this.myHeadPhoto = dest.readString();  
	        this.hxusername = dest.readString();
	        this.isVip=dest.readInt();
	    }
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}  

}
