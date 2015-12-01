package differentjavabean;

public class Seehouse_DataBean {
	String id,area,info,price,status,scale,image;
	public Seehouse_DataBean(String id,String area,String info,String price,String status,String scale,String image) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.area=area;
		this.info=info;
		this.price=price;
		this.status=status;
		this.scale=scale;
		this.image=image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
