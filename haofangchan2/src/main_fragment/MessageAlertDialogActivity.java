package main_fragment;

import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.haofangchan2.R;

public class MessageAlertDialogActivity extends Activity {

	private String content = null;
	private String title = null;
	private Dialog b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(MyApplication
				.getContext());
		View view = layoutInflater.inflate(R.layout.dialogview, null);

		b = new Dialog(MessageAlertDialogActivity.this, R.style.NobackDialog);

		b.setContentView(view);
		title = getIntent().getStringExtra("title");
		b.show();
		b.setCancelable(false);

		// final Dialog d = b.create();
		// d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		// d.show();
		// d.setCancelable(false);// 设置点击空白处，dialog不消失
		Button yesButton = (Button) view.findViewById(R.id.positiveButton);
		Button cancelButton = (Button) view.findViewById(R.id.negativeButton);
		TextView contentTextView = (TextView) view
				.findViewById(R.id.tv_content);
		TextView title = (TextView) view.findViewById(R.id.title);
		content = getIntent().getStringExtra("content");
		contentTextView.setText(content);
		title.setText("宜居");
		LogUtil.d("执行带这里", "创建dialog");
		yesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Messagefragment.dialogInterface.dialogSureClick();
				b.dismiss();
				MessageAlertDialogActivity.this.finish();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Messagefragment.dialogInterface.dialogCancleClick();
				b.dismiss();
				MessageAlertDialogActivity.this.finish();
			}
		});
	}

}
