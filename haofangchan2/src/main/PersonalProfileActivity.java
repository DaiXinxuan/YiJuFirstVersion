package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import actionsheet.ActionSheet;
import actionsheet.ActionSheet.ActionSheetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.hdodenhof.circleimageview.CircleImageView;
import different_jsonparse.PersonalInfoModelParser;
import different_jsonparse.State_parse_Json;
import differentjavabean.PersonalInfoModel;

public class PersonalProfileActivity extends FragmentActivity implements
		ActionSheetListener, OnClickListener {
	private EditText nickname, job, age, signature, name, phone, address,
			member, price, demand, income;
	private ImageButton goback;
	private CircleImageView head;
	private RelativeLayout genderlayout;
	private TextView sex, save;
	private boolean isgender;
	private CircleImageView headimage;
	private Uri photoUri;
	private Map<String, String> map, map2;
	private PersonalInfoModel pim;

	private static final int PHOTO_REQUEST_CAREMA = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	/* ͷ������ */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	private Bitmap headbitmap;
	private RelativeLayout backgroud;

	/*
	 * ������ȡ
	 */
	public void gallery(View view) {
		if (hasSdcard()) {
			tempFile = new File(Environment.getExternalStorageDirectory(),
					PHOTO_FILE_NAME);
		}
		// ����ϵͳͼ�⣬ѡ��һ��ͼƬ
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_GALLERY
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/*
	 * �������ȡ
	 */
	public void camera(View view) {
		// �������
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// �жϴ洢���Ƿ�����ã����ý��д洢
		if (hasSdcard()) {
			tempFile = new File(Environment.getExternalStorageDirectory(),
					PHOTO_FILE_NAME);
			// ���ļ��д���uri
			Uri uri = Uri.fromFile(tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		}
		// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CAREMA
		startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
	}

	/*
	 * ����ͼƬ
	 */
	private void crop(Uri uri) {
		// �ü�ͼƬ��ͼ
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// �ü���ı�����1��1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// �ü������ͼƬ�ĳߴ��С
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);

		intent.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
		intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
		intent.putExtra("return-data", true);
		// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	/*
	 * �ж�sdcard�Ƿ񱻹���
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void postFile() throws Exception {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("image", tempFile);
		params.put("param", "" + MyApplication.getpayid());
		client.post(MyApplication.base_url_upload, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						Toast.makeText(PersonalProfileActivity.this, "ʧ��",
								Toast.LENGTH_LONG).show();
						// ����ʱ�ļ�ɾ��
						tempFile.delete();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(PersonalProfileActivity.this, "�ɹ�",
								Toast.LENGTH_LONG).show();
						headimage.setImageBitmap(headbitmap);
						// ����ʱ�ļ�ɾ��
						tempFile.delete();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			// ����᷵�ص�����
			if (data != null) {
				// �õ�ͼƬ��ȫ·��
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAREMA) {
			// ��������ص�����
			if (hasSdcard()) {
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(PersonalProfileActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��",
						Toast.LENGTH_SHORT).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			// �Ӽ���ͼƬ���ص�����
			if (data != null) {

				try {
					// ���´���tempFile
					FileOutputStream out = new FileOutputStream(tempFile);
					// �õ�bitmap
					headbitmap = data.getParcelableExtra("data");
					// ��bitmap����Ϊ�ļ�
					headbitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					// �ϴ�ͷ��
					postFile();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void serverHandle(final String photopath) {
		HashMap<String, String> map = JSONCommand.JSON10026("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				nickname.setText(((PersonalInfoModel) model).getNickName());
				sex.setText(((PersonalInfoModel) model).getSex());
				signature.setText(((PersonalInfoModel) model).getIntroduce());
				name.setText(((PersonalInfoModel) model).getName());
				age.setText(((PersonalInfoModel) model).getAge());
				phone.setText(((PersonalInfoModel) model).getTel());
				address.setText(((PersonalInfoModel) model).getAddr());
				member.setText(((PersonalInfoModel) model).getMember());
				job.setText(((PersonalInfoModel) model).getJob());
				income.setText(((PersonalInfoModel) model).getIncome());
				demand.setText(((PersonalInfoModel) model).getHouseType());
				price.setText(((PersonalInfoModel) model).getWillPrice());
				InitPicture_setting.getImageLoader(photopath, headimage);
			}
		}, new PersonalInfoModelParser());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.profile);
		init();
		serverHandle(getIntent().getStringExtra("photo"));
	}

	private void init() {
		nickname = (EditText) findViewById(R.id.profile_nickname_edit);
		age = (EditText) findViewById(R.id.profile_age_edit);
		signature = (EditText) findViewById(R.id.profile_signature_edit);
		name = (EditText) findViewById(R.id.profile_name_edit);
		phone = (EditText) findViewById(R.id.profile_phone_edit);
		address = (EditText) findViewById(R.id.profile_address_edit);
		member = (EditText) findViewById(R.id.profile_member_edit);
		price = (EditText) findViewById(R.id.profile_price_edit);
		demand = (EditText) findViewById(R.id.profile_demand_edit);
		job = (EditText) findViewById(R.id.profile_job_edit);
		income = (EditText) findViewById(R.id.profile_income_edit);
		goback = (ImageButton) findViewById(R.id.profile_title_btn);
		head = (CircleImageView) findViewById(R.id.profile_head);
		genderlayout = (RelativeLayout) findViewById(R.id.profile_gender_layout);
		sex = (TextView) findViewById(R.id.profile_gender_text);
		headimage = (CircleImageView) findViewById(R.id.profile_head);
		backgroud = (RelativeLayout) findViewById(R.id.profile_head_layout);
		backgroud.getBackground().setAlpha(200);
		goback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent load = new Intent(getApplicationContext(),
						MainFragmentActivity.class);
				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(load);
				PersonalProfileActivity.this.finish();
			}
		});
		save = (TextView) findViewById(R.id.profile_title_save);
		save.setOnClickListener(this);
		head.setOnClickListener(this);
		genderlayout.setOnClickListener(this);
	}

	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "dismissed isCancle = " +
		// isCancel, 0).show();
	}

	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "click item index = " +
		// actionSheet.getId(),
		// 0).show();
		if (isgender) {
			if (index == 0)
				sex.setText("��");
			else
				sex.setText("Ů");
		} else {
			if (index == 0) {
				gallery(headimage);
			} else {
				camera(headimage);
			}
		}
	}

	@Override
	public void onClick(View e) {
		// TODO Auto-generated method stub
		setTheme(R.style.ActionSheetStyleIOS7);
		switch (e.getId()) {
		case R.id.profile_head:
			ActionSheet.createBuilder(this, getSupportFragmentManager())
					.setCancelButtonTitle("ȡ��")
					.setOtherButtonTitles("���ѡ��", "�������")
					.setCancelableOnTouchOutside(true).setListener(this).show();
			isgender = false;
			break;
		case R.id.profile_gender_layout:
			ActionSheet.createBuilder(this, getSupportFragmentManager())
					.setCancelButtonTitle("ȡ��").setOtherButtonTitles("��", "Ů")
					.setCancelableOnTouchOutside(true).setListener(this).show();
			isgender = true;
			break;
		case R.id.profile_title_save:

			PersonalInfoModel pim = new PersonalInfoModel();
			pim.setAddr(address.getText().toString());
			pim.setAge(age.getText().toString());
			pim.setHouseType(demand.getText().toString());
			pim.setIncome(income.getText().toString());
			pim.setIntroduce(signature.getText().toString());
			pim.setJob(job.getText().toString());
			pim.setMember(member.getText().toString());
			pim.setName(name.getText().toString());
			pim.setNickName(nickname.getText().toString());
			String sexString = null;
			if (sex.getText().toString().equals("��")) {
				sexString = "1";
			} else {
				sexString = "2";
			}
			pim.setSex(sexString);
			pim.setTel(phone.getText().toString());
			pim.setWillPrice(price.getText().toString());
			HashMap<String, String> map = JSONCommand.JSON10024(pim, "6", "1");
			ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
				@Override
				public void updateUI(Object model) {
					Boolean stateBoolean = (Boolean) model;
					if (stateBoolean) {
						Toast.makeText(PersonalProfileActivity.this,
								"����������ϳɹ���", Toast.LENGTH_SHORT).show();
					} else {

						Toast.makeText(PersonalProfileActivity.this,
								"��������æ�������������ʧ�ܣ����Ժ����ԣ�", Toast.LENGTH_SHORT)
								.show();
					}

				}
			}, new State_parse_Json());

			break;
		default:
			break;
		}
	}

}
