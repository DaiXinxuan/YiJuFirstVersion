package differentjavabean;


public class FriendBean {  
       
    private String imgurl;  
    private String name;  
    private String detail;  
       
    public FriendBean(String imgurl, String name, String detail) {  
        this.imgurl  = imgurl;  
        this.name   = name;  
        this.detail = detail;  
    }  
       
    public void setImageId(int resId) {  
        this.imgurl  = imgurl;  
    }  
       
    public String getImageId() {  
        return imgurl;  
    }  
       
    public void setName(String name) {  
        this.name   = name;  
    }  
       
    public String getName() {  
        return name;  
    }  
       
    public void setDetail(String detail) {  
        this.detail = detail;  
    }  
       
    public String getDetail() {  
        return detail;  
    }  
       
    public String toString() {  
        return "Item[" + imgurl + ", " + name + ", " + detail + "]";  
    }  
   
}