package connect;

import httpConnect.ConnectionHandleInteface;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.json.JSONObject;

import android.util.Log;

import com.eip.util.GZipDecompress;

import testandmanage.LogUtil;

public class Http_Connection {


public static Object postRequest(final String url,
		final Map<String,String> rawParams,
		ConnectionHandleInteface ch)  throws Exception{
	
	FutureTask<String> task=new FutureTask<String>(
			new Callable<String>() {
				public  String call() throws Exception {
					JSONObject jsonObject = new JSONObject();
					for(String key:rawParams.keySet()){
						jsonObject.put(key, rawParams.get(key));
					}
					String params=jsonObject.toString();
					String sr = com.eip.post.post.sendPost(url,
							"jsondata="+GZipDecompress.zipCompressBase64Encoding(params));
					sr=GZipDecompress.zipDecompressBase64Decoding(sr);
					 sr=java.net.URLDecoder.decode(sr, "utf-8");
					return sr;
				}});
	new Thread(task).start();
	LogUtil.d("httpconntion_result",""+task.get());
	 ServerHandle sh=null;
	try {
		
		sh = new ServerHandle(task.get(), ch);
		
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	 Object data = null;
	try {
		data = sh.Response_Hand();
	} catch (Exception e) {
		LogUtil.w("http_connection_back", "·µ»ØÊý¾ÝÊ§°Ü");
	}
	Log.d("http_connection", ""+data);
	return data;
	
}
}
