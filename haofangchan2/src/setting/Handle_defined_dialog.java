package setting;

import android.content.Context;
import android.view.Window;

public class Handle_defined_dialog {
	int judge;
	public Logic_defined_dialog dialog;

	public Handle_defined_dialog(Context context, String title, String content,
			String confirmButtonText, String cacelButtonText) {
		dialog = new Logic_defined_dialog(context, title, content,
				confirmButtonText, cacelButtonText);
	}

	public void cleardialog() {
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		// //回调机制实现事件监听事件
		// dialog.setClicklistener(new ClickListenerInterface() {
		// @Override
		// public void doConfirm() {
		// // TODO Auto-generated method stub
		// LogUtil.d("Setting", "true");
		// dialog.dismiss();
		//
		// }
		// @Override
		// public void doCancel() {
		// // TODO Auto-generated method stub
		// LogUtil.d("Setting", "false");
		// dialog.dismiss();
		// }
		// });
	}
}
