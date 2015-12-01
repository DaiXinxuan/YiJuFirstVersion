package load;


import testandmanage.LogUtil;

import testandmanage.MyApplication;
import add_salebuilding_demo.Add_salebuilding_activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.example.haofangchan2.R;

	public class  Load_Animation extends Activity{
	    private static final long SPLASH_DELAY_MILLIS = 3000;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);    
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
			View view=View.inflate(this, R.layout.activity_spalash, null);
			setContentView(view);
			AlphaAnimation aa = new AlphaAnimation(0.2f,1.0f);
	        aa.setDuration(SPLASH_DELAY_MILLIS);
	        view.startAnimation(aa);
	        aa.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
					
				}
				
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				public void onAnimationEnd(Animation animation) {
					goHome();
					
				}
			});
	        
		}
		private void goHome() {
			String payid=MyApplication.getpayid();
			Intent intent = null;
			if(payid!=null){
				intent=new Intent(Load_Animation.this,Add_salebuilding_activity.class);
			}else{
				intent = new Intent(Load_Animation.this, LoginActivity.class);
			}
	        this.startActivity(intent);
	        
	        finish();
	    }
	}

