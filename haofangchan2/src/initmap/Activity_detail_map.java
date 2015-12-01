package initmap;

import java.util.HashMap;
import java.util.Map;

public class Activity_detail_map {
	 Map<String,String> map;
   public Map<String,String> initMap(int type,String activityid){
	   map=new HashMap<String,String>();
	   map.put("type", ""+type);
	   map.put("payid", "1");
	   map.put("activityid", activityid);
	   
	   return map;
   }
   public Map<String,String> initMap_main(int type){
	   map=new HashMap<String,String>();
	   map.put("type", ""+type);
	   map.put("payid", "1");
	   map.put("proid", "1");
   
	   return map;
   }
   
   
}
