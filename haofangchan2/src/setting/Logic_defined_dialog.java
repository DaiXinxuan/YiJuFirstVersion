package setting;


import com.example.haofangchan2.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Logic_defined_dialog extends Dialog{
	
		   private Context context;
		   private String title;
		   private String confirmButtonText;
		   private String cacelButtonText;
		   private String content;
		    
	     private ClickListenerInterface clickListenerInterface;
	
		    public interface ClickListenerInterface {
		
		        public void doConfirm();
		
		        public void doCancel();
		    }
		
		   public Logic_defined_dialog(Context context, String title,
				   String content,
				   String confirmButtonText, String cacelButtonText) {
		        super(context);
		        this.context = context;
		        this.content=content;
		        this.title = title;
		        this.confirmButtonText = confirmButtonText;
	            this.cacelButtonText = cacelButtonText;
	     }
		
	   
		     protected void onCreate(Bundle savedInstanceState) {
		       // TODO Auto-generated method stub
		         super.onCreate(savedInstanceState);
		
		         init();
		    }
		 
		    public void init() {
		        LayoutInflater inflater = LayoutInflater.from(context);
		        View view = inflater.inflate(R.layout.dialogview, null);
	            setContentView(view);
	 
		        TextView tvTitle = (TextView) view.findViewById(R.id.title);
		        TextView tvCotent = (TextView) view.findViewById(R.id.tv_content);
		        Button tvConfirm = (Button) view.findViewById(R.id.positiveButton);
		        Button tvCancel = (Button) view.findViewById(R.id.negativeButton);

	 
		         tvTitle.setText(title);
		         tvConfirm.setText(confirmButtonText);
		         tvCancel.setText(cacelButtonText);
		         tvCotent.setText(content);
	
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
		            case R.id.positiveButton:
		                 clickListenerInterface.doConfirm();
		                break;
		            case R.id.negativeButton:
		                clickListenerInterface.doCancel();
	                 break;
		             }
		         }
		 
		     };
		 
		 }
