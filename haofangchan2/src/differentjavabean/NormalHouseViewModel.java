package differentjavabean;

import java.util.ArrayList;
import java.util.HashMap;

public class NormalHouseViewModel {
	private ArrayList<NormalHouseListViewModel> houseListViewModel;
	private ArrayList<String> searchFormModel;
	private HashMap<String, String> searchHouseTypeModel;
	private ArrayList<String> searchPriceModel;
	private NormalHouseInfoModel houseinfoModel;

	public NormalHouseInfoModel getHouseinfoModel() {
		return houseinfoModel;
	}

	public void setHouseinfoModel(NormalHouseInfoModel houseinfoModel) {
		this.houseinfoModel = houseinfoModel;
	}

	public ArrayList<NormalHouseListViewModel> getHouseListViewModel() {
		return houseListViewModel;
	}

	public void setHouseListViewModel(
			ArrayList<NormalHouseListViewModel> houseListViewModel) {
		this.houseListViewModel = houseListViewModel;
	}

	public ArrayList<String> getSearchFormModel() {
		return searchFormModel;
	}

	public void setSearchFormModel(ArrayList<String> searchFormModel) {
		this.searchFormModel = searchFormModel;
	}

	public HashMap<String, String> getSearchHouseTypeModel() {
		return searchHouseTypeModel;
	}

	public void setSearchHouseTypeModel(
			HashMap<String, String> searchHouseTypeModel) {
		this.searchHouseTypeModel = searchHouseTypeModel;
	}

	public ArrayList<String> getSearchPriceModel() {
		return searchPriceModel;
	}

	public void setSearchPriceModel(ArrayList<String> searchPriceModel) {
		this.searchPriceModel = searchPriceModel;
	}

}
