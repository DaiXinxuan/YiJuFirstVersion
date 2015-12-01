package differentjavabean;

public class PriceBean {
	String time,name,price;
	public PriceBean(String time,String name,String price){
		this.time=time;
		this.name=name;
		this.price=price;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
