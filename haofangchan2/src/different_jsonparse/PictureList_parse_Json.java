package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.Add_salebuilding_picture_data_javabean;

import testandmanage.LogUtil;
import testandmanage.MyApplication;

import android.R.integer;
import android.widget.Toast;



public class PictureList_parse_Json implements ConnectionHandleInteface{
	private String m;
	private String str=null;
	List<Add_salebuilding_picture_data_javabean> list;
 public List<Add_salebuilding_picture_data_javabean> Picture_parse(String result){
	 JSONObject jsonObject;
	 
	 list=new ArrayList<Add_salebuilding_picture_data_javabean>() ;
	try {
		 jsonObject = new JSONObject(result);
		 boolean state=jsonObject.getBoolean("status");
		 if(state==true){
			 LogUtil.d("bug", "zhenshibug2_begin");
			 JSONArray jsonArray=jsonObject.getJSONArray("info");
			 for(int i=0;i<jsonArray.length();i++){
				 //此处map要放在循环中声明
				 JSONObject dataJsonObject= jsonArray.getJSONObject(i);
				 Add_salebuilding_picture_data_javabean saleBuilding=new Add_salebuilding_picture_data_javabean();
				 m=  dataJsonObject.getString("proid");
				 JSONArray photos=dataJsonObject.getJSONArray("img");
				 str= photos.getString(0);
				 saleBuilding.setId(m);
				 saleBuilding.setUrl(str);
				 list.add(saleBuilding);
			 }
		 }
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		LogUtil.d("bug", "zhenshibug4");
	}
    
	 return list;
	 
 }
@Override
public Object handResponse(String response) {
	
	return Picture_parse(response);
}
}
