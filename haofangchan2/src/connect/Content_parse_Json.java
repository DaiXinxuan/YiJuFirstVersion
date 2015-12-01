package connect;

import httpConnect.ConnectionHandleInteface;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Content_parse_Json implements ConnectionHandleInteface 
{
	Object back_data=null;
  public Content_parse_Json(Object object) {
	 back_data=object;
}
  public String file_parse_Json(){
	  
	  return null;
  }
public Bitmap picture_parse_Json(){
	  
	  return null;
  }
@Override
public Object handResponse(String response) {
	try {
		ArrayList<HashMap<String, Object>> list = 
				new ArrayList<HashMap<String, Object>>();
		JSONObject jsonObject=new JSONObject(response);
		 JSONArray jsonArray = jsonObject.getJSONArray("jsondata=");
	        for (int i = 0; i < jsonArray.length(); i++) {
	             jsonObject = jsonArray.getJSONObject(i);
	             // ��ʼ��map�������
	             HashMap<String, Object> map = new HashMap<String, Object>();
	             map.put("logo", jsonObject.getString("logo"));
	             map.put("logoLunbo", jsonObject.getString("logoLunbo"));
	             map.put("biaoTi", jsonObject.getString("biaoTi"));
	             map.put("yuanJia", jsonObject.getString("yuanJia"));
	             map.put("xianJia", jsonObject.getString("xianJia"));
	             map.put("id", jsonObject.getInt("id"));
	             list.add(map);
	         }
	        Iterator<HashMap<String, Object>> it = list.iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                if ((Integer) ma.get("id") == 68) {
                	//������ַ���ΪҪ������ı���Ϣ
                 String a=(String) ma.get("biaoTi");
                 String b=(String) ma.get("yuanJia");
                 String c=(String) ma.get("xianJia");
       
                }}
            List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
            for(HashMap<String, Object> news : list){
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("chuXingTianShu", news.get("chuXingTianShu"));
                item.put("biaoTi", news.get("biaoTi"));
                item.put("yuanJia", news.get("yuanJia"));
                item.put("xianJia", news.get("xianJia"));
                item.put("id", news.get("id"));
                Bitmap bitmap=null;
                try {
                	//��ȡ���������ص�ͼƬ��ӦURL��Ȼ�������µ�ͼƬ��ַ��ȡͼƬ
                    bitmap = getImage(news.get("logo").toString());//ͼƬ�ӷ������ϻ�ȡ
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(bitmap==null){
                    LogUtil.d("bitmap", ""+bitmap);                                      // ��ʾͼƬ���
                }
                item.put("logo",bitmap);
                data.add(item);




            }} catch (JSONException e) {
		e.printStackTrace();
	}
	return null;
}
public static Bitmap getImage(String path) throws Exception{

    /*URL url = new URL(imageUrl);   
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
    InputStream is = conn.getInputStream();   
    mBitmap = BitmapFactory.decodeStream(is);*/
    Bitmap bitmap= null;
    URL url = new URL(path);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();//����HTTPЭ�����Ӷ���
    conn.setConnectTimeout(5000);
    conn.setRequestMethod("GET");
    if(conn.getResponseCode() == 200){
        InputStream inStream = conn.getInputStream();
        bitmap = BitmapFactory.decodeStream(inStream);
    }
    return bitmap;
}
}

    



