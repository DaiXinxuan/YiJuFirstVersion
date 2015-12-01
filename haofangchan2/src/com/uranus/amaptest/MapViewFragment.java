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
	private static long InitType = 1;// ��ʼ����������֮������¥����¥��
	private static long NormalType = 2;// ��ͨ��������Ȥ�������
	private long searchType;// �������ͣ�ֻ��ΪinitType��NormalType
	private MapView mapView;// mapView
	private AMap aMap;
	private GeocodeSearch geocodeSearch;// ��ַ������
	private ArrayList<Marker> markerList;// ê������
	private ArrayList<Marker> initMarkerList;// ¥�̺���¥������
	private ArrayList<String> addressList;// ��ǰ�����ĵ�ַ���飬������Ҫ���������е�ַ
	private String buidingAddress;// ¥�̵�ַ
	private String saleBuildingAddress;// ��¥����ַ
	private int pos;// ���鵱ǰλ��
	private String keyword;// �����ؼ���
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
		// fragment������������ͬ������ֻ��ʼ��һ�Σ������ظ����view
		if (view == null) {
			view = LayoutInflater.from(getActivity()).inflate(
					R.layout.mapviewfragment, null);
			mapView = (MapView) view.findViewById(R.id.mapViewFragment);
			// ���룬��Ȼ�޷���ʾ��ͼ
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
				LogUtil.d("��ͼλ��", "�ƶ�");
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

	// �Զ���ӿڣ����������ã��Գ�ʼ����ͼ
	public void initMap(String buildingAddress, String saleBuildingAddress) {
		this.buidingAddress = buildingAddress;
		this.saleBuildingAddress = saleBuildingAddress;
		LogUtil.d("��ͼ��ͼ", "Ϊ"+mapView);
		LogUtil.d("��ͼ","Ϊ"+aMap);
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

	// ��ʼ��Ȥ���������
	private void beginPOISearch() {
		Query query = new PoiSearch.Query(keyword, keyword);
		PoiSearch poiSearch = new PoiSearch(getActivity(), query);

		if (initMarkerList.size() > 0) {
			// �õ�¥�̵ľ�γ������
			Marker marker = initMarkerList.get(0);
			poiSearch.setBound(new SearchBound(new LatLonPoint(marker
					.getPosition().latitude, marker.getPosition().longitude),
					5000));
			// ������Ȥ�������Ļص�Ϊ�Լ�
			poiSearch.setOnPoiSearchListener(this);
			// ��ʼ����
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

	// ��ַ���������Ļص���
	private void beginAddressToSearch() {
		if (searchType == InitType) {
			// ��ʼ��ַ���������趨��¥����¥�̵�����
			GeocodeQuery query;

			if (pos == 0) {
				// ������¥��
				query = new GeocodeQuery(buidingAddress, "");
			} else {
				// ����¥��
				query = new GeocodeQuery(saleBuildingAddress, "");
			}

			// ��ʼ����
			geocodeSearch.getFromLocationNameAsyn(query);
		}
	}

	// ��ͨ�����Ļص��������������ĵ�ַ�ַ���������������Ļص���geocodeSearch.setOnGeocodeSearchListener(this);
	// ����������
	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == 0) {
			// �õ������ص�������ͬһ�������ĵ�ַ���ܻ�������������
			List<GeocodeAddress> results = arg0.getGeocodeAddressList();
			// �õ���һ�����������
			GeocodeAddress address = results.get(0);
			// �õ���ַ�ľ�γ�ȡ�
			LatLonPoint point = address.getLatLonPoint();
			// �õ���ϸ�ĵ�ַ��Ϣ
			String detailAddr = address.getFormatAddress();
			// ����ê���������Ϣ
			MarkerOptions markerOptions = new MarkerOptions();
			// ����ê��ľ�γ��
			markerOptions.position(new LatLng(point.getLatitude(), point
					.getLongitude()));
			// ����ê�����ʾ����ϸ��ַ
			markerOptions.snippet(detailAddr);

			if (searchType == InitType) {
				// �õ�֮ǰ��quary��ѯ���ж���¥������������¥������
				GeocodeQuery query = arg0.getGeocodeQuery();
				String lastAddress = query.getLocationName();

				if (lastAddress.equals(buidingAddress)) {
					// ����ϴ�������¥�̣��½�һ����¥������
					pos = 1;
					// ����ê��ı���
					markerOptions.title("¥�̵�ַ");
					// ����ê���ͼ��
					markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.property));
					// ��ê����뵽��ͼ��
					Marker marker = aMap.addMarker(markerOptions);
					// ��ê����뵽initMarkerList���棬���ں�����Ȥ������
					initMarkerList.add(marker);
					// ������һ����¥������
					beginAddressToSearch();
				} else {
					// ����ê��ı���
					markerOptions.title("��¥����ַ");
					// ����ê���ͼ��
					markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.sales));
					// ��ê����뵽��ͼ��
					Marker marker = aMap.addMarker(markerOptions);
					// ��ê����뵽initMarkerList���棬���ں�����Ȥ������
					initMarkerList.add(marker);
					// ����ͼ�Ŵ�����ʾ����ê�������
					zoomToAllMarkers(initMarkerList);
					// ����arrayList��λ��
					pos = 0;
				}
			}
		}
	}

	// ����ͼ�Ŵ�����ʾ����ê�������
	private void zoomToAllMarkers(ArrayList<Marker> markers) {
		// ͨ��builder���õ��Ŵ�����
		Builder builder = new Builder();

		for (int i = 0; i < markers.size(); i++) {
			// ����������굽buider��
			builder.include(markers.get(i).getPosition());
		}

		LatLngBounds bounds = builder.build();
		// ����ͼ�Ŵ��ܹ���ʾ������
		aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {

	}

	// ê�����Ļص�������ͨ��aMap.setOnMarkerClickListener(this);����
	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		if (arg0.isInfoWindowShown()) {
			// �����ʾ����ر�
			arg0.hideInfoWindow();
		} else {
			// ����رյģ�����ʾ
			arg0.showInfoWindow();
		}

		return false;
	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	// ������е�ê��
	private void clearMarkerList() {
		for (Marker marker : markerList) {
			// ����ǰ��ê��ӵ�ͼ��ɾ��
			marker.remove();
		}

		// ���ê������
		markerList.clear();
	}

	// POI��Points of interents�����ص�������ͨ��PoiSearch poiSearch = new PoiSearch(this,
	// query); ����������
	@Override
	public void onPoiSearched(PoiResult arg0, int arg1) {
		// TODO Auto-generated method stub
		// ����ͼ�����е�ê�����
		clearMarkerList();

		// �ж������Ƿ�ɹ�
		if (arg1 == 0) {
			// �õ����е�ê���ArrayList
			ArrayList<PoiItem> poiList = arg0.getPois();
			// �ж�ê��ArrayList�Ƿ�Ϊ�գ�������Ĺؼ��ʻ���ɵ�ǰListΪ��
			if (poiList != null) {
				for (int i = 0; i < poiList.size(); i++) {
					// �õ���ǰê��
					PoiItem item = poiList.get(i);
					// ê����������
					MarkerOptions markerOptions = new MarkerOptions();
					// �õ�POI������
					LatLonPoint point = item.getLatLonPoint();
					// �õ�ê�����ϸ����
					String detailAddr = item.getSnippet();
					// �õ�POI�ı���
					String name = item.getTitle();
					// ����ê�������
					markerOptions.position(new LatLng(point.getLatitude(),
							point.getLongitude()));
					// ����ê����ʾ����ϸ��Ϣ
					markerOptions.snippet(detailAddr);
					// ����ê��ı���
					markerOptions.title(name);
					// ���ê�㵽��ͼ��
					Marker marker = aMap.addMarker(markerOptions);
					// ��ê����뵽ArrayList�У��ٴ���������յ�ͼ�е�ê��
					markerList.add(marker);
				}

				// ����ͼ�Ŵ�����ʾ����ê�������
				zoomToAllMarkers(markerList);
			}
		}
	}
}
