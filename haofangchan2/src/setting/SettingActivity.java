package setting;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;

import org.litepal.crud.DataSupport;

import setting.Logic_defined_dialog.ClickListenerInterface;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import web.WebActivity;

import com.easemob.chat.EMChatManager;
import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import different_adapter.SettingAdapter;
import different_jsonparse.AboutUsParser;
import differentjavabean.AboutUsModel;
import differentjavabean.MyfriendActivity_javabean;

import load.LoginActivity;
import load.PasswordLost;
import main.MainFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	private ListView list1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		list1 = (ListView) findViewById(R.id.list1);
		SettingAdapter adapter = new SettingAdapter();
		list1.setAdapter(adapter);
		list1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (parent.getAdapter().getItem(position).toString()) {

				case "清除缓存":
					final Handle_defined_dialog hd = new Handle_defined_dialog(SettingActivity.this, "清除缓存",
							"确认清除缓存?", "确认", "取消");
					hd.dialog.setClicklistener(new ClickListenerInterface() {
						public void doConfirm() {
							// TODO Auto-generated method stub
							ImageLoader loader = ImageLoader.getInstance();
							loader.clearMemoryCache();
							loader.clearDiskCache();
							LogUtil.d("Setting", "true");
							hd.dialog.dismiss();
							Toast.makeText(getApplicationContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
						}
						public void doCancel() {
							// TODO Auto-generated method stub
							LogUtil.d("Setting", "false");
							hd.dialog.dismiss();
						}
					});
					hd.cleardialog();
					break;
				case "修改密码":
					Intent changepassword = new Intent(getApplicationContext(),
							PasswordLost.class);
					changepassword.putExtra("change", "1");
					startActivity(changepassword);
					break;

				case "用户反馈":
					Intent feedback = new Intent(getApplicationContext(),
							Feedback.class);
					startActivity(feedback);
					break;
				case "关于我们":
					HashMap<String, String> map = JSONCommand.JSON10020("", "", "", "");
					ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
						@Override
						public void updateUI(Object model) {
							// TODO Auto-generated method stub
							AboutUsModel amodel = (AboutUsModel) model;
							Intent walk = new Intent(SettingActivity.this,WebActivity.class);
							walk.putExtra("url", amodel.getHtml());
							walk.putExtra("title", "关于我们");
							startActivity(walk);
						}
					}, new AboutUsParser());
					break;
				}
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_title_btn:
//			Intent load = new Intent(getApplicationContext(),
//					MainFragmentActivity.class);
//			load.putExtra("backactivity", 3);
//			startActivity(load);
			SettingActivity.this.finish();
			break;
		case R.id.exit:
			final Handle_defined_dialog hd = new Handle_defined_dialog(SettingActivity.this, "退出登录",
					"是否退出登录？", "确认", "取消");
			hd.dialog.setClicklistener(new ClickListenerInterface() {
				public void doConfirm() {
					// TODO Auto-generated method stub
					EMChatManager.getInstance().logout();
					SharedPreferences sp = MyApplication
							.getSharedPreferences();
					Editor editor = sp.edit();
					editor.remove("payid");
					editor.clear();
					editor.commit();
					Intent exit = new Intent(getApplicationContext(),
							LoginActivity.class);
					exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					SettingActivity.this.startActivity(exit);
					DataSupport
							.deleteAll(MyfriendActivity_javabean.class);
					LogUtil.d("Setting", "true");
					hd.dialog.dismiss();
				}
				public void doCancel() {
					// TODO Auto-generated method stub
					LogUtil.d("Setting", "false");
					hd.dialog.dismiss();
				}
			});
			hd.cleardialog();
			break;

		}
	}

}
