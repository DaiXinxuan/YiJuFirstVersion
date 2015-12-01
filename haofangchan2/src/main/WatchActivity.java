package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import testandmanage.JSONCommand;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.haofangchan2.R;

import different_adapter.InfoListAdapter;
import different_adapter.Main_seecar_Adapter;
import different_adapter.Main_seehouse_Adapter;
import different_jsonparse.NormalCarModelParser;
import different_jsonparse.NormalHouseModelParser;
import different_jsonparse.SimpleHouseModelParser;
import differentjavabean.NormalCarListViewModel;
import differentjavabean.NormalHouseListViewModel;
import differentjavabean.NormalHouseViewModel;

public class WatchActivity extends Activity {
	private ViewPager vp;
	private ArrayList<View> viewContainter = new ArrayList<View>();
	private ImageView cursor, tab1, tab2;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	// 看房界面的控件
	private RadioGroup rg;
	private boolean exit = true;
	private ImageView back;
	private Map<String, String> map, map2;
	// 选项按钮选择状态判断
	boolean flag = false, firstadd = true;
	private ListView info_list;
	// 标识弹出选择条的按钮类别
	private String type;
	// 选项标签文本
	ArrayList<String> data;
	String[] data3 = { "不限", "A1", "A2", "B1", "B2", "B3", "B4" };
	String[] data21 = { "不限", "0", "50", "70", "90", "110", "130", "150", "200" };
	String[] data22 = { "不限", "50", "70", "90", "110", "130", "150", "200",
			"2000" };
	private NormalHouseViewModel nhm;
	private ArrayList<NormalHouseListViewModel> nhvm;
	private ArrayList<NormalCarListViewModel> ncm;
	private ArrayList<String> sfm, spm;
	private HashMap<String, String> shtm;
	private PullToRefreshListView carsRefreshListView, houseRefreshListView;
	private int carInt, houseInt;
	private Main_seecar_Adapter carAdapter;
	private Main_seehouse_Adapter houseAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_watch);
		nhvm = new ArrayList<NormalHouseListViewModel>();
		ncm = new ArrayList<NormalCarListViewModel>();
		carInt = 0;
		houseInt = 0;
		initPager();
		findView();
		InitImageView();
		carAdapter = new Main_seecar_Adapter(ncm);
		carsRefreshListView.setAdapter(carAdapter);
		houseAdapter = new Main_seehouse_Adapter(nhvm);
		houseRefreshListView.setAdapter(houseAdapter);
		getHouseData();
		getCarData();
	}

	public void getHouseData() {
		HashMap<String, String> map = JSONCommand.JSON10009("6", "1", houseInt
				+ "", "15");

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			public void updateUI(Object model) {
				nhm = (NormalHouseViewModel) model;
				nhvm.addAll(nhm.getHouseListViewModel());
				sfm = nhm.getSearchFormModel();
				spm = nhm.getSearchPriceModel();
				shtm = nhm.getSearchHouseTypeModel();
				houseAdapter.notifyDataSetChanged();
				if (houseRefreshListView.isRefreshing()) {
					houseRefreshListView.onRefreshComplete();
				}
				nhvm = nhm.getHouseListViewModel();
				houseRefreshListView
						.setAdapter(new Main_seehouse_Adapter(
								nhvm));

			}
		}, new NormalHouseModelParser());

	}

	public void getCarData() {

		@SuppressWarnings("unchecked")
		// HashMap<String, String> map = HttpRequest.extedMap("10021", "6", "1",
		// "" + carInt, "15");
		HashMap<String, String> map = JSONCommand
				.JSON10021("6", "1", "0", "15");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@SuppressWarnings("unchecked")
			@Override
			public void updateUI(Object model) {

				ncm.addAll((ArrayList<NormalCarListViewModel>) model);
				carAdapter.notifyDataSetChanged();
				if (carsRefreshListView.isRefreshing()) {
					carsRefreshListView.onRefreshComplete();
				}
			}
		}, new NormalCarModelParser());

	}

	private void InitImageView() {
		// cursor = (ImageView) findViewById(R.id.cursor);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		bmpW = 2 * screenW / 5;
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
	}

	// 车位界面
	private void initCarViews() {

		LayoutInflater mInflater = getLayoutInflater();
		View v2 = mInflater.inflate(R.layout.watchcar_layout, null);
		carsRefreshListView = (PullToRefreshListView) v2
				.findViewById(R.id.car_list);

		carsRefreshListView.setMode(Mode.BOTH);
		carsRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						carInt = 0;
						ncm.clear();
						getCarData();

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						carInt++;
						getCarData();
					}
				});

		viewContainter.add(v2);
	}

	private RadioButton btn1, btn2, btn3, btn4;

	@SuppressLint("InflateParams")
	private void initPager() {
		vp = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater mInflater = getLayoutInflater();
		// 找明看房界面的各个控件并添加事件
		View v = mInflater.inflate(R.layout.activity_watchhouse, null);
		rg = (RadioGroup) v.findViewById(R.id.bottomRg);
		houseRefreshListView = (PullToRefreshListView) v
				.findViewById(R.id.house_list);
		houseRefreshListView.setMode(Mode.BOTH);
		houseRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						houseInt = 0;
						nhvm.clear();
						getHouseData();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						houseInt++;
						getHouseData();
					}
				});

		info_list = (ListView) v.findViewById(R.id.info_list);

		info_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 0) {
				} else {
					rg.clearCheck();
					info_list.setVisibility(View.GONE);
					flag = false;
				}
			}
		});
		btn1 = (RadioButton) v.findViewById(R.id.rb1);
		btn2 = (RadioButton) v.findViewById(R.id.rb2);
		btn3 = (RadioButton) v.findViewById(R.id.rb3);
		btn4 = (RadioButton) v.findViewById(R.id.rb4);
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				exit = false;
				info_list.setVisibility(View.VISIBLE);
				switch (checkedId) {
				case R.id.rb1:
					data = new ArrayList<String>();
					data = sfm;
					if (!data.get(0).equals("不限")){
						data.add(0, "不限");
					}
//					if (firstadd) {
//						data.add(0, "不限");
//						firstadd = false;
//					}
					type = "rb1";
					info_list.setAdapter(new InfoListAdapter(data));
					info_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							if (arg2 == 0) {
								getHouseData();

							} else {
								HashMap<String, String> map = JSONCommand
										.JSON10016("6", "1", data.get(arg2));
								Log.d("WatchOnItem", map.toString());
								ServerAsyncTask asyncTask = new ServerAsyncTask();
								asyncTask.execute(map, new UpdateUIInterface() {

									public void updateUI(Object model) {
										nhm = (NormalHouseViewModel) model;

										nhvm = nhm.getHouseListViewModel();
										houseRefreshListView
												.setAdapter(new Main_seehouse_Adapter(
														nhvm));

									}
								}, new SimpleHouseModelParser());
							}
							info_list.setVisibility(View.GONE);
							flag = false;
						}
					});

					break;
				case R.id.rb2:
					data = new ArrayList<String>();
					data.add("不限");
					data.add("50㎡以下");
					data.add("50㎡-70㎡");
					data.add("70㎡-90㎡");
					data.add("90㎡-110㎡");
					data.add("110㎡-130㎡");
					data.add("130㎡-150㎡");
					data.add("150㎡-200㎡");
					data.add("200㎡以上");
					type = "rb2";
					info_list.setAdapter(new InfoListAdapter(data));
					info_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							Log.d("WatchOnItem", "Entry");
							HashMap<String, String> map;
							if (arg2 == 0){
								//getHouseData();
								Log.d("nolimit", "arg=0");
								map = JSONCommand.JSON10017("6", "1", "0",
										"1000");
								}
							else 
								
								if (arg2 == 1) {
									map = JSONCommand.JSON10017("6", "1", "0",
											"50");
								} else if (arg2 == 8) {
									map = JSONCommand.JSON10017("6", "1",
											"200", "1000");
								} else {
									map = JSONCommand.JSON10017("6", "1", 50
											+ 20 * (arg2 - 2) + "", 50 + 20
											* (arg2 - 1) + "");
								}

								ServerAsyncTask asyncTask = new ServerAsyncTask();
								asyncTask.execute(map, new UpdateUIInterface() {

									public void updateUI(Object model) {
										nhm = (NormalHouseViewModel) model;
										nhvm = nhm.getHouseListViewModel();
										houseRefreshListView
												.setAdapter(new Main_seehouse_Adapter(
														nhvm));

									}
								}, new SimpleHouseModelParser());
							
							info_list.setVisibility(View.GONE);
							flag = false;
						}
					});

					break;
				case R.id.rb3:
					data = new ArrayList<String>();
					data.add("不限");

					Iterator iter = shtm.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String key = (String) entry.getKey();
						String val = (String) entry.getValue();
						Log.d(key, val);
						data.add(val);
					}
					type = "rb3";
					info_list.setAdapter(new InfoListAdapter(data));
					info_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							if (arg2 == 0)
								getHouseData();
							else {
								Iterator iter = shtm.entrySet().iterator();
								while (iter.hasNext()) {
									Map.Entry entry = (Map.Entry) iter.next();
									String key = (String) entry.getKey();
									String val = (String) entry.getValue();
									if (val.equals(data.get(arg2))) {
										HashMap<String, String> map = JSONCommand
												.JSON10018("6", "1", key);
										ServerAsyncTask asyncTask = new ServerAsyncTask();
										asyncTask.execute(map,
												new UpdateUIInterface() {

													public void updateUI(
															Object model) {
														nhm = (NormalHouseViewModel) model;
														nhvm = nhm
																.getHouseListViewModel();
														houseRefreshListView
																.setAdapter(new Main_seehouse_Adapter(
																		nhvm));

													}
												}, new SimpleHouseModelParser());
									}
								}
							}
							info_list.setVisibility(View.GONE);
							flag = false;
						}
					});
					break;
				case R.id.rb4:
					final double maxprice,
					minprice,
					x;
					data = new ArrayList<String>();
					maxprice = Double.parseDouble(spm.get(1));
					minprice = Double.parseDouble(spm.get(0));
					x = Math.round((maxprice - minprice) / 5);
					data.add("不限");
					data.add((int) minprice / 10000 + "万-"
							+ (int) (minprice + x) / 10000 + "万");
					data.add((int) (minprice + x) / 10000 + "万-"
							+ (int) (minprice + 2 * x) / 10000 + "万");
					data.add((int) (minprice + 2 * x) / 10000 + "万-"
							+ (int) (minprice + 3 * x) / 10000 + "万");
					data.add((int) (minprice + 3 * x) / 10000 + "万-"
							+ (int) (minprice + 4 * x) / 10000 + "万");
					data.add((int) (minprice + 4 * x) / 10000 + "万-"
							+ (int) maxprice / 10000 + "万");
					type = "rb4";
					info_list.setAdapter(new InfoListAdapter(data));
					info_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							Log.d("WatchOnItem", "Entry");
							HashMap<String, String> map;
							if (arg2 == 0) {
								map = JSONCommand
										.JSON10019("6", "1", minprice+"", maxprice+"");

							} else {
								map = JSONCommand
										.JSON10019("6", "1", minprice + arg2
												* x + "", minprice + (arg2 + 1)
												* x + "");
								Log.d("WatchOnItem", map.toString());
								
							}
							ServerAsyncTask asyncTask = new ServerAsyncTask();
							asyncTask.execute(map, new UpdateUIInterface() {

								public void updateUI(Object model) {
									nhm = (NormalHouseViewModel) model;

									nhvm = nhm.getHouseListViewModel();
									houseRefreshListView
											.setAdapter(new Main_seehouse_Adapter(
													nhvm));

								}
							}, new SimpleHouseModelParser());
							info_list.setVisibility(View.GONE);
							flag = false;
						}
					});
					break;

				}
			}
		});
		viewContainter.add(v);
		initCarViews();
		vp.setAdapter(new MyPagerAdapter(viewContainter));
		vp.setCurrentItem(0);

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Animation animation = null;
				switch (arg0) {
				case 0:
					tab1.setImageResource(R.drawable.showingsn1x);
					tab2.setImageResource(R.drawable.parkings1x);
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
					}
					break;
				case 1:
					tab1.setImageResource(R.drawable.showingss1x);
					tab2.setImageResource(R.drawable.parkingn1x);
					if (currIndex == 0) {
						animation = new TranslateAnimation(0, one, 0, 0);
					}
					break;
				}
				currIndex = arg0;
				animation.setFillAfter(true);// 图片在动画位置结束停止
				animation.setDuration(300);
				// cursor.startAnimation(animation);
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
	}

	android.view.View.OnClickListener listener = new View.OnClickListener() {
		RadioButton v0;

		public void onClick(View v) {
			RadioButton btn = (RadioButton) v;
			if (flag == true && v0 == btn) {
				rg.clearCheck();
				info_list.setVisibility(View.GONE);
				btn.setChecked(false);
				flag = false;
			}
			if (btn.isChecked()) {
				flag = true;
				info_list.setVisibility(View.VISIBLE);
				v0 = btn;
			}
		}
	};

	public void onBackPressed() {
		// TODO Auto-generated method stub
		// rg.clearCheck();
		if (!exit) {
			rg.clearCheck();
			info_list.setVisibility(View.GONE);
			exit = true;
		} else {
			super.onBackPressed();
		}
		return;
	}

	class MyPagerAdapter extends PagerAdapter {
		public ArrayList<View> list;

		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			super.destroyItem(container, position, object);
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position), 0);
			return list.get(position);
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	private void findView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		tab1 = (ImageView) findViewById(R.id.watch_houseiv);
		tab2 = (ImageView) findViewById(R.id.car_iv);
		back = (ImageView) findViewById(R.id.watch_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				WatchActivity.this.finish();
			}
		});
		tab1.setOnClickListener(new OnClickListener2(0));
		tab2.setOnClickListener(new OnClickListener2(1));
	}

	class OnClickListener2 implements android.view.View.OnClickListener {
		private int index = 0;

		public OnClickListener2(int i) {
			index = i;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			vp.setCurrentItem(index);
		}

	}
}
