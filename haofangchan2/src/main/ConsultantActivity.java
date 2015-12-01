package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pictureconnectinit.InitPicture_setting;
import setting.Handle_defined_dialog;
import setting.Logic_defined_dialog.ClickListenerInterface;
import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_adapter.ConsultcommentAdapter;
import different_jsonparse.SalerParser;
import different_jsonparse.State_parse_Json;
import differentjavabean.SalerModel;

public class ConsultantActivity extends Activity implements
		OnPageChangeListener {
	ImageButton goback;
	ImageView call, img, sex;
	ImageView add, comment, wantcom;
	TextView name, age, company, introduct, job, phone, number, rate;
	ListView lv;
	ScrollView sv;
	private ArrayList<String> imagearray;
	ConsultcommentAdapter adapter;
	// Zhiyeguwen_Detail_javabean data;
	SalerModel sm;
	private ViewPager vp;// 首页滑动图片
	private List<View> mIV;// 图片数组
	private ImageView[] tips;// 点点数组
	private ViewGroup vg;
	private boolean flag = false;
	private boolean isContinue = true;

	public void setgone() {
		vp.setVisibility(View.GONE);
		vg.setVisibility(View.GONE);
		flag = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateUI();
		super.onResume();
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.consultantview);
		init();
		updateUI();
	}

	public void updateUI() {
		HashMap<String, String> map = JSONCommand.JSON10044("6", "1",
				getIntent().getStringExtra("salerid"));
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// 为每个控件添加数据
				sm = (SalerModel) model;
				name.setText("姓名:" + sm.getName());
				age.setText("年龄:" + sm.getAge());
				phone.setText("电话" + sm.getTel());
				company.setText("销售公司:" + sm.getCompany());
				introduct.setText("个人介绍:" + sm.getIntroduction());
				job.setText("管理人数:" + sm.getPosition());
				int n = sm.getBadLevel() + sm.getGoodLevel() + sm.getMidLevel();
				number.setText("共计" + n + "条评论");
				rate.setTextColor(Color.RED);
				double m = (double) sm.getGoodLevel()
						/ (double) (sm.getBadLevel() + sm.getMidLevel() + sm
								.getGoodLevel());
				m = Math.round(m * 100);
				rate.setText("好评率" + m + "%");
				InitPicture_setting.getImageLoader(sm.getHeadPhoto(), img);
				imagearray = new ArrayList<String>();
				imagearray.add(sm.getHeadPhoto());
				if (sm.getSex().equals("1"))
					sex.setBackgroundResource(R.drawable.male);
				else
					sex.setBackgroundResource(R.drawable.female);
				initViewPager();
			}
		}, new SalerParser());
	}

	private void init() {
		img = (ImageView) findViewById(R.id.consult_img);
		name = (TextView) findViewById(R.id.consult_name);
		age = (TextView) findViewById(R.id.consult_age);
		phone = (TextView) findViewById(R.id.consult_phone);
		company = (TextView) findViewById(R.id.consult_company);
		introduct = (TextView) findViewById(R.id.consult_introduction);
		job = (TextView) findViewById(R.id.consult_job);
		add = (ImageView) findViewById(R.id.choose);
		comment = (ImageView) findViewById(R.id.watchcomment);
		wantcom = (ImageView) findViewById(R.id.iwantcomment);
		goback = (ImageButton) findViewById(R.id.consult_goback);
		number = (TextView) findViewById(R.id.consult_numberofcomment);
		rate = (TextView) findViewById(R.id.consult_rate);
		call = (ImageView) findViewById(R.id.consult_call);
		sv = (ScrollView) findViewById(R.id.consult_scrollview);
		sv.smoothScrollTo(0, 0);
		vg = (ViewGroup) findViewById(R.id.consultant_viewGroup);
		vp = (ViewPager) findViewById(R.id.consultant_viewPager);
		sex = (ImageView) findViewById(R.id.consult_sex);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flag = true;
				vp.setVisibility(View.VISIBLE);
				vg.setVisibility(View.VISIBLE);
			}
		});
		goback.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setResult(1);
				finish();
			}
		});
		call.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ "" + sm.getTel()));
				startActivity(intent);
			}
		});
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!sm.getState().equals("true")) {
					if (getIntent().getBooleanExtra("hasSaler", false)) {
						final Handle_defined_dialog hd = new Handle_defined_dialog(
								ConsultantActivity.this, "确认选择",
								"您已添加置业顾问，是否变换" + sm.getName() + "成为您的置业顾问。",
								"确认", "取消");
						hd.dialog
								.setClicklistener(new ClickListenerInterface() {
									public void doConfirm() {
										// TODO Auto-generated method stub
										changeSaler();
										hd.dialog.dismiss();
									}

									public void doCancel() {
										// TODO Auto-generated method stub
										hd.dialog.dismiss();
									}
								});
						hd.cleardialog();
						// new AlertDialog.Builder(ConsultantActivity.this)
						// .setTitle("确认选择")// 设置对话框标题
						// .setMessage(
						// "您已添加置业顾问，是否变换" + sm.getName()
						// + "成为您的置业顾问。")// 设置显示的内容
						// .setPositiveButton("确定",
						// new DialogInterface.OnClickListener() {// 添加确定按钮
						// public void onClick(
						// DialogInterface dialog,
						// int which) {// 确定按钮的响应事件
						// changeSaler();
						// }
						// }).setNegativeButton("取消", null).show();
					} else {
						final Handle_defined_dialog hd = new Handle_defined_dialog(
								ConsultantActivity.this, "确认选择", "是否添加"
										+ sm.getName() + "成为您的置业顾问。", "确认",
								"取消");
						hd.dialog
								.setClicklistener(new ClickListenerInterface() {
									public void doConfirm() {
										// TODO Auto-generated method stub
										changeSaler();
										hd.dialog.dismiss();
									}

									public void doCancel() {
										// TODO Auto-generated method stub
										hd.dialog.dismiss();
									}
								});
						hd.cleardialog();
						// new AlertDialog.Builder(ConsultantActivity.this)
						// .setTitle("确认选择")// 设置对话框标题
						// .setMessage("是否添加" + sm.getName() + "成为您的置业顾问。")//
						// 设置显示的内容
						// .setPositiveButton("确定",
						// new DialogInterface.OnClickListener() {// 添加确定按钮
						// public void onClick(
						// DialogInterface dialog,
						// int which) {// 确定按钮的响应事件
						// changeSaler();
						// }
						// }).setNegativeButton("取消", null).show();
					}
				} else {
					Toast.makeText(ConsultantActivity.this,
							"您已添加该置业顾问，请不要重复添加", Toast.LENGTH_SHORT).show();
				}
			}
		});

		comment.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(),
						ConsultCommentActivity.class);
				i.putExtra("salerid", ""
						+ getIntent().getStringExtra("salerid"));
				i.putExtra("salername", "" + sm.getName());
				startActivity(i);
			}
		});
		wantcom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ConsultantActivity.this,
						SendCommentActivity.class);
				i.putExtra("salerid", ""
						+ getIntent().getStringExtra("salerid"));
				i.putExtra("salername", "" + sm.getName());
				startActivity(i);

			}

		});
	}

	public void initViewPager() {
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		tips = new ImageView[imagearray.size()];
		mIV = new ArrayList<View>();
		for (int i = 0; i < imagearray.size(); i++) {
			ImageView iv = new ImageView(this);
			LayoutParams para = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			iv.setScaleType(ScaleType.FIT_CENTER);
			iv.setLayoutParams(para);
			InitPicture_setting.getImageLoader(imagearray.get(i), iv);
			iv.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					vp.setVisibility(View.GONE);
					vg.setVisibility(View.GONE);
					flag = false;
				}
			});
			// iv.setScaleType(ScaleType.MATRIX);
			iv.setOnTouchListener(new MulitPointTouchListener(this));
			mIV.add(iv);
		}
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			// tips[i] = imageView;
			// if (i == 0) {
			// tips[i].setBackgroundResource(R.drawable.index01);
			// } else {
			// tips[i].setBackgroundResource(R.drawable.index02);
			// }
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams((int) (0.05 * screenHeight),
							(int) (0.05 * screenHeight)));
			layoutParams.height = (int) (0.02 * screenHeight);
			layoutParams.width = (int) (0.02 * screenHeight);
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			vg.addView(imageView, layoutParams);
		}
		vp.setAdapter(new AdvAdapter(mIV));
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

	private void changeSaler() {
		HashMap<String, String> map = JSONCommand.JSON10046("6", "1",
				getIntent().getStringExtra("salerid"), sm.getName());
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				Boolean state = (Boolean) model;
				if (state) {

					Toast.makeText(ConsultantActivity.this, "变换置业顾问成功",
							Toast.LENGTH_SHORT).show();
				} else {

					Toast.makeText(ConsultantActivity.this, "服务器繁忙，请稍后再试",
							Toast.LENGTH_SHORT).show();
				}

			}
		}, new State_parse_Json());
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // Check if the key event was the Back button and if there's history
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// // 返回键退回
	// setResult(1);
	// ConsultantActivity.this.finish();
	// }
	//
	// return super.onKeyDown(keyCode, event);
	// }

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {
		if (flag == true) {
			vp.setVisibility(View.GONE);
			vg.setVisibility(View.GONE);
			flag = false;

		} else {
			setResult(1);
			ConsultantActivity.this.finish();
		}
	}

}
