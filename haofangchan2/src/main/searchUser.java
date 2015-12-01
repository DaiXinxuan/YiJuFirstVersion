package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import setting.Handle_input_dialog;
import setting.Input_denfined_dialog.ClickListenerInterface;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.AddFriendJsonParse;
import different_jsonparse.State_parse_Json;
import differentjavabean.AddFriendModel;
import differentjavabean.searchuserJavabean;

public class searchUser extends Activity {

	private EditText phoneNumber = null;// 接收手机号码
	private TextView cancel = null;// 取消按钮
	private searchuserJavabean data = null;
	private TextView name = null;// 搜索结果
	private Button add = null;// 添加好友
	private Map<String, String> map = null;// 存储向服务器发送的数据
	private AddFriendModel addFriendModel;
	private String phonenumberString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_user);
		initView();// 初始化组件及视图
		cancel.setOnClickListener(new OnClickListener() {
			// 取消按钮
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		phoneNumber.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				LogUtil.d("键盘输入", "输入");
				initData();
				return false;
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 添加好友数据

				final EditText inputEditText = new EditText(searchUser.this);
				inputEditText.setFocusable(true);
				final Handle_input_dialog hd = new Handle_input_dialog(
						searchUser.this, "请输入好友备注（可为空）", "确认");
				hd.dialog.setClicklistener(new ClickListenerInterface() {

					@Override
					public void doConfirm() {
						// TODO Auto-generated method stub
						String inputName = hd.dialog.inputstr;
						map = new HashMap<String, String>();
						LogUtil.d("BBBBBBB",
								"111" + addFriendModel.getNicknameString());
						if (!(inputName.equals(""))) {
							LogUtil.d("BBBBBBB",
									"111" + addFriendModel.getNicknameString());
							map = JSONCommand.JSON10040("6", "1", inputName,
									addFriendModel.getFriendidString(),
									phonenumberString);

						} else {
							LogUtil.d("BBBBBBB",
									"111" + addFriendModel.getNicknameString());
							map = JSONCommand.JSON10040("6", "1",
									addFriendModel.getNicknameString(),
									addFriendModel.getFriendidString(),
									phonenumberString);
						}
						LogUtil.d("BBBBBBB", addFriendModel.getNicknameString());
						ServerAsyncHttpTask.execute(map,
								new UpdateUIInterface() {
									@Override
									public void updateUI(Object model) {
										Boolean state = (Boolean) model;
										if (state) {
											LogUtil.d(
													"searchuser",
													"11122"
															+ addFriendModel
																	.getNicknameString());
											setResult(2);
											Toast.makeText(searchUser.this,
													"已发送添加好友请求，请稍候",
													Toast.LENGTH_SHORT).show();
											// searchUser.this.finish();
										} else {
											Toast.makeText(searchUser.this,
													"发送失败,服务器繁忙，请稍后再试",
													Toast.LENGTH_SHORT).show();
										}
									}
								}, new State_parse_Json());
						hd.dialog.dismiss();
					}

					@Override
					public void doCancel() {
						// TODO Auto-generated method stub
						hd.dialog.dismiss();
					}
				});
				hd.cleardialog();
				// AlertDialog.Builder builder=new
				// AlertDialog.Builder(searchUser.this);
				// builder.setTitle("请输入好友备注（可为空）")
				// .setView(inputEditText)
				// .setNegativeButton("取消", null)
				// .setPositiveButton("确定", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// // TODO Auto-generated method stub
				// String inputName=inputEditText.getText().toString();
				// map = new HashMap<String, String>();
				// LogUtil.d("BBBBBBB",
				// "111"+addFriendModel.getNicknameString());
				// if(!(inputName.equals(""))){
				// LogUtil.d("BBBBBBB",
				// "111"+addFriendModel.getNicknameString());
				// map=JSONCommand.JSON10040("6", "1",
				// inputName,addFriendModel.getFriendidString(),phonenumberString);
				//
				// }else{
				// LogUtil.d("BBBBBBB",
				// "111"+addFriendModel.getNicknameString());
				// map=JSONCommand.JSON10040("6", "1",
				// addFriendModel.getNicknameString(),addFriendModel.getFriendidString(),phonenumberString);
				// }
				// LogUtil.d("BBBBBBB", addFriendModel.getNicknameString());
				// ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
				// @Override
				// public void updateUI(Object model) {
				// Boolean state=(Boolean)model;
				// if(state){
				// LogUtil.d("searchuser",
				// "11122"+addFriendModel.getNicknameString());
				// setResult(2);
				// Toast.makeText(searchUser.this, "已发送添加好友请求，请稍候",
				// Toast.LENGTH_SHORT).show();
				// //searchUser.this.finish();
				// }else{
				// Toast.makeText(searchUser.this, "发送失败,服务器繁忙，请稍后再试",
				// Toast.LENGTH_SHORT).show();
				// }
				// }
				// }, new State_parse_Json() );
				// }
				// }).show();

			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		map = new HashMap<String, String>();
		map = JSONCommand.JSON10061("6", "1", phoneNumber.getText().toString());

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				addFriendModel = (AddFriendModel) model;
				name.setText(addFriendModel.getNicknameString() + "");
				phonenumberString = phoneNumber.getText().toString();
				if (addFriendModel.getIsFriendString().equals("false")) {
					add.setVisibility(View.VISIBLE);
					add.setBackgroundResource(R.drawable.search_user_addbutton);
					add.setClickable(true);
				} else {
					Toast.makeText(searchUser.this, "您已经添加该好友！",
							Toast.LENGTH_SHORT).show();
					add.setVisibility(View.VISIBLE);
					add.setBackgroundResource(R.drawable.nosearch);
					add.setTextColor(Color.WHITE);
					add.setClickable(false);
				}
			}
		}, new AddFriendJsonParse());

	}

	private void initView() {
		// TODO Auto-generated method stub
		phoneNumber = (EditText) findViewById(R.id.search_user_phoneNumber);
		cancel = (TextView) findViewById(R.id.search_user_back);
		name = (TextView) findViewById(R.id.search_user_name);
		add = (Button) findViewById(R.id.search_user_add);
		add.setVisibility(View.GONE);
	}

}
