package httpConnect;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import load.LoginActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eip.util.GZipDecompress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ServerAsyncHttpTask {
	public static String parseMap(Map<String, String> hashMap) {
		JSONObject mapObj = new JSONObject();
		// 得到迭代器
		Iterator<String> iterator = hashMap.keySet().iterator();
		String jsonData = null;

		while (iterator.hasNext()) {
			// 得到key值
			String key = iterator.next();
			// 得到value值
			String value = hashMap.get(key);
				// 将key和value放入JSONObject中
				try {
					mapObj.put(key, value);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 进行base64压缩解码

		}
		LogUtil.d("新网络请求map",""+mapObj.toString());
		try {
			jsonData = GZipDecompress.zipCompressBase64Encoding(mapObj
					.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonData;
	}

	public static void execute(Map<String, String> hashMap,
			final UpdateUIInterface uiHandler,
			final ConnectionHandleInteface modelParser) {
		LogUtil.d("新网络发送数据：",""+hashMap);
		// 将hashmap转换为base64压缩字符串
		String jsonData = parseMap(hashMap);
		// 初始化request的parameter
		RequestParams params = new RequestParams();
		// 设置JSON的内容
		params.put("jsondata", jsonData);
		// 初始化上传客户端
		AsyncHttpClient client = new AsyncHttpClient();
		// 进行网络请求
		client.post(MyApplication.base_url,params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				// 得到base64的字符串
				String response = new String(arg2);
				LogUtil.d("新网络返回值",""+response);
				try {
					// 对base64字符串进行反解码
					response = GZipDecompress
							.zipDecompressBase64Decoding(response);
					// URLDecoder进行反解码
					response = URLDecoder.decode(response, "utf-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtil.d(this.getClass().getName(), response);
				// 解析model
	            try {
					JSONObject jsonObject=new JSONObject(response);
					String status= jsonObject.getString("status");
					if(status.equals("relogin")){
						String deviceName=jsonObject.getString("deviceName");
						Toast.makeText(MyApplication.getContext(), "重复登录："+deviceName+"登录您的帐号",Toast.LENGTH_LONG).show();
						turnToLoginActivity();
						
					}else if(status.equals("inexist")){
						turnToLoginActivity();
					}else{
						Object model = modelParser.handResponse(response);
						// 更新UI
						if(model!=null){
							
						   uiHandler.updateUI(model);
						
						}else {
							Toast.makeText(MyApplication.getContext(), "未获取到网络数据", Toast.LENGTH_SHORT).show();
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MyApplication.getContext(), "网络通讯错误",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	
	public static void  turnToLoginActivity() {
		Intent intent=new Intent(MyApplication.getContext(),LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    MyApplication.getContext().startActivity(intent);
	}
	
	
	public static void executeWithIndecator(Map<String, String> hashMap,
			final UpdateUIInterface uiHandler,
			final ConnectionHandleInteface modelParser,Context context) {
		// 将hashmap转换为base64压缩字符串
		String jsonData = parseMap(hashMap);
		// 初始化request的parameter
		RequestParams params = new RequestParams();
		// 设置JSON的内容
		params.put("jsondata", jsonData);
		// 初始化上传客户端
		AsyncHttpClient client = new AsyncHttpClient();
		// 新建一个dialog
		final ProgressDialog dialog = new ProgressDialog(
				context);
		// 设置dialog的样式
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置菊花不能够被取消
		dialog.setCancelable(false);
		// 显示菊花
		dialog.show();

		client.post(MyApplication.base_url,params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				// 得到base64的字符串
				String response = new String(arg2);
				// 取消菊花
				dialog.dismiss();
				try {
					// 对base64字符串进行反解码
					response = GZipDecompress
							.zipDecompressBase64Decoding(response);
					// URLDecoder进行反解码
					response = URLDecoder.decode(response, "utf-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtil.d(this.getClass().getName(), response);
				// 解析model
				Object model = modelParser.handResponse(response);
				// 更新UI
				uiHandler.updateUI(model);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				// 取消菊花
				dialog.dismiss();
				Toast.makeText(MyApplication.getContext(), "网络通讯错误",
						Toast.LENGTH_LONG).show();
			}
		});
	}

//	public static void upLoadHeadPhoto(final File headPhoto, String payUserId) {
//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//		try {
//			// 设置图片
//			params.put("image", headPhoto);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 设置payId
//		params.put("param", payUserId);
//		client.post(MyApplication.base_url_upload, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MyApplication.getContext(), "失败",
//								Toast.LENGTH_LONG).show();
//						// 将临时文件删除
//						headPhoto.delete();
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MyApplication.getContext(), "成功",
//								Toast.LENGTH_LONG).show();
//						// 将临时文件删除
//						headPhoto.delete();
//					}
//				});
//	}
}
