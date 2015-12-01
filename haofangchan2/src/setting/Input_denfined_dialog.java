package setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.haofangchan2.R;

public class Input_denfined_dialog extends Dialog {

	private Context context;
	private String title;
	private String confirmButtonText;
	private String cancleButtonText;
	private EditText edit;
	public String inputstr;
	private ClickListenerInterface clickListenerInterface;

	public interface ClickListenerInterface {

		public void doConfirm();

		public void doCancel();
	}

	public Input_denfined_dialog(Context context, String title,
			String confirmButtonText) {
		super(context);
		this.context = context;
		this.title = title;
		this.confirmButtonText = confirmButtonText;
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.inputdialogview, null);
		setContentView(view);

		TextView tvTitle = (TextView) view.findViewById(R.id.inputtitle);
		Button tvConfirm = (Button) view.findViewById(R.id.inputpositiveButton);
		Button tvCancel = (Button) view.findViewById(R.id.inputnegativeButton);
		edit = (EditText) view.findViewById(R.id.inputedit);

		tvTitle.setText(title);
		tvConfirm.setText(confirmButtonText);

		tvConfirm.setOnClickListener(new clickListener());
		tvCancel.setOnClickListener(new clickListener());
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		lp.width = (int) (d.widthPixels * 0.8);
		dialogWindow.setAttributes(lp);
	}

	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
		this.clickListenerInterface = clickListenerInterface;
	}

	private class clickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.inputpositiveButton:
				inputstr = edit.getText().toString();
				clickListenerInterface.doConfirm();
				break;
			case R.id.inputnegativeButton:
				clickListenerInterface.doCancel();
				break;
			}
		}

	};

}
