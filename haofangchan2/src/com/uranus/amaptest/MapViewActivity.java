package com.uranus.amaptest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import testandmanage.LogUtil;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class MapViewActivity extends Activity implements
		OnGeocodeSearchListener, OnMarkerClickListener, OnClickListener,
		OnPoiSearchListener {
	private static long InitType = 1;// ��ʼ����������֮������¥����¥��
	private static long NormalType = 2;// ��ͨ��������Ȥ�������
	private long searchType;// �������ͣ�ֻ��ΪinitType��NormalType
	private MapView mapView;// mapView
	private AMap aMap;
	private ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;// ��Ȥ��������ť
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
	private String mapSearchString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		ImageView mapBack = (ImageView) findViewById(R.id.map_back);
		mapBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MapViewActivity.this.finish();
			}
		});

		mapView = (MapView) findViewById(R.id.mapView);

		mapView.onCreate(savedInstanceState);

		aMap = mapView.getMap();
		aMap.setOnMarkerClickListener(this);
		btn1 = (ImageView) findViewById(R.id.button1);
		btn2 = (ImageView) findViewById(R.id.button2);
		btn3 = (ImageView) findViewById(R.id.button3);
		btn4 = (ImageView) findViewById(R.id.button4);
		btn5 = (ImageView) findViewById(R.id.button5);
		btn6 = (ImageView) findViewById(R.id.button6);
		btn7 = (ImageView) findViewById(R.id.button7);
		btn8 = (ImageView) findViewById(R.id.button8);

		btn1.setTag("��ͨ");
		btn2.setTag("����");
		btn3.setTag("����");
		btn4.setTag("����");
		btn5.setTag("ҽԺ");
		btn6.setTag("ѧУ");
		btn7.setTag("����");
		btn8.setTag("�г�");

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		
		markerList = new ArrayList<Marker>();
		initMarkerList = new ArrayList<Marker>();
		geocodeSearch = new GeocodeSearch(this);
		geocodeSearch.setOnGeocodeSearchListener(this);

		buidingAddress = getIntent().getStringExtra("address");
		saleBuildingAddress = getIntent().getStringExtra("saleAddress");

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

	// ��ť����¼�
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageView poImageView = (ImageView) v;
		// �趨Ϊ��ͨ����
		searchType = NormalType;
		keyword = poImageView.getTag().toString();
		mapSearchString = keyword;
		if (addressHashMap != null) {
			LogUtil.d("�Ƿ���addressHashMap", "��");
			addressList = addressHashMap.get(keyword);
		}
		LogUtil.d("�Ƿ���addressHashMap", "��");
		if (addressList != null) {
			// ����������������鲻Ϊ�գ������������еĵ�ַ��
			beginAddressToSearch();
		} else {
			// ���������������Ϊ�գ�������ť�Ĺؼ���
			Log.e("btn keyword", keyword);
			beginPOISearch();
		}
	}

	// ��ʼ��Ȥ���������
	private void beginPOISearch() {
		Query query = new PoiSearch.Query(keyword, keyword);
		PoiSearch poiSearch = new PoiSearch(this, query);

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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
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
		} else {
			// ��ͨ��ַ������posΪ��ǰ��ַ��addressList�е�λ�á�
			GeocodeQuery query = new GeocodeQuery(addressList.get(pos++), "");
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
			} else {
				// ����ê��ı���
				markerOptions.title("��ַ");
				// ����ê���ͼ��
				markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
				// ��ê����뵽��ͼ��
				Marker marker = aMap.addMarker(markerOptions);
				// ��ê����뵽ArrayList�У��ٴ���������յ�ͼ�е�ê��
				markerList.add(marker);

				// �жϵ�ǰ�����Ƿ񵽴���ArrayList�����һ��
				if (pos != addressList.size()) {
					// ������ǣ��������������
					GeocodeQuery query = new GeocodeQuery(
							addressList.get(pos++), "");
					geocodeSearch.getFromLocationNameAsyn(query);
				} else {
					// ����ǣ�����ͼ�Ŵ�����ʾ����ê�������
					zoomToAllMarkers(markerList);
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
		// TODO Auto-generated method stub
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
		LogUtil.d("��ͼ�����ַ�����վ", "map"+mapSearchString);
		// �ж������Ƿ�ɹ�
		if (arg1 == 0) {
			// �õ����е�ê���ArrayList
			ArrayList<PoiItem> poiList = arg0.getPois();
			// �ж�ê��ArrayList�Ƿ�Ϊ�գ�������Ĺؼ��ʻ���ɵ�ǰListΪ��
			LogUtil.d("��ͼ�����ַ�����վ", "map"+"boolean"+(poiList==null));
			if (poiList != null) {
				LogUtil.d("��ͼ�����ַ�����վ", "map"+poiList.size());
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
                    LogUtil.d("��ͼ�����ַ���", "map"+mapSearchString);
					switch (mapSearchString) {
					case "��ͨ":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.station));
						break;
					case "����":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.food));
						break;
					case "����":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.supermarket));
						break;
					case "����":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.entertainment));
						break;
					case "ҽԺ":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.hospital));
						break;
					case "ѧУ":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.school));
						break;
					case "����":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.bank));
						break;
					case "�г�":
						markerOptions.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.fair));
						break;

					default:
						markerOptions.icon(BitmapDescriptorFactory
								.defaultMarker());
						break;
					}

					// ����ê��ͼ��

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
