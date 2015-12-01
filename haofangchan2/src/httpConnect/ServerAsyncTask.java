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

	// �õ��Ƿ���ʾ�ջ�
	public boolean isShowIndicator() {
		return showIndicator;
	}

	// �����Ƿ���ʾ�ջ�
	public void setShowIndicator(boolean showIndicator) {
		this.showIndicator = showIndicator;
	}

	// ��HashMapת��ΪJSON�ַ���
	public String parseMap(Map<String, String> hashMap) {
		JSONObject mapObj = new JSONObject();
		// �õ�������
		Iterator<String> iterator = hashMap.keySet().iterator();
		String result = null;
		while (iterator.hasNext()) {
			// �õ�keyֵ
			String key = iterator.next();
			// �õ�valueֵ
			String value = hashMap.get(key);
			try {
				// ��key��value����JSONObject��
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

	// Ԥ��������ջ�����Ϊ��ʾ�������ջ�
	protected void onPreExecute() {
		
		super.onPreExecute();

		if (showIndicator) {
			// �½�һ��dialog
			dialog = new ProgressDialog(MyApplication.getContext());
			// ����dialog����ʽ
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// ���þջ����ܹ���ȡ��
			dialog.setCancelable(false);
			// ��ʾ�ջ�
			dialog.show();
		}
	}

	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		// ��һ������������ΪhashMap
		HashMap<String, String> commandMap = (HashMap<String, String>) params[0];
		// ���ڶ�����������ΪModel����UI�ӿ�
		this.updateUI = (UpdateUIInterface) params[1];
		// ����������������Ϊmodel�����ӿ�
		this.updateModel = (ConnectionHandleInteface) params[2];
		
		LogUtil.d("���緢�����ݣ�",""+commandMap);
		
		byte[] byteData=null;
		String response=null;
		try {
			byteData=sendPostRequest(MyApplication.base_url,"jsondata="+ parseMap(commandMap));
			response=new String(byteData);
			response = GZipDecompress.zipDecompressBase64Decoding(response);
			response = java.net.URLDecoder.decode(response, "utf-8");
			LogUtil.d("�������󷵻�����",""+response);
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
		// ȡ���ջ�
		if(dialog!=null){
		dialog.dismiss();
		}
		if (result != null ) {
			if (updateModel != null && updateUI != null) {
				// ����ģ��
				Object model = updateModel.handResponse(result);
				// ����UI
				updateUI.updateUI(model);
			} else {
				LogUtil.e(this.getClass().getName(),
						"UpdateUIInterface ���� updateModel Ϊ��");
			}
		} else {
			LogUtil.e(this.getClass().getName(), "��������Ϊnull");
		}
	}

//httpurlconnection ����
public  byte[] sendPostRequest(String path, String params) throws Exception{
    URL url = new URL(path);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");// �ύģʽ
    // conn.setConnectTimeout(10000);//���ӳ�ʱ ��λ����
    // conn.setReadTimeout(2000);//��ȡ��ʱ ��λ����
    conn.setDoOutput(true);// �Ƿ��������
    byte[] bypes = params.toString().getBytes();
    conn.getOutputStream().write(bypes);// �������
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
    byte[] data = outStream.toByteArray();//��ҳ�Ķ���������
    outStream.close();
    inStream.close();
    return data;
}
}