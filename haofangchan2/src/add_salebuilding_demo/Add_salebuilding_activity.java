package add_salebuilding_demo;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import load.LoginActivity;
import main.MainFragmentActivity;
import scanzbar.ScanzbarActivity;
import setting.Handle_defined_dialog;
import setting.Logic_defined_dialog.ClickListenerInterface;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_adapter.Add_salebuilding_adapter;
import different_jsonparse.PictureList_parse_Json;
import different_jsonparse.State_parse_Json;
import differentjavabean.Add_salebuilding_picture_data_javabean;

public class Add_salebuilding_activity extends Activity {
	private GridView gridview = null;
	private Map<String, String> map = null;
	private ImageView titlebtn,back;
	public List<Add_salebuilding_picture_data_javabean> picture_url_list;
	private List<Integer> proIds;
	private int itemClickIndex;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_salebuilding_layout);
		LogUtil.d("add_salebuilding", "已跳转");
		gridview = (GridView) findViewById(R.id.add_sale_grid_view);
		titlebtn = (ImageView) findViewById(R.id.title_btn_scannar1);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent load=new Intent(getApplicationContext(),LoginActivity.class);
				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(load);
				Add_salebuilding_activity.this.finish();
			}
		});
		titlebtn.setOnClickListener(listener);

		initMap();
		initGridViewData();
		if (MyApplication.getpayid() != null) {
			gridview.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(getApplicationContext(),
							MainFragmentActivity.class);
					MyApplication.setProid(picture_url_list.get(position)
							.getId());
					intent.putExtra("backactivity", 0);
					Add_salebuilding_activity.this.startActivity(intent);
				}
			});

			gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					itemClickIndex = arg2;
					final Handle_defined_dialog hd = new Handle_defined_dialog(
							Add_salebuilding_activity.this, "提示", "是否删除该售楼部",
							"确认", "取消");
					hd.dialog.setClicklistener(new ClickListenerInterface() {
						public void doConfirm() {
							// TODO Auto-generated method stub
							Map<String, String> map = new HashMap<String, String>();
							map = JSONCommand.JSON10006(
									picture_url_list.get(itemClickIndex)
											.getId(), MyApplication.getpayid());
							ServerAsyncHttpTask.execute(map,
									new UpdateUIInterface() {
										@Override
										public void updateUI(Object model) {
											Boolean state = (Boolean) model;
											LogUtil.d("结果为", "" + state);
											if (state == true) {
												initGridViewData();
												hd.dialog.dismiss();
											} else {
												Toast.makeText(
														Add_salebuilding_activity.this,
														"服务器繁忙，删除失败！",
														Toast.LENGTH_SHORT)
														.show();
												hd.dialog.dismiss();
											}

										}
									}, new State_parse_Json());

						}

						public void doCancel() {
							// TODO Auto-generated method stub
							LogUtil.d("Setting", "false");
							hd.dialog.dismiss();
						}

					});
					hd.cleardialog();
					// AlertDialog alertBuilder = new AlertDialog.Builder(
					// Add_salebuilding_activity.this)
					// .setMessage("是否删除该售楼部")
					// .setNegativeButton("确定",
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(
					// DialogInterface arg0, int arg1) {
					// Map<String, String> map = new HashMap<String, String>();
					// map = JSONCommand.JSON10006(
					// picture_url_list.get(
					// itemClickIndex)
					// .getId(),
					// MyApplication.getpayid());
					// ServerAsyncHttpTask.execute(map,
					// new UpdateUIInterface() {
					// @Override
					// public void updateUI(
					// Object model) {
					// Boolean state = (Boolean) model;
					// LogUtil.d("结果为", ""
					// + state);
					// if (state == true) {
					// initGridViewData();
					// } else {
					// Toast.makeText(
					// Add_salebuilding_activity.this,
					// "服务器繁忙，删除失败！",
					// Toast.LENGTH_SHORT)
					// .show();
					// }
					//
					// }
					// }, new State_parse_Json());
					// }
					// }).setNeutralButton("取消", null).create();
					// alertBuilder.show();
					return true;
				}
			});
		}

	}

	private void initGridViewData() {
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@SuppressWarnings("unchecked")
			@Override
			public void updateUI(Object model) {

				picture_url_list = (List<Add_salebuilding_picture_data_javabean>) model;
				proIds = new ArrayList<Integer>();
				for (int i = 0; i < picture_url_list.size(); i++) {
					Add_salebuilding_picture_data_javabean data = picture_url_list
							.get(i);
					proIds.add(Integer.parseInt(data.getId()));
				}

				LogUtil.d("售房部界面数据长度", "" + picture_url_list.size());
				Add_salebuilding_adapter adapter = new Add_salebuilding_adapter(
						picture_url_list);
				gridview.setAdapter(adapter);

			}
		}, new PictureList_parse_Json());
	}

	OnClickListener listener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_btn_scannar1:
				Intent intent = new Intent(getApplicationContext(),
						ScanzbarActivity.class);
				Add_salebuilding_activity.this
						.startActivityForResult(intent, 0);
				break;
			}
		}
	};

	public void initMap() {
		map = new HashMap<String, String>();
		map.put("type", "10005");
		map.put("payid", MyApplication.getpayid());
	}

	private Boolean isRepeateScanBoolean;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		isRepeateScanBoolean = false;
		LogUtil.d("添加售楼部的proid", "为" + resultCode);
		if (resultCode != -1) {

			if (requestCode == 0) {
				int proid = resultCode;
				for (int i = 0; i < proIds.size(); i++) {
					int proidInt = proIds.get(i);
					if (proid == proidInt) {
						isRepeateScanBoolean = true;
					}
				}
				if (!isRepeateScanBoolean) {
					Map<String, String> map = JSONCommand.JSON10007(proid + "",
							MyApplication.getpayid());

					ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

						@Override
						public void updateUI(Object model) {
							// TODO Auto-generated method stub
							Boolean state = (Boolean) model;
							LogUtil.d("结果为", "" + state);
							if (state == true) {
								initGridViewData();
							} else {
								Toast.makeText(Add_salebuilding_activity.this,
										"服务器繁忙，添加失败！", Toast.LENGTH_SHORT)
										.show();
							}
						}
					}, new State_parse_Json());

				} else {
					Toast.makeText(Add_salebuilding_activity.this,
							"您已添加该售楼部，请不要重复添加", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}
}
