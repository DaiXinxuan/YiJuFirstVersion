package main;

import main.CollectDialog;
import main.CollectDialog.ClickListenerInterface;
import testandmanage.LogUtil;
import android.content.Context;
import android.view.Window;

public class Handle_collect_dialog {
	public void cleardialog(Context context, String title,String content){
		  
		  final CollectDialog dialog=new CollectDialog(context,title, content);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    dialog.show();
		    //�ص�����ʵ���¼������¼�
		    dialog.setClicklistener(new ClickListenerInterface() {
				
				@Override
				public void doCancel() {
					// TODO Auto-generated method stub
					LogUtil.d("Setting", "false");
					dialog.dismiss();
				}
			});
	  }
}
