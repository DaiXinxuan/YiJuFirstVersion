package different_fragment_data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import main.MainFragmentActivity;

import pictureconnectinit.InitPicture_setting;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.Window_metrics;
import web.WebActivity;
import android.content.Intent;
import android.graphics.Matrix.ScaleToFit;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import different_adapter.Main_picture_adapter;
import differentjavabean.ImageModel;
import differentjavabean.Main_data_javabean;

public class Main_viewpager implements OnPageChangeListener {

	private List<ImageModel> listpictureurl;
	private View main_view;
	private View view1, view2, view3, view4;
	private ImageView iv1, iv2, iv3, iv4;
	private TextView tv1, tv2, tv3, tv4, title;
	private Button b1;
	private AtomicInteger whats = new AtomicInteger(0);
	private boolean isContinue = true;
	private ViewPager vp;// 首页滑动图片
	private List<View> mIV;// 图片数组
    private ImageModel html;
	private Thread thread;
	Main_data_javabean main_data;

	private Handler handler = new Handler() {
		public void handleMessage(Message m) {
			LogUtil.d("图片位置变化",""+m.what);
			vp.setCurrentItem(m.what);
			super.handleMessage(m);
		}
	};

	public void initPicture(List<ImageModel> listpictureurl, View main_view) {
		this.main_view = main_view;
		this.listpictureurl = listpictureurl;

		LogUtil.d("main_fragment", "异步加载图片初始化成功");
		if(thread==null){
			thread=new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (isContinue) {
							handler.sendEmptyMessage(whats.get());
							whatOption();
						}
					}
				}	
			});
			thread.start();
		}
		
	}
	public void initFirstPage() {
		int screenHeight = Window_metrics.getScreenHeight();
		FrameLayout fl = (FrameLayout) main_view.findViewById(R.id.FrameLayout);
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) fl
				.getLayoutParams();
		linearParams.height = (int) (screenHeight * 0.2);
		fl.setLayoutParams(linearParams);
		vp = (ViewPager) main_view.findViewById(R.id.viewPager1);

		// 图片列表
		mIV = new ArrayList<View>();
		for (int i = 0; i < listpictureurl.size(); i++) {
			html=listpictureurl.get(i);
			
			ImageView iv = new ImageView(MyApplication.getContext());
			iv.setScaleType(iv.getScaleType().FIT_XY);
			InitPicture_setting.getImageLoader(html.getImageUrl(), iv);
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					LogUtil.d("HHHHHH", "success!!!!!");
					Intent intent=new Intent(MyApplication.getContext(),WebActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("url", ""+html.getRedirectUrl());
					intent.putExtra("title", "广告");
					MyApplication.getContext().startActivity(intent);
				}
			});
			mIV.add(iv);
		}
		vp.setAdapter(new Main_picture_adapter(mIV));
		vp.setOnPageChangeListener(this);
		// 按下时不继续定时滑动,弹起时继续定时滑动
		vp.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
	}

	private void whatOption() {

		whats.incrementAndGet();
        if(whats.get()>=listpictureurl.size()){
        	whats=new AtomicInteger(0);
        }
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
	}


	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {


	}

}
