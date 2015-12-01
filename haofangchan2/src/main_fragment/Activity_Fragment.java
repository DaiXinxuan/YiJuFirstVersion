package main_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testandmanage.HttpRequest;
import testandmanage.LogUtil;
import testandmanage.MyApplication;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.haofangchan2.R;

import connect.Http_Connection;
import different_jsonparse.ActivityParser;
import different_jsonparse.Activity_Indiana_jsonparse;
import different_jsonparse.Activity_Sign_jsonparse;
import different_jsonparse.GroupBuyParser;
import differentjavabean.ActivityModel;
import differentjavabean.Activity_Indiana_javabean;
import differentjavabean.Activity_Sign_javabean;
import differentjavabean.GroupBuyModel;


public class Activity_Fragment extends Fragment {  
	  private TextView tv;  
	  private ViewPager vp;
	  private ArrayList<View> viewContanier = new ArrayList<View>();
	  private ImageView cursor,tab1,tab2,tab3,tab4,back,background;
	  private int offset = 0;// 动画图片偏移量
	  private int currIndex = 0;// 当前页卡编号
	  private int bmpW;// 动画图片宽度
	  View view;
	  int width,height;
	  private ArrayList<Fragment> fragmentList;
	  private Activity_Viewpager_fragment1 mfragment1; 
	  private Activity_Viewpager_fragment2 mfragment2; 
	  private Activity_Viewpager_fragment3 mfragment3; 
	  private Activity_Viewpager_fragment4 mfragment4; 
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	          Bundle savedInstanceState) {  
	      view=inflater.inflate(R.layout.activity_layout2, container, false);
	      
	      DisplayMetrics dm = new DisplayMetrics();
		  getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		  width = dm.widthPixels;// 获取分辨率宽度
		  height = dm.heightPixels;//获取分辨率高度

	      findView();
	      initPager(inflater);
	      InitImageView();
	      
		  LinearLayout.LayoutParams lp2 = (LayoutParams) background.getLayoutParams();
		  lp2.height = (int) (0.15*height);
		  lp2.width=(int)(width);
		  background.setLayoutParams(lp2);
		  
		  LinearLayout.LayoutParams lp=(LayoutParams)tab1.getLayoutParams() ;
		  lp.width = (int) (0.25*width);
		  lp.height = (int) (0.15*height);
		  tab1.setLayoutParams(lp);
		  tab2.setLayoutParams(lp);
		  tab3.setLayoutParams(lp);
		  tab4.setLayoutParams(lp);
		  
		  LinearLayout.LayoutParams lp1 = (LayoutParams) cursor.getLayoutParams();
		  lp1.width=(int) (0.25*width);
		  lp1.setMargins(0, 0, 0, 0);
		  cursor.setLayoutParams(lp1);
		  
		  
	      vp.setOnPageChangeListener(new OnPageChangeListener() {
				int one = offset;// 页卡1 -> 页卡2 偏移量
				@Override
				public void onPageSelected(int arg0) {
					LogUtil.d("当前位置：",""+arg0+":::::"+currIndex);
					Animation animation = null;
					switch(arg0){
					case 0:
						if(currIndex==1){
							LogUtil.d("执行","+++++++++++++++++++");
							animation = new TranslateAnimation(one, 0,0,0);
						}else if(currIndex==2){
							animation = new TranslateAnimation(2*one, 0, 0, 0);
						}else{
							animation = new TranslateAnimation(3*one, 0,0,0);
						}
						break;
					case 1:
						if(currIndex==0){
							animation = new TranslateAnimation(0, one,0,0);
						}else if(currIndex==2){
							animation = new TranslateAnimation(2*one, one, 0, 0);
						}else{
							animation = new TranslateAnimation(3*one, one, 0, 0);
						}
						break;
					case 2:
						if(currIndex==0){
							animation = new TranslateAnimation(0, 2*one,0,0);
						}else if(currIndex==1){
							animation = new TranslateAnimation(one, 2*one, 0, 0);
						}else{
							animation = new TranslateAnimation(3*one, 2*one, 0, 0);
						}
						break;
					case 3:
						if(currIndex==0){
							animation = new TranslateAnimation(0, 3*one,0,0);
						}else if(currIndex==1){
							animation = new TranslateAnimation(one, 3*one, 0, 0);
						}else{
							animation = new TranslateAnimation(2*one, 3*one, 0, 0);
						}
						break;
					}
					currIndex=arg0;	
					animation.setFillAfter(true);//图片在动画位置结束停止
					animation.setDuration(300);
					cursor.startAnimation(animation);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	      return view;
	  }  
	  private void initPager(LayoutInflater l){
			vp = (ViewPager) view.findViewById(R.id.viewpager1);

			mfragment1 = new 
					Activity_Viewpager_fragment1();
			mfragment1.topImageView=background;
			mfragment1.width=width;
			mfragment2= new 
					Activity_Viewpager_fragment2();
			
			
			mfragment3 = new 
					Activity_Viewpager_fragment3();
			
			
			mfragment4 = new 
					Activity_Viewpager_fragment4();
      		fragmentList = new ArrayList<Fragment>();
			fragmentList.add(mfragment1);
			fragmentList.add(mfragment2);
			fragmentList.add(mfragment3);
			fragmentList.add(mfragment4);
			
			//注意设置viewpager当前选项要放在适配之后
			vp.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
			vp.setCurrentItem(0);
	  }
	   
	  public void onActivityCreated(Bundle savedInstanceState) {  
	      super.onActivityCreated(savedInstanceState);  
	      tv = (TextView) getView().findViewById(R.id.titleTv);  
	      tv.setText("活动");  
	  }  
	  private void InitImageView() {
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenW = dm.widthPixels;// 获取分辨率宽度
			offset = screenW/4;// 计算偏移量
			Matrix matrix = new Matrix();
			matrix.setTranslate(0, 0);
			cursor.setScaleType(ScaleType.MATRIX);
			cursor.setImageMatrix(matrix);  //设置动画初始位置
		}
	  private void findView(){
			cursor = (ImageView) view.findViewById(R.id.cursor);
			tab1 = (ImageView)view.findViewById(R.id.qiandao_iv);
			tab2 = (ImageView) view.findViewById(R.id.duobao_iv);
			tab3 = (ImageView) view.findViewById(R.id.paimai_iv);
			tab4 = (ImageView) view.findViewById(R.id.tuangou_iv);
			tab1.setOnClickListener(new OnClickListener(0));
			tab2.setOnClickListener(new OnClickListener(1));
			tab3.setOnClickListener(new OnClickListener(2));
			tab4.setOnClickListener(new OnClickListener(3));
			back = (ImageView)  view.findViewById(R.id.back);
			background = (ImageView) view.findViewById(R.id.background);
			ImageView imageView=(ImageView)view.findViewById(R.id.title_btn);
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				    getActivity().finish();	
				}
			});
		}
		class OnClickListener implements android.view.View.OnClickListener{
			private int index=0;
			public OnClickListener(int i){
				index=i;
			}
			@Override
			public void onClick(View v) {
				vp.setCurrentItem(index);
			}
			
		}
	  class MyPagerAdapter extends FragmentPagerAdapter{
			public MyPagerAdapter(FragmentManager fm) {
				super(fm);
			}
			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
			@Override
			public int getCount() {
				return fragmentList.size();
			}

			
		}
}
