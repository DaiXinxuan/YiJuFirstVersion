package setting;

import android.content.Context;
import android.view.Window;

public class Handle_input_dialog {
	int judge;
	public Input_denfined_dialog dialog;

	public Handle_input_dialog(Context context, String title,
			String confirmButtonText) {
		dialog = new Input_denfined_dialog(context, title, confirmButtonText);
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
