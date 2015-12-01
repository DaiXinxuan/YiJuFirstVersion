package add_salebuilding_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import load.LoginActivity;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import connect.Http_Connection;
import different_jsonparse.PictureList_parse_Json;
import differentjavabean.Add_salebuilding_picture_data_javabean;

public class Add_building_postdata {
  public List<Add_salebuilding_picture_data_javabean> post(Map<String, String> map){
	  
		List<Map<String, String>> listofpicture=null;
		List<Add_salebuilding_picture_data_javabean> pictureObject_list=null;
		try {
			listofpicture=new ArrayList<Map<String, String>>();
			pictureObject_list=
					new ArrayList<Add_salebuilding_picture_data_javabean>();
			listofpicture=(List<Map<String, String>>) Http_Connection.postRequest(
					MyApplication.base_url,map
					,new PictureList_parse_Json());
            Log.d("listofpicture", ""+listofpicture.toString());
		} catch (Exception e) {
			LogUtil.d("ssss2", "big wrong");
		} 

		return pictureObject_list;
  }
}
