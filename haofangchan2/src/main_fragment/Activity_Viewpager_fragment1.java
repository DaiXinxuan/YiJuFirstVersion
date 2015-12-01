package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import web.WebActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.Activity_Sign_jsonparse;
import different_jsonparse.State_parse_Json;
import differentjavabean.Activity_Sign_javabean;

public class Activity_Viewpager_fragment1 extends Fragment {
//	private ScrollView ll;
	private RelativeLayout rl;
	private TextView info, time, prize, introduce, signday, text;
	private Activity_Sign_javabean data;
	private View view;
	private Boolean state;
	public ImageView topImageView;
	public int width;

	public void updateData() {
		Map<String, String> map = JSONCommand.JSON10037("6", "1");

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				data = (Activity_Sign_javabean) model;
				if (data.getActivatyid() != null) {
					info.setText(data.getInstruction());
					String s1 = data.getContent().substring(0, Integer.parseInt(data.getNameLength()));
					String s2 = data.getContent().substring(Integer.parseInt(data.getNameLength()));
					SpannableString styledText = new SpannableString(s1+"\n"+s2+ "\n————点击查看详情");
					styledText.setSpan(
							new ForegroundColorSpan(Color.rgb(255, 146, 36)),
							0, Integer.parseInt(data.getNameLength()),
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					introduce.setText(styledText);
					time.setText(data.getDate());
					prize.setText(data.getAward());
					signday.setText("签到总天数：" + data.getSignday());
					if (data.getSignState().equals("true")) {
						rl.setClickable(false);
						rl.setBackgroundResource(R.drawable.graybutton);
						text.setText("您已签到");
					}
					InitPicture_setting.getImageLoader(
							data.getPictureAddress(), topImageView);
					LinearLayout.LayoutParams layoutParams = (LayoutParams) topImageView
							.getLayoutParams();
					layoutParams.width = width;
					topImageView.setLayoutParams(layoutParams);
				} else {
					Toast.makeText(getActivity(), "当前没有签到活动",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new Activity_Sign_jsonparse());

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activityfragment_viewpagerfragment1, container, false);
		initView();

		updateData();
		return view;
	}

	private void initView() {
		topImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), WebActivity.class);
				i.putExtra("url", data.getTopUrl());
				i.putExtra("title", "精彩活动");
				startActivity(i);
			}
		});
//		ll= (ScrollView) view.findViewById(R.id.qiandao_ll);
		
		rl = (RelativeLayout) view.findViewById(R.id.sign_button);
		info = (TextView) view.findViewById(R.id.acti_info);
		time = (TextView) view.findViewById(R.id.acti_time);
		prize = (TextView) view.findViewById(R.id.acti_prize);
		introduce = (TextView) view
				.findViewById(R.id.acti_introduce_viewpagerfragment1);
		introduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), WebActivity.class);
				i.putExtra("url", data.getUrl());
				i.putExtra("title", "签到详情");
				startActivity(i);
			}
		});
		signday = (TextView) view.findViewById(R.id.sign_days);
		text = (TextView) view.findViewById(R.id.text);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;// 获取分辨率宽度
		int height = dm.heightPixels;// 获取分辨率高度
//		LinearLayout.LayoutParams lp = (LayoutParams) ll.getLayoutParams();
//		lp.height = (int) (0.15 * height);
//		ll.setLayoutParams(lp);
		rl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Map<String, String> map = new HashMap<String, String>();
				map = JSONCommand.JSON10039("6", "1", data.getActivatyid());
				ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

					public void updateUI(Object model) {
						state = (boolean) model;
						if (state) {
							updateData();
							Toast.makeText(MyApplication.getContext(), "签到成功！",
									Toast.LENGTH_SHORT).show();
							rl.setClickable(false);
							rl.setBackgroundResource(R.drawable.graybutton);
							text.setText("您已签到");
						} else {

							Toast.makeText(MyApplication.getContext(),
									"签到失败，服务器异常！", Toast.LENGTH_SHORT).show();
						}
					}
				}, new State_parse_Json());

			}
		});
	}

}
