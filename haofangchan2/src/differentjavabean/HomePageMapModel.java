package differentjavabean;

public class HomePageMapModel {
	private String address;
	private String saleAddress;
	
	public HomePageMapModel(String address, String saleAddress){
		this.setAddress(address);
		this.setSaleAddress(saleAddress);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSaleAddress() {
		return saleAddress;
	}

	public void setSaleAddress(String saleAddress) {
		this.saleAddress = saleAddress;
	}
}
