package differentjavabean;

import java.util.ArrayList;

public class HomePageModel {
	private String projectName;
	private String buildingIntrodeceHtml;
	private String magzineHtml;
	private ArrayList<ImageModel> photos;
	private ArrayList<HomePageHouseListModel> houses;
	private HomePageMapModel homePageMapModel;

	
	public String getBuildingIntrodeceHtml() {
		return buildingIntrodeceHtml;
	}

	public void setBuildingIntrodeceHtml(String buildingIntrodeceHtml) {
		this.buildingIntrodeceHtml = buildingIntrodeceHtml;
	}

	public HomePageMapModel getHomePageMapModel() {
		return homePageMapModel;
	}

	public void setHomePageMapModel(HomePageMapModel homePageMapModel) {
		this.homePageMapModel = homePageMapModel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ArrayList<ImageModel> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<ImageModel> photos) {
		this.photos = photos;
	}

	public ArrayList<HomePageHouseListModel> getHouses() {
		return houses;
	}

	public void setHouses(ArrayList<HomePageHouseListModel> houses) {
		this.houses = houses;
	}

	public String getMagzineHtml() {
		return magzineHtml;
	}

	public void setMagzineHtml(String magzineHtml) {
		this.magzineHtml = magzineHtml;
	}

}
