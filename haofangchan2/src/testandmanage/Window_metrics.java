package testandmanage;

import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;


public class Window_metrics extends Activity{

	public static int getScreenWidth() { 
	    WindowManager manager = (WindowManager) MyApplication.getContext() 
	            .getSystemService(MyApplication.getContext().WINDOW_SERVICE); 
	    Display display = manager.getDefaultDisplay(); 
	    return display.getWidth(); 
	} 
	//获取屏幕的高度 
	public static int getScreenHeight() { 
	    WindowManager manager = (WindowManager)  MyApplication.getContext() 
	            .getSystemService( MyApplication.getContext().WINDOW_SERVICE); 
	    Display display = manager.getDefaultDisplay(); 
	    return display.getHeight(); 
	}
}
