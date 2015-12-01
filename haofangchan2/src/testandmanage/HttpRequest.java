package testandmanage;

import java.util.HashMap;

public class HttpRequest {
	public static HashMap getBaseMap(String type,String proid,String payid){
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("type",type);
		map.put("proid", proid);
		map.put("payid",payid);
		return map;
	}
	
	public static HashMap extedMap(String type, String proid, String payid, String index,String count){
		HashMap<String, String> map = getBaseMap(type, proid, payid);
		map.put("index", index);
		map.put("count", count);
		return map;
	}
}
