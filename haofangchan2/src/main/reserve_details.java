package main;

import com.example.haofangchan2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class reserve_details extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reserve_details);
	}
	
}
