package com.uranus.amaptest;

import java.util.ArrayList;
import java.util.HashMap;

public class MapViewModel {
	private String buildingAddress;
	private String saleBuildingAddress;
	private HashMap<String, ArrayList<String>> addressHashMap;

	public String getBuildingAddress() {
		return buildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		this.buildingAddress = buildingAddress;
	}

	public String getSaleBuildingAddress() {
		return saleBuildingAddress;
	}

	public void setSaleBuildingAddress(String saleBuildingAddress) {
		this.saleBuildingAddress = saleBuildingAddress;
	}

	public HashMap<String, ArrayList<String>> getAddressHashMap() {
		return addressHashMap;
	}

	public void setAddressHashMap(
			HashMap<String, ArrayList<String>> addressHashMap) {
		this.addressHashMap = addressHashMap;
	}
}
