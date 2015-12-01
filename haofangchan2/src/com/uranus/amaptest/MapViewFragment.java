package com.uranus.amaptest;

import handmark.pulltorefresh.library.PullToRefreshListView;
import handmark.pulltorefresh.library.PullToRefreshListView.InternalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import testandmanage.LogUtil;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapFragment;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.example.haofangchan2.R;

import android.R.integer;
import android.app.Activity;
import android.app.Notification.Action;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class MapViewFragment extends Fragment implements
		OnGeocodeSearchListener, OnMarkerClickListener, OnPoiSearchListener {
	private static long InitType = 1;// 初始化的搜索，之搜索售楼部和楼盘
	private static long NormalType = 2;// 普通搜索，兴趣点的搜索
	private long searchType;// 搜索类型，只会为initType和NormalType
	private MapView mapView;// mapView
	private AMap aMap;
	private GeocodeSearch geocodeSearch;// 地址编译器
	private ArrayList<Marker> markerList;// 锚点数组
	private ArrayList<Marker> initMarkerList;// 楼盘和售楼部数组
	private ArrayList<String> addressList;// 当前搜索的地址数组，包括需要搜索的所有地址
	private String buidingAddress;// 楼盘地址
	private String saleBuildingAddress;// 售楼部地址
	private int pos;// 数组当前位置
	private String keyword;// 搜索关键字
	private HashMap<String, ArrayList<String>> addressHashMap;
	private MapViewModel mapViewModel;
	private View view;
	private PullToRefreshListView listView;

	public MapViewFragment() {
		// TODO Auto-generated constructor stub
	}

	public MapViewFragment(PullToRefreshListView listView) {
		this.listView = listView;
	}

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		// fragment生活周期与活动相同，这里只初始化一次，避免重复添加view
		if (view == null) {
			view = LayoutInflater.from(getActivity()).inflate(
					R.layout.mapviewfragment, null);
			mapView = (MapView) view.findViewById(R.id.mapViewFragment);
			// 必须，不然无法显示地图
			mapView.onCreate(arg2);
		}
		if (aMap == null) {

			aMap = mapView.getMap();
		}
		aMap.setOnMarkerClickListener(this);
		markerList = new ArrayList<Marker>();
		initMarkerList = new ArrayList<Marker>();
		geocodeSearch = new GeocodeSearch(getActivity());
		geocodeSearch.setOnGeocodeSearchListener(this);

		aMap.setOnMapTouchListener(new OnMapTouchListener() {

			@Override
			public void onTouch(MotionEvent arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("地图位移", "移动");
				int action = arg0.getAction();
				InternalListView internalListView = (InternalListView) listView
						.getRefreshableView();
				if (action == MotionEvent.ACTION_DOWN) {
					if (listView != null) {

						internalListView.setListViewIfCloseIntercept(true);
						internalListView.onInterceptTouchEvent(arg0);
						listView.setIfClosePullRefreshOnIntercept(true);
						listView.onInterceptTouchEvent(arg0);
					}
				}
				if (action == MotionEvent.ACTION_UP
						|| action == MotionEvent.ACTION_CANCEL) {
					if (listView != null) {
						// listView.getRefreshableView().onInterceptTouchEvent(null);
						internalListView.setListViewIfCloseIntercept(false);
						internalListView.onInterceptTouchEvent(arg0);
						listView.setIfClosePullRefreshOnIntercept(false);
						listView.onInterceptTouchEvent(arg0);
					}
				}
			}
		});

		return view;
	}

	// 自定义接口，给别的类调用，以初始化地图
	public void initMap(String buildingAddress, String saleBuildingAddress) {
		this.buidingAddress = buildingAddress;
		this.saleBuildingAddress = saleBuildingAddress;
		LogUtil.d("地图视图", "为"+mapView);
		LogUtil.d("地图","为"+aMap);
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		updateMapView();
	}

	private void updateMapView() {
		clearMarkerList();
		searchType = InitType;
		beginAddressToSearch();

	}

	public void setMapViewModel(MapViewModel mapViewModel) {
		this.mapViewModel = mapViewModel;
		this.addressHashMap = this.mapViewModel.getAddressHashMap();
		this.buidingAddress = this.mapViewModel.getBuildingAddress();
		this.saleBuildingAddress = this.mapViewModel.getSaleBuildingAddress();
		updateMapView();
	}

	// 开始兴趣点的搜索。
	private void beginPOISearch() {
		Query query = new PoiSearch.Query(keyword, keyword);
		PoiSearch poiSearch = new PoiSearch(getActivity(), query);

		if (initMarkerList.size() > 0) {
			// 得到楼盘的经纬度坐标
			Marker marker = initMarkerList.get(0);
			poiSearch.setBound(new SearchBound(new LatLonPoint(marker
					.getPosition().latitude, marker.getPosition().longitude),
					5000));
			// 设置兴趣点搜索的回调为自己
			poiSearch.setOnPoiSearchListener(this);
			// 开始搜索
			poiSearch.searchPOIAsyn();

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		((ViewGroup) view.getParent()).removeView(view);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	// 地址编码搜索的回调。
	private void beginAddressToSearch() {
		if (searchType == InitType) {
			// 初始地址的搜索，设定售楼部和楼盘的坐标
			GeocodeQuery query;

			if (pos == 0) {
				// 搜索售楼部
				query = new GeocodeQuery(buidingAddress, "");
			} else {
				// 搜索楼盘
				query = new GeocodeQuery(saleBuildingAddress, "");
			}

			// 开始搜索
			geocodeSearch.getFromLocationNameAsyn(query);
		}
	}

	// 普通搜索的回调函数，传入中文地址字符串，搜索结束后的回调。geocodeSearch.setOnGeocodeSearchListener(this);
	// 进行了设置
	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == 0) {
			// 得到搜索回调函数，同一不清晰的地址可能会搜索出多个结果
			List<GeocodeAddress> results = arg0.getGeocodeAddressList();
			// 得到第一个搜索结果。
			GeocodeAddress address = results.get(0);
			// 得到地址的经纬度。
			LatLonPoint point = address.getLatLonPoint();
			// 得到详细的地址信息
			String detailAddr = address.getFormatAddress();
			// 建立锚点的设置信息
			MarkerOptions markerOptions = new MarkerOptions();
			// 设置锚点的经纬度
			markerOptions.position(new LatLng(point.getLatitude(), point
					.getLongitude()));
			// 设置锚点的显示的详细地址
			markerOptions.snippet(detailAddr);

			if (searchType == InitType) {
				// 得到之前的quary查询，判断是楼盘搜索还是售楼部搜索
				GeocodeQuery query = arg0.getGeocodeQuery();
				String lastAddress = query.getLocationName();

				if (lastAddress.equals(buidingAddress)) {
					// 如果上次搜索是楼盘，新建一次售楼部搜索
					pos = 1;
					// 设置锚点的标题
					markerOptions.title("楼盘地址");
					// 设置锚点的图标
					markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.property));
					// 将锚点加入到地图中
					Marker marker = aMap.addMarker(markerOptions);
					// 将锚点加入到initMarkerList里面，用于后续兴趣点搜索
					initMarkerList.add(marker);
					// 进行下一次售楼部搜索
					beginAddressToSearch();
				} else {
					// 设置锚点的标题
					markerOptions.title("售楼部地址");
					// 设置锚点的图标
					markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.sales));
					// 将锚点加入到地图中
					Marker marker = aMap.addMarker(markerOptions);
					// 将锚点加入到initMarkerList里面，用于后续兴趣点搜索
					initMarkerList.add(marker);
					// 将地图放大到能显示所有锚点的区域
					zoomToAllMarkers(initMarkerList);
					// 重置arrayList的位置
					pos = 0;
				}
			}
		}
	}

	// 将地图放大到能显示所有锚点的区域
	private void zoomToAllMarkers(ArrayList<Marker> markers) {
		// 通过builder来得到放大区域
		Builder builder = new Builder();

		for (int i = 0; i < markers.size(); i++) {
			// 添加所有坐标到buider中
			builder.include(markers.get(i).getPosition());
		}

		LatLngBounds bounds = builder.build();
		// 将地图放大到能够显示的区域
		aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {

	}

	// 锚点点击的回调函数，通过aMap.setOnMarkerClickListener(this);设置
	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		if (arg0.isInfoWindowShown()) {
			// 如果显示，则关闭
			arg0.hideInfoWindow();
		} else {
			// 如果关闭的，则显示
			arg0.showInfoWindow();
		}

		return false;
	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	// 清除所有的锚点
	private void clearMarkerList() {
		for (Marker marker : markerList) {
			// 将当前的锚点从地图中删除
			marker.remove();
		}

		// 清空锚点数组
		markerList.clear();
	}

	// POI，Points of interents搜索回调函数，通过PoiSearch poiSearch = new PoiSearch(this,
	// query); 进行了设置
	@Override
	public void onPoiSearched(PoiResult arg0, int arg1) {
		// TODO Auto-generated method stub
		// 将地图中所有的锚点清除
		clearMarkerList();

		// 判断搜索是否成功
		if (arg1 == 0) {
			// 得到所有的锚点的ArrayList
			ArrayList<PoiItem> poiList = arg0.getPois();
			// 判断锚点ArrayList是否为空，不规则的关键词会造成当前List为空
			if (poiList != null) {
				for (int i = 0; i < poiList.size(); i++) {
					// 得到当前锚点
					PoiItem item = poiList.get(i);
					// 锚点属性设置
					MarkerOptions markerOptions = new MarkerOptions();
					// 得到POI的坐标
					LatLonPoint point = item.getLatLonPoint();
					// 得到锚点的详细坐标
					String detailAddr = item.getSnippet();
					// 得到POI的标题
					String name = item.getTitle();
					// 设置锚点的坐标
					markerOptions.position(new LatLng(point.getLatitude(),
							point.getLongitude()));
					// 设置锚点显示的详细信息
					markerOptions.snippet(detailAddr);
					// 设置锚点的标题
					markerOptions.title(name);
					// 添加锚点到地图上
					Marker marker = aMap.addMarker(markerOptions);
					// 将锚点加入到ArrayList中，再次搜索会清空地图中的锚点
					markerList.add(marker);
				}

				// 将地图放大到能显示所有锚点的区域
				zoomToAllMarkers(markerList);
			}
		}
	}
}
