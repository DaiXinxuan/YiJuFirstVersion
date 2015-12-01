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
		// �õ�������
		Iterator<String> iterator = hashMap.keySet().iterator();
		String jsonData = null;

		while (iterator.hasNext()) {
			// �õ�keyֵ
			String key = iterator.next();
			// �õ�valueֵ
			String value = hashMap.get(key);
				// ��key��value����JSONObject��
				try {
					mapObj.put(key, value);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ����base64ѹ������

		}
		LogUtil.d("����������map",""+mapObj.toString());
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
		LogUtil.d("�����緢�����ݣ�",""+hashMap);
		// ��hashmapת��Ϊbase64ѹ���ַ���
		String jsonData = parseMap(hashMap);
		// ��ʼ��request��parameter
		RequestParams params = new RequestParams();
		// ����JSON������
		params.put("jsondata", jsonData);
		// ��ʼ���ϴ��ͻ���
		AsyncHttpClient client = new AsyncHttpClient();
		// ������������
		client.post(MyApplication.base_url,params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				// �õ�base64���ַ���
				String response = new String(arg2);
				LogUtil.d("�����緵��ֵ",""+response);
				try {
					// ��base64�ַ������з�����
					response = GZipDecompress
							.zipDecompressBase64Decoding(response);
					// URLDecoder���з�����
					response = URLDecoder.decode(response, "utf-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtil.d(this.getClass().getName(), response);
				// ����model
	            try {
					JSONObject jsonObject=new JSONObject(response);
					String status= jsonObject.getString("status");
					if(status.equals("relogin")){
						String deviceName=jsonObject.getString("deviceName");
						Toast.makeText(MyApplication.getContext(), "�ظ���¼��"+deviceName+"��¼�����ʺ�",Toast.LENGTH_LONG).show();
						turnToLoginActivity();
						
					}else if(status.equals("inexist")){
						turnToLoginActivity();
					}else{
						Object model = modelParser.handResponse(response);
						// ����UI
						if(model!=null){
							
						   uiHandler.updateUI(model);
						
						}else {
							Toast.makeText(MyApplication.getContext(), "δ��ȡ����������", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(MyApplication.getContext(), "����ͨѶ����",
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
		// ��hashmapת��Ϊbase64ѹ���ַ���
		String jsonData = parseMap(hashMap);
		// ��ʼ��request��parameter
		RequestParams params = new RequestParams();
		// ����JSON������
		params.put("jsondata", jsonData);
		// ��ʼ���ϴ��ͻ���
		AsyncHttpClient client = new AsyncHttpClient();
		// �½�һ��dialog
		final ProgressDialog dialog = new ProgressDialog(
				context);
		// ����dialog����ʽ
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ���þջ����ܹ���ȡ��
		dialog.setCancelable(false);
		// ��ʾ�ջ�
		dialog.show();

		client.post(MyApplication.base_url,params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				// �õ�base64���ַ���
				String response = new String(arg2);
				// ȡ���ջ�
				dialog.dismiss();
				try {
					// ��base64�ַ������з�����
					response = GZipDecompress
							.zipDecompressBase64Decoding(response);
					// URLDecoder���з�����
					response = URLDecoder.decode(response, "utf-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtil.d(this.getClass().getName(), response);
				// ����model
				Object model = modelParser.handResponse(response);
				// ����UI
				uiHandler.updateUI(model);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				// ȡ���ջ�
				dialog.dismiss();
				Toast.makeText(MyApplication.getContext(), "����ͨѶ����",
						Toast.LENGTH_LONG).show();
			}
		});
	}

//	public static void upLoadHeadPhoto(final File headPhoto, String payUserId) {
//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//		try {
//			// ����ͼƬ
//			params.put("image", headPhoto);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// ����payId
//		params.put("param", payUserId);
//		client.post(MyApplication.base_url_upload, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MyApplication.getContext(), "ʧ��",
//								Toast.LENGTH_LONG).show();
//						// ����ʱ�ļ�ɾ��
//						headPhoto.delete();
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MyApplication.getContext(), "�ɹ�",
//								Toast.LENGTH_LONG).show();
//						// ����ʱ�ļ�ɾ��
//						headPhoto.delete();
//					}
//				});
//	}
}
