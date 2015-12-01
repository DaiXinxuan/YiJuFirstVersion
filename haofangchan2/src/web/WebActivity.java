package web;

import testandmanage.LogUtil;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.haofangchan2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebActivity extends Activity {
	private WebView web;
	private TextView sharetv;
	String address;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		Intent intent = getIntent();
		address = intent.getStringExtra("url");
		String title = intent.getStringExtra("title");
		LogUtil.d("��ҳ��ַΪ", address);
		web = (WebView) findViewById(R.id.webView);
		sharetv = (TextView) findViewById(R.id.webShare); 
		web.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // ��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��
																				// ������������Ǳ�
				view.loadUrl(url);
				return true;
			}
		});

		web.getSettings().setUseWideViewPort(true);
		web.getSettings().setLoadWithOverviewMode(true);
		WebSettings settings = web.getSettings();
		settings.setSupportZoom(true);// �趨֧������
		settings.setBuiltInZoomControls(true);
		settings.setJavaScriptEnabled(true);
	    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	    web.loadUrl(address);
		TextView titleView = (TextView) findViewById(R.id.webTitle);
		titleView.setText(title);
		
		sharetv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showShare(address);
			}
		});
		
		ImageButton goback = (ImageButton) findViewById(R.id.webGoback);
		goback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				WebActivity.this.finish();
			}
		});
	}
	private void showShare(String url) {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle(getString(R.string.share));
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl(url);
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("�˾����㷿�������Ƽ�");
		 oks.setImageUrl("http://112.124.62.158:2869/project/img/projectlogo/23/23-2015-10-03-12-06-04.jpg");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 //oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl(url);
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl(url);
//		 oks.setTheme(OnekeyShareTheme.SKYBLUE);
		// ��������GUI
		 oks.show(this);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			// ���ؼ��˻�
			web.goBack();
			return true;
		} else {
			WebActivity.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
