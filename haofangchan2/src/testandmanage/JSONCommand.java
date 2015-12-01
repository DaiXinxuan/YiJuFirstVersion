package testandmanage;

import java.util.HashMap;
import java.util.Map;

import differentjavabean.PersonalInfoModel;

public class JSONCommand {
	public static HashMap<String, String> JSONNormalBase(String proId,
			String payId) {
		HashMap<String, String> map = new HashMap<String, String>();
		String payidString = MyApplication.getpayid();
		if (payidString != null) {
			payId = payidString;
		}
		String proidString = MyApplication.getproid();
		if (proidString != null) {
			proId = proidString;
		}
		map.put("payid", payId);
		map.put("proid", proId);
		return map;
	}

	public static HashMap<String, String> JSON10001(String userName,
			String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "10001");
		map.put("username", userName);
		map.put("password", password);
		return map;
	}

	public static HashMap<String, String> JSON10002(String userName,
			String password, String nickName) {
		HashMap<String, String> map = JSON10001(userName, password);
		map.put("type", "10002");
		map.put("nickname", nickName);
		return map;
	}

	public static HashMap<String, String> JSON10003(String payid,
			String userName, String password, String oldPassword) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "10003");
		map.put("payid", payid);
		map.put("username", userName);
		map.put("password", password);
		map.put("oldPassword", oldPassword);
		return map;
	}

//	public static HashMap<String, String> JSON10005(String payId) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("type", "10005");
//		map.put("payid", payId);
//		return map;
//	}

	public static HashMap<String, String> JSON10006(String proId, String payId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payid", payId);
		map.put("proid", proId);
		map.put("type", "10006");
		return map;
	}

	public static HashMap<String, String> JSON10007(String proId, String payId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payid", payId);
		map.put("proid", proId);
		map.put("type", "10007");
		return map;
	}

	public static HashMap<String, String> JSON10008(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10008");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10009(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10009");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10010(String proId, String payId,
			String roomId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10010");
		map.put("roomid", roomId);
		return map;
	}

	public static HashMap<String, String> JSON10011(String proId, String payId,
			String houseId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10011");
		map.put("houseid", houseId);
		return map;
	}

	public static HashMap<String, String> JSON10012(String proId, String payId,
			String roomId, String firstPrice, String years, String loan,
			String backLoan, String roomprice) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10012");
		map.put("roomid", roomId);
		map.put("firstprice", firstPrice);
		map.put("years", years);
		map.put("loan", loan);
		map.put("backloan", backLoan);
		map.put("roomprice", roomprice);
		return map;
	}

	public static HashMap<String, String> JSON10013(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10013");
		return map;
	}

	public static HashMap<String, String> JSON10014(String proId, String payId,
			String index, String count, String roomId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10014");
		map.put("index", index);
		map.put("count", count);
		map.put("roomid", roomId);
		return map;
	}

	public static HashMap<String, String> JSON10015(String proId, String payId,
			String content, String roomNum, String roomId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10015");
		map.put("content", content);
		map.put("roomNum", roomNum);
		map.put("roomid", roomId);
		return map;
	}

	public static HashMap<String, String> JSON10016(String proId, String payId,
			String houseForm) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10016");
		map.put("houseForm", houseForm);
		return map;
	}

	public static HashMap<String, String> JSON10017(String proId, String payId,
			String key1, String key2) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10017");
		map.put("key1", key1);
		map.put("key2", key2);
		return map;
	}

	public static HashMap<String, String> JSON10018(String proId, String payId,
			String houseTypeId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10018");
		map.put("housetypeid", houseTypeId);
		return map;
	}

	public static HashMap<String, String> JSON10019(String proId, String payId,
			String key1, String key2) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10019");
		map.put("key1", key1);
		map.put("key2", key2);
		return map;
	}

	public static HashMap<String, String> JSON10020(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10020");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10021(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10021");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10022(String payId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "10022");
		map.put("payid", payId);
		return map;
	}

	public static HashMap<String, String> JSON10023(String userName,
			String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "10023");
		map.put("username", userName);
		map.put("password", password);
		return map;
	}

	public static HashMap<String, String> JSON10024(PersonalInfoModel model,
			String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10024");
		map.put("nickname", model.getNickName());
		map.put("sex", model.getSex());
		map.put("introduce", model.getIntroduce());
		map.put("name", model.getName());
		map.put("age", model.getAge());
		map.put("tel", model.getTel());
		map.put("addr", model.getAddr());
		map.put("member", model.getMember());
		map.put("job", model.getJob());
		map.put("income", model.getIncome());
		map.put("housetype", model.getHouseType());
		map.put("willprice", model.getWillPrice());
		return map;
	}

	public static HashMap<String, String> JSON10025() {
		HashMap<String, String> map = JSONNormalBase("6", "1");
		map.put("type", "10025");
		return map;
	}

	public static HashMap<String, String> JSON10026(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10026");
		return map;
	}

	public static HashMap<String, String> JSON10027(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10027");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10028(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10028");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10029(String proId, String payId,
			String index, String count, String salerId, String salerName) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10029");
		map.put("index", index);
		map.put("count", count);
		map.put("salerid", salerId);
		map.put("salername", salerName);
		return map;
	}

	public static HashMap<String, String> JSON10030(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10030");
		return map;
	}

	public static HashMap<String, String> JSON10031(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10031");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10034(String proId, String payId,
			String friendId, String salerId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10034");
		map.put("friendid", friendId);
		map.put("salerid", salerId);
		return map;
	}

	public static HashMap<String, String> JSON10035(String proId, String payId,
			String friendId, String salerId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10035");
		map.put("friendid", friendId);
		map.put("friendphone", salerId);
		return map;
	}

	public static HashMap<String, String> JSON10037(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10037");
		return map;
	}

	public static HashMap<String, String> JSON10038(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10038");
		return map;
	}

	public static HashMap<String, String> JSON10039(String proId, String payId,
			String activityId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10039");
		map.put("activityid", activityId);
		return map;
	}

	public static HashMap<String, String> JSON10040(String proId, String payId,
			String remarkname, String friendid, String friendPhone) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10040");
		map.put("remarkname", remarkname);
		map.put("friendid", friendid);
		map.put("friendphone", friendPhone);
		return map;
	}
	
	public static HashMap<String, String> JSON10042(String proId, String payId,
			String remarkname, String friendid, String friendPhone,String realPayid) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10042");
		map.put("remarkname", remarkname);
		map.put("friendid", friendid);
		map.put("friendphone", friendPhone);
		map.put("realPayid", realPayid);
		return map;
	}
	
	public static HashMap<String, String> JSON10044(String proId, String payId,
			String salerId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10044");
		map.put("salerid", salerId);
		return map;
	}
	public static HashMap<String, String> JSON10045(String proId, String payId,
			String friendid, String remarkname) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10045");
		map.put("friendid", friendid);
		map.put("remarkname", remarkname);
		return map;
	}
	public static HashMap<String, String> JSON10046(String proId, String payId,
			String saleId, String saleName) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10046");
		map.put("salerid", saleId);
		map.put("salername", saleName);
		return map;
	}

	public static HashMap<String, String> JSON10048(String proId, String payId,
			String buyActivityId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10048");
		map.put("buyactivityId", buyActivityId);
		return map;
	}

	public static HashMap<String, String> JSON10049(String proId, String payId,
			String activityId, String roomKind) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10049");
		map.put("activityid", activityId);
		map.put("roomkind", roomKind);
		return map;
	}

	public static HashMap<String, String> JSON10050(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10050");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10051(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10051");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10052(String proId, String payId, String activityId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10052");
		map.put("activityid", activityId);
		return map;
	}

	public static HashMap<String, String> JSON10053(String proId, String payId,
			String activityId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10053");
		map.put("activityid", activityId);
		return map;
	}

	
	public static HashMap<String, String> JSON10055(String proId, String payId,String activityid) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10055");
		map.put("activityid", activityid);
		return map;
	}
	
	public static HashMap<String, String> JSON10056(String proId, String payId) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10056");
		return map;
	}

	public static HashMap<String, String> JSON10057(String proId, String payId,
			String salerId, String salerName, String content, String level) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10057");
		map.put("salerid", salerId);
		map.put("salername", salerName);
		map.put("content", content);
		map.put("level", level);
		return map;
	}

	public static HashMap<String, String> JSON10058(String proId, String payId,
			String roomId, String name, String date, String phoneNumber,
			String count, String introduction) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10058");
		map.put("roomid", roomId);
		map.put("name", name);
		map.put("date", date);
		map.put("phonenumber", phoneNumber);
		map.put("visitamount", count);
		map.put("introduction", introduction);
		return map;
	}

	public static HashMap<String, String> JSON10059(String proId, String payId,
			String index, String count) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10059");
		map.put("index", index);
		map.put("count", count);
		return map;
	}

	public static HashMap<String, String> JSON10060(String roomId,
			String payId, String proid) {
		HashMap<String, String> map = JSONNormalBase(proid, payId);
		map.put("type", "10060");
		map.put("roomid", roomId);
		return map;
	}

	public static HashMap<String, String> JSON10061(String proId, String payId,
			String frientPhone) {
		HashMap<String, String> map = JSONNormalBase(proId, payId);
		map.put("type", "10061");
		map.put("friendphone", frientPhone);
		return map;
	}

	public static HashMap<String, String> JSON10062(String payId,
			String roomId, String proid) {
		HashMap<String, String> map = JSONNormalBase(proid, payId);
		map.put("type", "10062");
		map.put("roomid", roomId);
		return map;
	}

	public static HashMap<String, String> JSON10063(String proId, String payid,
			String count, String index, String roomid) {
		HashMap<String, String> map =  JSONNormalBase(proId, payid);
		map.put("type", "10063");
		map.put("count", count);
		map.put("index", index);
		map.put("roomid", roomid);
		return map;
	}

	public static Map<String, String> JSON10032(String softFeed,String youFeed,String softVersion) {
		HashMap<String, String>  map = JSONNormalBase(MyApplication.getproid(), MyApplication.getpayid());
		map.put("type", "10032");
		map.put("softfeed", softFeed);
		map.put("youfeed", youFeed);
		map.put("softVersions", softVersion);
		return map;
	}
}
