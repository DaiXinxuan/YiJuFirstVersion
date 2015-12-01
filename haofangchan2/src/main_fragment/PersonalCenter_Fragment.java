package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import main.AttentionRoom_Activity;
import main.CommnetActivity;
import main.LatestNewsActivity;
import main.MessageCenterActivity;
import main.MyActivitiesActivity;
import main.OrderWatchingItem_Activity;
import main.PersonalProfileActivity;
import main.ReceiveMagazineActivity;
import main.ZhiyeguwenActivity;
import pictureconnectinit.InitPicture_setting;
import setting.SettingActivity;
import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.example.haofangchan2.R;
import de.hdodenhof.circleimageview.CircleImageView;
import different_jsonparse.PersonalCenterModelParser;
import differentjavabean.PersonalCenterModel;

public class PersonalCenter_Fragment extends Fragment {
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		updateUi();
		super.onResume();
	}

	private TextView tv;
	private RelativeLayout setting, username, changesale, magazine,
			messageCenter, last;
	private Map<String, String> map;
	private PersonalCenterModel pcm;
	private TextView username_txt, sex, logindata, introduce, txt1;
	private CircleImageView image;
	private ImageView img1, img2, img3;

	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_layout4, container, false);
		init();
		updateUi();

		return view;
	}

	private void updateUi() {
		HashMap<String, String> map = new HashMap<String, String>();
		map = JSONCommand.JSON10022(MyApplication.getpayid());
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				pcm = (PersonalCenterModel) model;
				username_txt.setText(pcm
						.getNickName());
				EMChatManager.getInstance().updateCurrentUserNick(pcm.getNickName());
				String sexString = null;
				if (pcm.getSex().equals("1")) {
					sexString = "男";
				} else if (pcm.getSex().equals("2")){
					sexString = "女";
				} else sexString = " ";
				sex.setText(sexString);
				logindata.setText("上次登录时间："+pcm.getLoginDate());
				introduce.setText(pcm.getIntroduce());
				if(pcm.getHeadPhoto()!=null){
					InitPicture_setting.getImageLoader(
							pcm.getHeadPhoto(), image);
				}
				
			}
		}, new PersonalCenterModelParser());

	}

	private void init() {
		
		ImageView imageView=(ImageView)view.findViewById(R.id.title_btn);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    getActivity().finish();	
			}
		});
		
		
		setting = (RelativeLayout) view
				.findViewById(R.id.setting_personalcenter);
		setting.setOnClickListener(listener);
		username = (RelativeLayout) view
				.findViewById(R.id.personalcenter_username);
		username.setOnClickListener(listener);
		changesale = (RelativeLayout) view
				.findViewById(R.id.change_sale_activityfragment4);
		changesale.setOnClickListener(listener);
		magazine = (RelativeLayout) view
				.findViewById(R.id.magazine_personalsetting);
		magazine.setOnClickListener(listener);
		messageCenter = (RelativeLayout) view.findViewById(R.id.messageCenter);
		messageCenter.setOnClickListener(listener);
		last = (RelativeLayout) view.findViewById(R.id.change_saler);
		last.setOnClickListener(listener);
		img1 = (ImageView) view.findViewById(R.id.tab04image01);
		img1.setOnClickListener(listener);
		img2 = (ImageView) view.findViewById(R.id.tab04image02);
		img2.setOnClickListener(listener);
		img3 = (ImageView) view.findViewById(R.id.tab04image03);
		img3.setOnClickListener(listener);
		image = (CircleImageView) view.findViewById(R.id.profile_image);
		username_txt = (TextView) view.findViewById(R.id.main_username);
		sex = (TextView) view.findViewById(R.id.sex_personalcenter);
		logindata = (TextView) view.findViewById(R.id.main_gender);
		introduce = (TextView) view.findViewById(R.id.main_signature);

	}

	OnClickListener listener = new OnClickListener() {

		Intent intent;

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.setting_personalcenter:
				intent = new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);
				break;
			case R.id.personalcenter_username:
				intent = new Intent(getActivity(),
						PersonalProfileActivity.class);
				intent.putExtra("photo", pcm.getHeadPhoto());
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			case R.id.change_sale_activityfragment4:
				intent = new Intent(getActivity(),
						OrderWatchingItem_Activity.class);
				startActivity(intent);
				break;
			case R.id.magazine_personalsetting:
				intent = new Intent(getActivity(),
						ReceiveMagazineActivity.class);
				startActivity(intent);
				break;
			case R.id.tab04image01:
				intent = new Intent(getActivity(), MyActivitiesActivity.class);
				startActivity(intent);
				break;
			case R.id.tab04image02:
				intent = new Intent(getActivity(), AttentionRoom_Activity.class);
				startActivity(intent);
				break;
			case R.id.tab04image03:
				intent = new Intent(getActivity(), CommnetActivity.class);
				intent.putExtra("isMyComment", true);
				startActivity(intent);
				break;
			case R.id.messageCenter:
				intent = new Intent(getActivity(), MessageCenterActivity.class);
				startActivity(intent);
				break;
			case R.id.change_saler:
				intent = new Intent(getActivity(), ZhiyeguwenActivity.class);
				startActivity(intent);
			default:
				break;
			}

		}

	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tv = (TextView) getView().findViewById(R.id.titleTv);
		tv.setText("个人中心");
	}

}
