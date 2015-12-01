package differentjavabean;

public class ImageModel {
	private String imageUrl;
	private String redirectUrl;
	
	public ImageModel(String imageUrl, String redirectUrl){
		this.setImageUrl(imageUrl);
		this.setRedirectUrl(redirectUrl);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
