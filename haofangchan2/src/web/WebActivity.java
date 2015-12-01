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
		LogUtil.d("网页地址为", address);
		web = (WebView) findViewById(R.id.webView);
		sharetv = (TextView) findViewById(R.id.webShare); 
		web.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，
																				// 不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});

		web.getSettings().setUseWideViewPort(true);
		web.getSettings().setLoadWithOverviewMode(true);
		WebSettings settings = web.getSettings();
		settings.setSupportZoom(true);// 设定支持缩放
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
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl(url);
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("宜居优秀房产分享推荐");
		 oks.setImageUrl("http://112.124.62.158:2869/project/img/projectlogo/23/23-2015-10-03-12-06-04.jpg");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl(url);
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl(url);
//		 oks.setTheme(OnekeyShareTheme.SKYBLUE);
		// 启动分享GUI
		 oks.show(this);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			// 返回键退回
			web.goBack();
			return true;
		} else {
			WebActivity.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
