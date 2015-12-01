package main;

import com.example.haofangchan2.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectDialog extends Dialog {
	private Context context;
	private String title;
	private String content;
	private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;
	public interface ClickListenerInterface {
		public void doCancel();
	    }
	public CollectDialog(Context context, String title,
			   String content) {
		super(context);
        this.context = context;
        this.content=content;
        this.title = title;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.collectdialogview, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.collect_title);
        TextView tvCotent = (TextView) view.findViewById(R.id.collect_content);
        ImageView tvCancel = (ImageView) view.findViewById(R.id.collect_button);


         tvTitle.setText(title);
         tvCotent.setText(content);
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
            case R.id.collect_button:
                 clickListenerInterface.doCancel();
                break;
             }
         }
 
     };
}
