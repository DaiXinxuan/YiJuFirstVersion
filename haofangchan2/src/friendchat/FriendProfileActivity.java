package friendchat;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import de.hdodenhof.circleimageview.CircleImageView;
import different_jsonparse.FriendMessageJsonParse;
import different_jsonparse.State_parse_Json;
import differentjavabean.FriendMessageModel;

public class FriendProfileActivity extends Activity {
	private ImageView goback;
	private Button delete;
	private TextView name, sex, sign, remark, save;
	private EditText edit;
	private CircleImageView headphotoCircleImageView;
	private String useridString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.myfriendprofile);
		name = (TextView) findViewById(R.id.friendpro_name);
		sex = (TextView) findViewById(R.id.friendpro_sex);
		sign = (TextView) findViewById(R.id.friendpro_sign);
		remark = (TextView) findViewById(R.id.friendpro_remark);
		headphotoCircleImageView = (CircleImageView) findViewById(R.id.friendMessageHeadPhoto);
		goback = (ImageView) findViewById(R.id.friendpro_back);
		useridString = getIntent().getStringExtra("userid");
		save = (TextView) findViewById(R.id.friendpro_save);
		edit = (EditText) findViewById(R.id.friendpro_remarkedit);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Map<String, String> map;
				map = JSONCommand.JSON10045("6", "1", useridString, edit
						.getText().toString());
				ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

					@Override
					public void updateUI(Object model) {

						Boolean stateBoolean = (Boolean) model;
						if (stateBoolean) {

							Toast.makeText(getApplicationContext(), "修改备注成功！",
									Toast.LENGTH_SHORT).show();
							setResult(RESULT_OK);
							FriendProfileActivity.this.finish();

						} else {
							Toast.makeText(getApplicationContext(),
									"服务器繁忙，修改备注失败！", Toast.LENGTH_SHORT).show();
						}

					}
				}, new State_parse_Json());
			}
		});
		goback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		delete = (Button) findViewById(R.id.friendpro_delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Map<String, String> map;
				map = JSONCommand.JSON10035("6", "1", useridString, getIntent()
						.getStringExtra("phonenumber"));

				ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

					@Override
					public void updateUI(Object model) {

						Boolean stateBoolean = (Boolean) model;
						if (stateBoolean) {

							Toast.makeText(getApplicationContext(), "删除好友成功！",
									Toast.LENGTH_SHORT).show();
							setResult(RESULT_OK);
							FriendProfileActivity.this.finish();

						} else {
							Toast.makeText(getApplicationContext(),
									"服务器繁忙，删除好友失败！", Toast.LENGTH_SHORT).show();
						}

					}
				}, new State_parse_Json());

			}
		});

		Map<String, String> map;
		if (getIntent().getStringExtra("isVip").equals("1")) {
			map = JSONCommand.JSON10034("6", "1", useridString, useridString);
		} else {
			map = JSONCommand.JSON10034("6", "1", useridString, "0");
		}

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				FriendMessageModel friendMessageModel = (FriendMessageModel) model;
				InitPicture_setting.getImageLoader(
						friendMessageModel.getHeadphotopathString(),
						headphotoCircleImageView);
				name.setText("昵		称:     "
						+ friendMessageModel.getNicknameString());
				sex.setText("性		别:     " + friendMessageModel.getSexString());
				remark.setText("备    注:     ");
				edit.setText(friendMessageModel.getRemarknameString());

				sign.setText("签		名:     "
						+ friendMessageModel.getContentString());
				if (getIntent().getStringExtra("isVip").equals("1")) {
					delete.setBackgroundColor(Color.GRAY);
					delete.setClickable(false);
					save.setVisibility(View.GONE);
					save.setClickable(false);
					edit.setFocusable(false);
					edit.setFocusableInTouchMode(false);
				}
			}
		}, new FriendMessageJsonParse());

	}
}
