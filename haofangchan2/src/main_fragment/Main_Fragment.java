package main_fragment;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.CalculateActivity;
import main.ConsultantActivity;
import main.HouseinfoActivity;
import main.LatestNewsActivity;
import main.WatchActivity;
import main.ZhiyeguwenActivity;

import org.litepal.crud.DataSupport;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.Window_metrics;
import web.WebActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.uranus.amaptest.MapViewActivity;
import com.uranus.amaptest.MapViewFragment;

import different_adapter.Main_MessageAdapter;
import different_fragment_data.Main_viewpager;
import different_jsonparse.HomePageModelParser;
import different_jsonparse.MainZhiYeGuWenJsonparse;
import differentjavabean.HomePageHouseListModel;
import differentjavabean.HomePageModel;
import differentjavabean.LatestNewsTable;
import differentjavabean.MainZhiYeGuWenModel;

public class Main_Fragment extends Fragment {
	private TextView tv;
	private List<String> listpictureurl;
	private List<String[]> housemessage;
	private View main_view, headView;
	private String proid = "1";
	private ImageView seehouse, buildinginfo, zhiyeconslutant, magezine,
			calculate;
	private ListView list;
	private PullToRefreshListView listRefreshListView;
	private Main_MessageAdapter messageAdapter = null;
	private HomePageModel hpm;
	private ImageButton turnToMap;
	private LinearLayout mapFragmentLinearLayout;
	private MapViewFragment mapFragment;
	private TextView titleTextView;
	private ImageView latestImageView,invisbleView;
	private int UnreadLatestNewsCount;
	private BadgeView badgeView;
	private int mainHouseIndx;
	private Main_viewpager headViewpager;
	private ArrayList<HomePageHouseListModel> houses;
    private Main_MessageAdapter adapter;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		main_view = inflater.inflate(R.layout.activity_layout1, container,
				false);
		headView = inflater.inflate(R.layout.activity_layou1_headview, null);
		initView();
		housemessage = new ArrayList<String[]>();
		listpictureurl = new ArrayList<String>();

		adapter=new Main_MessageAdapter(houses, getActivity());
		listRefreshListView.setAdapter(adapter);
		
		initInternetdata();
		initLatestNewsTintNumber();

		return main_view;
	}

	private void initView() {
		invisbleView = (ImageView) headView.findViewById(R.id.invisibleView);
		seehouse = (ImageView) headView.findViewById(R.id.online_watch);
		houses = new ArrayList<HomePageHouseListModel>();
		latestImageView = (ImageView) headView.findViewById(R.id.latestNews);
		buildinginfo = (ImageView) headView
				.findViewById(R.id.house_simple_instruct);
		zhiyeconslutant = (ImageView) headView.findViewById(R.id.consultor);
		magezine = (ImageView) headView.findViewById(R.id.project_magzine);
		calculate = (ImageView) headView.findViewById(R.id.calculate_pay);
        
		ImageView imageView=(ImageView)main_view.findViewById(R.id.back_activity_layout1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    getActivity().finish();	
			}
		});
		
		headViewpager = new Main_viewpager();

		LinearLayout.LayoutParams lp = (LayoutParams) seehouse
				.getLayoutParams();
		lp.height = (int) (Window_metrics.getScreenHeight() * 0.13);
		seehouse.setLayoutParams(lp);
	//	latestImageView.setLayoutParams(lp);
		buildinginfo.setLayoutParams(lp);
		zhiyeconslutant.setLayoutParams(lp);
		magezine.setLayoutParams(lp);
		calculate.setLayoutParams(lp);

		FrameLayout.LayoutParams lp1 = (android.widget.FrameLayout.LayoutParams) latestImageView.getLayoutParams();
		lp1.height = (int) (Window_metrics.getScreenHeight() * 0.13);
		latestImageView.setLayoutParams(lp1);
		invisbleView.setLayoutParams(lp1);
		
		turnToMap = (ImageButton) main_view.findViewById(R.id.turnToMap);
		titleTextView = (TextView) main_view
				.findViewById(R.id.title_mainfragment);
		listRefreshListView = (PullToRefreshListView) main_view
				.findViewById(R.id.main_info_List);
		list = (ListView) listRefreshListView.getRefreshableView();
	    LogUtil.d("下拉刷新listViewClass", "class:"+list.getClass());
		list.addHeaderView(headView);
		listRefreshListView.setMode(Mode.BOTH);
		
		listRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						mainHouseIndx = 0;
						houses.clear();
						initInternetdata();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						mainHouseIndx++;
						initInternetdata();
					}
				});

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				LogUtil.d("首页房屋点击事件", "项数为" + arg3);
				HomePageHouseListModel houseModel = main_data.getHouses().get(
						(int) arg3);
				Intent houseinfo = new Intent(MyApplication.getContext(),
						HouseinfoActivity.class);
				houseinfo.putExtra("houseid", houseModel.getRoomId());
				houseinfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(houseinfo);

			}
		});

		//初始化地图实例
		mapFragmentLinearLayout = (LinearLayout) headView
				.findViewById(R.id.map_view);
		mapFragment = new MapViewFragment(listRefreshListView);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.map_view, (Fragment) mapFragment);

		// 步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
		transaction.commit();
		
		
		zhiyeconslutant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Map<String, String> map = JSONCommand.JSON10025();
				ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

					@Override
					public void updateUI(Object model) {
						// TODO Auto-generated method stub
						MainZhiYeGuWenModel data = (MainZhiYeGuWenModel) model;
						if (data.getStatus().equals("true")) {
							if (data.getSalerid().equals("-1")) {
								Intent intent = new Intent(getActivity(),
										ZhiyeguwenActivity.class);
								startActivity(intent);
								Toast.makeText(getActivity(),
										"您当前未添加置业顾问，请添加置业顾问",
										Toast.LENGTH_SHORT).show();
							} else {
								Intent intent = new Intent(getActivity(),
										ConsultantActivity.class);
								intent.putExtra("salerid",
										"" + data.getSalerid());
								intent.putExtra("hasSaler", true);
								startActivity(intent);
							}
						} else {
							Toast.makeText(getActivity(), "服务器繁忙，请稍后再试",
									Toast.LENGTH_SHORT).show();
						}
					}
				}, new MainZhiYeGuWenJsonparse());
			}
		});

		magezine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						WebActivity.class);
//				intent.putExtra("isProjectMagazine", true);
				intent.putExtra("url", main_data.getMagzineHtml());
				intent.putExtra("title", "项目杂志");
				getActivity().startActivity(intent);
			}
		});

		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CalculateActivity.class);
				startActivity(intent);
			}
		});

		latestImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(getActivity(),
						LatestNewsActivity.class);
				startActivity(intent);
				if (badgeView != null) {
					badgeView.setVisibility(View.GONE);
				}
			}
		});

		seehouse.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WatchActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(intent);
			}
		});
		buildinginfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("url", main_data.getBuildingIntrodeceHtml());
				intent.putExtra("title", "楼盘简介");
				getActivity().startActivity(intent);
			}
		});
		turnToMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), MapViewActivity.class);
				intent.putExtra("address", main_data.getHomePageMapModel()
						.getAddress());
				intent.putExtra("saleAddress", main_data.getHomePageMapModel()
						.getSaleAddress());
				startActivity(intent);
			}
		});

	}

	// 获取网络数据
	private HomePageModel main_data;

	public void initLatestNewsTintNumber() {

		UnreadLatestNewsCount = 0;
		List<LatestNewsTable> list = DataSupport.findAll(LatestNewsTable.class);

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LatestNewsTable newsTable = list.get(i);
				if (newsTable.getIsRead() == false) {
					UnreadLatestNewsCount++;
				}
			}
			if (UnreadLatestNewsCount > 0) {
				if (badgeView == null) {
					badgeView = new BadgeView(getActivity(), invisbleView);
				}
				badgeView.setText("" + UnreadLatestNewsCount);
				badgeView.show();
			}
		}
	}

	public void initInternetdata() {
		// 发送网络请求
		Map<String, String> map = new HashMap<String, String>();
		map = JSONCommand.JSON10008("6", "1", "" + mainHouseIndx, "10");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				main_data = (HomePageModel) model;
				mapFragment.initMap(main_data.getHomePageMapModel()
						.getAddress(), main_data.getHomePageMapModel()
						.getSaleAddress());
				houses.addAll(main_data.getHouses());
			    adapter.notifyDataSetChanged();	
				headViewpager.initPicture(main_data.getPhotos(), main_view);
				headViewpager.initFirstPage();
				titleTextView.setText(main_data.getProjectName());
				
				if (listRefreshListView.isRefreshing()) {
					listRefreshListView.onRefreshComplete();
				}
			}
		}, new HomePageModelParser());
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getProid() {
		return proid;
	}

}
