package differentjavabean;

public class OrderWatchingItemModel {
	private String houseName;
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHouseNameData() {
		return houseNameData;
	}
	public void setHouseNameData(String houseNameData) {
		this.houseNameData = houseNameData;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	private String houseNameData;
	private String visitDate;
	private String roomId;
}
