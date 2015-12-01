/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package scanzbar;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;

import com.example.haofangchan2.R;

import different_jsonparse.State_parse_Json;

import main.OrderMoreInfoActivity;
import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanzbarActivity extends Activity {

	private static final float BEEP_VOLUME = 0.10f;
	private static final long VIBRATE_DURATION = 200L;

	private Camera mCamera;
	private CameraPreview mPreview;
	private Handler autoFocusHandler;
	private MediaPlayer mediaPlayer;
	private boolean playBeep = true;

	ImageScanner scanner;

	private boolean previewing = true;

	static {
		System.loadLibrary("iconv");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scanbar);
        
		ImageView backImageView=(ImageView) findViewById(R.id.scanbar_back);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(-1);
				ScanzbarActivity.this.finish();
			}
		});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try {
			autoFocusHandler = new Handler();
			mCamera = getCameraInstance();

			scanner = new ImageScanner();
			scanner.setConfig(0, Config.X_DENSITY, 3);
			scanner.setConfig(0, Config.Y_DENSITY, 3);

			mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
			FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
			preview.addView(mPreview);
			
			mCamera.setPreviewCallback(previewCb);
			mCamera.startPreview();
			previewing = true;
			mCamera.autoFocus(autoFocusCB);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "没有摄像头权限", Toast.LENGTH_SHORT).show();
			
		}
		

		// 初始化声音和震动
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		// 如果手机是震动模式就震动
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
//		PackageManager pkm = getPackageManager();
//		boolean has_permission = (PackageManager.PERMISSION_GRANTED
//		== pkm.checkPermission("android.permission.CAMERA", "com.example.haofangchan2"));
//		int a= getApplicationContext().checkCallingPermission("android.permission.CAMERA");
//		Log.d("no","permission"+has_permission+" a= "+a);
//		if (has_permission) {
		// 这里才开始真的干活的

			}
//			// 初始化声音
//			initBeepSound();
//		}else {
//		// showToast("没有权限");
//		Toast.makeText(ScanzbarActivity.this, "没有设置权限", Toast.LENGTH_SHORT).show();
//		
//		}

		

	}

	public void onPause() {
		super.onPause();
		releaseCamera();
	}
	
	public void onBackPressed(){
		setResult(-1);
		ScanzbarActivity.this.finish();
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
		}
		return c;
	}

	private void releaseCamera() {
		if (mCamera != null) {
			previewing = false;
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}

	private Runnable doAutoFocus = new Runnable() {
		public void run() {
			if (previewing)
				mCamera.autoFocus(autoFocusCB);
		}
	};

	PreviewCallback previewCb = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();

			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);

			int result = scanner.scanImage(barcode);

			if (result != 0) {
				previewing = false;
				mCamera.setPreviewCallback(null);
				mCamera.stopPreview();
				playBeepSoundAndVibrate();
				
				SymbolSet syms = scanner.getResults();
				String jsonDataString=null;
				for (Symbol sym : syms) {
					LogUtil.d("收到数据：", ""+sym.getData());
					jsonDataString=sym.getData();
				}
				
				try {
					JSONObject jsonObject= new JSONObject(jsonDataString);
					String proid=jsonObject.getString("proid");
					
					if(proid!=null){
						int proidInt=Integer.parseInt(proid);
						setResult(proidInt);
						mCamera.stopPreview();
						ScanzbarActivity.this.finish();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
			}
		
	};

	// Mimic continuous auto-focusing
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};

	/**
	 * 初始化声音
	 */
//	private void initBeepSound() {
//		if (playBeep && mediaPlayer == null) {
//			setVolumeControlStream(AudioManager.STREAM_MUSIC);
//			mediaPlayer = new MediaPlayer();
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			mediaPlayer.setOnCompletionListener(beepListener);
//			AssetFileDescriptor file = getResources().openRawResourceFd(
//					R.raw.beep);
//			try {
//				mediaPlayer.setDataSource(file.getFileDescriptor(),
//						file.getStartOffset(), file.getLength());
//				file.close();
//				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//				mediaPlayer.prepare();
//			} catch (IOException e) {
//				mediaPlayer = null;
//			}
//		}
//	}

	/**
	 * 播放声音和震动
	 */
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		// 打开震动
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(VIBRATE_DURATION);
	}

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
}
