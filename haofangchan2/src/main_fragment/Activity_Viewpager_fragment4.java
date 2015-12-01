package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import pictureconnectinit.InitPicture_setting;

import testandmanage.HttpRequest;
import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import web.WebActivity;

import load.LoginActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import connect.Http_Connection;
import different_jsonparse.Activity_Groupbuying_jsonparse;
import different_jsonparse.GroupBuyParser;
import differentjavabean.Activity_Groupbuying_javabean;
import differentjavabean.GroupBuyModel;

public class Activity_Viewpager_fragment4 extends Fragment {
	private Button b;
	private View view;
	private TextView name, personnum, instruction, time, content;
	private GroupBuyModel gbm;
	private ImageView picture;


	private void updateUI() {
		Map<String, String> map = JSONCommand.JSON10038("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				gbm = (GroupBuyModel)model;
				
				SpannableString styledText = new SpannableString("活动名称："+gbm.getName());
				styledText.setSpan(
						new ForegroundColorSpan(Color.rgb(255, 146, 36)),
						0, "活动名称：".length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				name.setText(styledText);
				personnum.setTextColor(Color.BLACK);
				personnum.setText("已有");
				personnum.setTextColor(Color.CYAN);
				personnum.setText(personnum.getText()
						+ gbm.getPersonNum());
				personnum.setTextColor(Color.BLACK);
				personnum.setText(personnum.getText() + "人参加");
				
				SpannableString styledText1 = new SpannableString("活动内容：\n"+gbm.getContent());
				styledText1.setSpan(
						new ForegroundColorSpan(Color.rgb(255, 146, 36)),
						0, "活动内容：".length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				instruction.setText(styledText1);
				
				String timeString=gbm.getTime();
				timeString=timeString.replace("+", " ");
				time.setText(timeString);
				content.setText(gbm.getInstruction());
				InitPicture_setting.getImageLoader(gbm.getPhotoPath(), picture);	
			}
		}, new GroupBuyParser());
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activityfragment_viewpagerfragment4, container, false);
		initView();
        updateUI();
		return view;
	}

	private void initView() {
		name = (TextView) view.findViewById(R.id.activi_name);
		personnum = (TextView) view.findViewById(R.id.join_person_count);
		instruction = (TextView) view.findViewById(R.id.activi_intro);
		time = (TextView) view.findViewById(R.id.acti_time);
		content = (TextView) view.findViewById(R.id.acti_des);
		picture = (ImageView) view.findViewById(R.id.group_iv);
		picture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),WebActivity.class);
				intent.putExtra("title", "低价团购");
				intent.putExtra("url", gbm.getHtml());
				startActivity(intent);
			}
		});
		b = (Button) view.findViewById(R.id.group_button);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), GroupInfo.class);
				i.putExtra("groupid", gbm.getActivityid());
				startActivity(i);
			}
		});
		
	}

}
