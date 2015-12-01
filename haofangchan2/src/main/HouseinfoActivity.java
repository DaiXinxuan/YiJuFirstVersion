package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.litepal.util.LogUtil;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.HouseInfoParser;
import different_jsonparse.State_parse_Json;
import differentjavabean.NormalHouseInfoModel;
import differentjavabean.NormalHouseListViewModel;

public class HouseinfoActivity extends Activity implements OnPageChangeListener {
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

	private TextView collect, code, type, structarea, unitprice, jifang,
			configure, orientation, structure, poolarea, form, condition,
			innerarea, giftarea;
	private ImageView calculate, magazine, comment, book, back;
	private ImageView image, big;
	private Map<String, String> map;

	private ArrayList<NormalHouseListViewModel> datalist;
	private ArrayList<String> imagearray;
	private NormalHouseInfoModel nhim;
	private boolean flag = false;
	private Boolean isAttention;
	private String url;
//	int notClickedImages[] = new int[] { R.drawable.homen1x,
//			R.drawable.activityn1x, R.drawable.messagen1x, R.drawable.personn1x };
//	int clickedImages[] = new int[] { R.drawable.homes1x,
//			R.drawable.activitys1x, R.drawable.messages1x, R.drawable.persons1x };
	private AtomicInteger whats = new AtomicInteger(0);
	private boolean isContinue = true;
	private ViewPager vp;// 首页滑动图片
	private List<View> mIV;// 图片数组
	private ImageView[] tips;// 点点数组
	private ViewGroup vg;

	public void setgone() {
		vp.setVisibility(View.GONE);
		vg.setVisibility(View.GONE);
		flag = false;
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.houseinfo);
		init();
		getModel();
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
//			tips[i] = imageView;
//			if (i == 0) {
//				tips[i].setBackgroundResource(R.drawable.index01);
//			} else {
//				tips[i].setBackgroundResource(R.drawable.index02);
//			}
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

	public void getModel() {

		map = JSONCommand.JSON10010("6", "1",
				getIntent().getStringExtra("houseid"));
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			public void updateUI(Object model) {

				nhim = (NormalHouseInfoModel) model;
				code.setText(nhim.getRoomNumber());
				type.setText(nhim.getHouseType());
				unitprice.setText(nhim.getRoomPrice()
						+ unitprice.getText().toString());
				configure.setText(nhim.getRoomConfig());
				form.setText(nhim.getRoomForm());

				structure.setText(nhim.getRoomStruct());

				if (nhim.getRoomDecoration() == "0")
					condition.setText("未装修");
				else
					condition.setText("已装修");

				jifang.setText(nhim.getRoomConfig());
				structarea.setText(nhim.getStructArea()
						+ structarea.getText().toString());
				innerarea.setText(nhim.getRoomArea()
						+ innerarea.getText().toString());
				poolarea.setText(nhim.getStructArea()
						+ poolarea.getText().toString());
				giftarea.setText(nhim.getGiveArea()
						+ giftarea.getText().toString());
				orientation.setText(nhim.getRoomForward());
				InitPicture_setting.getImageLoader(nhim.getPhoto().get(0),
						image);
				url = nhim.getRoomUrl();
				imagearray = nhim.getPhoto();
				comment.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent commentIntent = new Intent(getApplicationContext(),
								CommnetActivity.class);
						LogUtil.d("房屋号码", "" + getIntent().getStringExtra("houseid"));
						commentIntent.putExtra("houseid",
								getIntent().getStringExtra("houseid"));
						commentIntent.putExtra("housenum", nhim.getRoomNumber());
						LogUtil.d("房屋编号", "为" + nhim.getRoomNumber());
						startActivity(commentIntent);

					}
				});
				collect.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						map = JSONCommand.JSON10011("6", "1", getIntent()
								.getStringExtra("houseid"));
						ServerAsyncTask asyncTask = new ServerAsyncTask();
						asyncTask.execute(map, new UpdateUIInterface() {

							public void updateUI(Object model) {
								isAttention = (boolean) model;
								if (isAttention == false) {
									Toast.makeText(getApplicationContext(), "您已关注，请勿重复关注",
											Toast.LENGTH_SHORT).show();
								} else {
									new Handle_collect_dialog().cleardialog(
											HouseinfoActivity.this, "加入关注", "关注成功");
									Toast.makeText(getApplicationContext(), "关注成功",
											Toast.LENGTH_SHORT).show();
								}
							}
						}, new State_parse_Json());
					}
				});

				magazine.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getApplicationContext(),
								WebActivity.class);
						intent.putExtra("url", url);
						intent.putExtra("title", "杂志");
						startActivity(intent);
					}
				});

				calculate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent calculate = new Intent(getApplicationContext(),
								CalculateActivity.class);
						calculate.putExtra("houseid",
								Integer.parseInt(getIntent().getStringExtra("houseid")));
						calculate.putExtra("housenum", nhim.getRoomNumber());
						startActivity(calculate);
					}
				});

				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						flag = true;
						vp.setVisibility(View.VISIBLE);
						vg.setVisibility(View.VISIBLE);
					}
				});

				book.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								OrderWatchingActivity.class);
						intent.putExtra("houseid", getIntent()
								.getStringExtra("houseid"));
						intent.putExtra("housenum", nhim.getRoomNumber());
						startActivity(intent);
					}
				});

				//Log.e("houseinfoViewPager", imagearray.toString());
				initViewPager();
			}
		}, new HouseInfoParser());
	}

	private void init() {

		collect = (TextView) findViewById(R.id.houseinfo_collect);
		code = (TextView) findViewById(R.id.houseinfo_codetext);
		type = (TextView) findViewById(R.id.houseinfo_typetext);
		structarea = (TextView) findViewById(R.id.houseinfo_structareatext);
		unitprice = (TextView) findViewById(R.id.houseinfo_unitpricetext);
		jifang = (TextView) findViewById(R.id.houseinfo_jifangtext);
		configure = (TextView) findViewById(R.id.houseinfo_configuretext);
		orientation = (TextView) findViewById(R.id.houseinfo_orientationtext);
		structure = (TextView) findViewById(R.id.houseinfo_structuretext);
		poolarea = (TextView) findViewById(R.id.houseinfo_poolareatext);
		form = (TextView) findViewById(R.id.houseinfo_formtext);
		condition = (TextView) findViewById(R.id.houseinfo_conditiontext);
		innerarea = (TextView) findViewById(R.id.houseinfo_innerareatext);
		giftarea = (TextView) findViewById(R.id.houseinfo_giftareatext);
		calculate = (ImageView) findViewById(R.id.houseinfo_calculate);
		magazine = (ImageView) findViewById(R.id.houseinfo_magazine);
		comment = (ImageView) findViewById(R.id.houseinfo_comment);
		//comment.setClickable(false);
		book = (ImageView) findViewById(R.id.houseinfo_booktowatch);
		image = (ImageView) findViewById(R.id.houseinfo_image);
		// big = (ImageView) findViewById(R.id.houseinfo_image);
		vg = (ViewGroup) findViewById(R.id.houseinfo_viewGroup);
		vp = (ViewPager) findViewById(R.id.houseinfo_viewPager);
		back = (ImageView) findViewById(R.id.houseinfo_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	public void onBackPressed() {
		if (flag == true) {
			vp.setVisibility(View.GONE);
			vg.setVisibility(View.GONE);
			flag = false;

		} else {
			super.onBackPressed();
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
}
