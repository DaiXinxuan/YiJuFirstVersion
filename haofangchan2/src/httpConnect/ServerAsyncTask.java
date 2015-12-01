package httpConnect;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import com.eip.util.GZipDecompress;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ServerAsyncTask extends AsyncTask<Object, Integer, String> {

	private UpdateUIInterface updateUI;
	private boolean showIndicator;
	private ProgressDialog dialog;
	private ConnectionHandleInteface updateModel;

	// 得到是否显示菊花
	public boolean isShowIndicator() {
		return showIndicator;
	}

	// 设置是否显示菊花
	public void setShowIndicator(boolean showIndicator) {
		this.showIndicator = showIndicator;
	}

	// 将HashMap转换为JSON字符串
	public String parseMap(Map<String, String> hashMap) {
		JSONObject mapObj = new JSONObject();
		// 得到迭代器
		Iterator<String> iterator = hashMap.keySet().iterator();
		String result = null;
		while (iterator.hasNext()) {
			// 得到key值
			String key = iterator.next();
			// 得到value值
			String value = hashMap.get(key);
			try {
				// 将key和value放入JSONObject中
				mapObj.put(key, value);
				result = GZipDecompress.zipCompressBase64Encoding(mapObj
						.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	// 预处理，如果菊花设置为显示，则加入菊花
	protected void onPreExecute() {
		
		super.onPreExecute();

		if (showIndicator) {
			// 新建一个dialog
			dialog = new ProgressDialog(MyApplication.getContext());
			// 设置dialog的样式
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// 设置菊花不能够被取消
			dialog.setCancelable(false);
			// 显示菊花
			dialog.show();
		}
	}

	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		// 第一个参数，解析为hashMap
		HashMap<String, String> commandMap = (HashMap<String, String>) params[0];
		// 将第二个参数解析为Model更新UI接口
		this.updateUI = (UpdateUIInterface) params[1];
		// 将第三个参数解析为model解析接口
		this.updateModel = (ConnectionHandleInteface) params[2];
		
		LogUtil.d("网络发送数据：",""+commandMap);
		
		byte[] byteData=null;
		String response=null;
		try {
			byteData=sendPostRequest(MyApplication.base_url,"jsondata="+ parseMap(commandMap));
			response=new String(byteData);
			response = GZipDecompress.zipDecompressBase64Decoding(response);
			response = java.net.URLDecoder.decode(response, "utf-8");
			LogUtil.d("网络请求返回数据",""+response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// 取消菊花
		if(dialog!=null){
		dialog.dismiss();
		}
		if (result != null ) {
			if (updateModel != null && updateUI != null) {
				// 解析模型
				Object model = updateModel.handResponse(result);
				// 更新UI
				updateUI.updateUI(model);
			} else {
				LogUtil.e(this.getClass().getName(),
						"UpdateUIInterface 或者 updateModel 为空");
			}
		} else {
			LogUtil.e(this.getClass().getName(), "返回数据为null");
		}
	}

//httpurlconnection 请求
public  byte[] sendPostRequest(String path, String params) throws Exception{
    URL url = new URL(path);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");// 提交模式
    // conn.setConnectTimeout(10000);//连接超时 单位毫秒
    // conn.setReadTimeout(2000);//读取超时 单位毫秒
    conn.setDoOutput(true);// 是否输入参数
    byte[] bypes = params.toString().getBytes();
    conn.getOutputStream().write(bypes);// 输入参数
    InputStream inStream=conn.getInputStream();
    return readInputStream(inStream);
}

public byte[] readInputStream(InputStream inStream) throws Exception{
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while( (len = inStream.read(buffer)) !=-1 ){
        outStream.write(buffer, 0, len);
    }
    byte[] data = outStream.toByteArray();//网页的二进制数据
    outStream.close();
    inStream.close();
    return data;
}
}