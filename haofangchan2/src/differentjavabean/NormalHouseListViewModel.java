package differentjavabean;

import java.util.ArrayList;

public class NormalHouseListViewModel {
	private String roomId;// 房屋编号
	private ArrayList<String> typePhotos;// 房屋图片
	private String roomPrice;// 房屋售价
	private String config;// 房屋配置
	private String allArea;// 实际面积
	private String decoration;// 是否装修
	private String giveArea;// 赠送面积
	private String floor;// 楼层号
	private String forward;// 朝向
	private String roomNum;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public ArrayList<String> getTypePhotos() {
		return typePhotos;
	}

	public void setTypePhotos(ArrayList<String> typePhotos) {
		this.typePhotos = typePhotos;
	}

	public String getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getAllArea() {
		return allArea;
	}

	public void setAllArea(String allArea) {
		this.allArea = allArea;
	}

	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getGiveArea() {
		return giveArea;
	}

	public void setGiveArea(String giveArea) {
		this.giveArea = giveArea;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

}
