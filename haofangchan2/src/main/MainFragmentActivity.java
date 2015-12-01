package main;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import main_fragment.Main_Fragment;
import main_fragment.Messagefragment;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.example.haofangchan2.R;
import com.readystatesoftware.viewbadger.BadgeView;

import differentjavabean.MyfriendActivity_javabean;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MainFragmentActivity extends FragmentActivity{
	private Fragment[] mFragments;  
    private RadioGroup bottomRg;  
    private FragmentManager fragmentManager;  
    private FragmentTransaction fragmentTransaction;  
    private RadioButton rbOne, rbTwo, rbThree, rbFour,rbback,rbThree2;  
    String proid="1";
   
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);  
        setContentView(R.layout.main_fragmentactivity_layout);  
        mFragments = new Fragment[4];  
        fragmentManager = getSupportFragmentManager();  
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_main);  
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_activity);  
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragement_message);  
        mFragments[3] = fragmentManager.findFragmentById(R.id.fragement_personalcenter);
        
        fragmentTransaction = fragmentManager.beginTransaction()  
                .hide(mFragments[0]).hide(mFragments[1])
                .hide(mFragments[2]).hide(mFragments[3]); 
        Intent intent=getIntent();
        int id=0;
        
        if(intent.getExtras()!=null){
        	id=intent.getIntExtra("backactivity",0);
        }
        if(id==0){
        	((Main_Fragment) mFragments[0]).setProid(proid);
        	fragmentTransaction.show(mFragments[0]).commit();
        }else{
        fragmentTransaction.show(mFragments[id]).commit();  }
        
        bottomRg = (RadioGroup) findViewById(R.id.mainbottomRg);
        rbback=(RadioButton) bottomRg.getChildAt(id);
        rbback.setChecked(true);
        setFragmentIndicator();  
        
        
        
        int unreadCount = 0;
		EMConversation conversation;
		List<MyfriendActivity_javabean> recentCont = new ArrayList<MyfriendActivity_javabean>();
		recentCont = DataSupport.findAll(MyfriendActivity_javabean.class);
		for (int i = 0; i < recentCont.size(); i++) {
			MyfriendActivity_javabean recentFriend = recentCont.get(i);
			conversation = EMChatManager.getInstance().// 未读消息小红点显示
					getConversation(recentFriend.getHxusername());
			unreadCount += conversation.getUnreadMsgCount();
		}
	RadioButton	messageTintRadioButton=(RadioButton) findViewById(R.id.rbThree2);
	 BadgeView childPhotoBadgeView = new BadgeView(MainFragmentActivity.this,
			messageTintRadioButton);
		Messagefragment messagefragment=(Messagefragment) mFragments[2];
		messagefragment.childPhotoBadgeView=childPhotoBadgeView;
		messagefragment.messageTintRadioButton=messageTintRadioButton;
        messagefragment.showTabBadgeView();
        
    }  
  
    
    private void setFragmentIndicator() {  
  
        rbOne = (RadioButton) findViewById(R.id.rbOne);  
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);  
        rbThree = (RadioButton) findViewById(R.id.rbThree);  
        rbFour = (RadioButton) findViewById(R.id.rbFour);  
        rbThree2=(RadioButton)findViewById(R.id.rbThree2);
        bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
  
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                fragmentTransaction = fragmentManager.beginTransaction()  
                        .hide(mFragments[0]).hide(mFragments[1])  
                        .hide(mFragments[2]).hide(mFragments[3]);  
                switch (checkedId) {  
                case R.id.rbOne:  
                    fragmentTransaction.show(mFragments[0]).commit();  
                    break;  
  
                case R.id.rbTwo:  
                    fragmentTransaction.show(mFragments[1]).commit();  
                    break;  
  
                case R.id.rbThree:  
                    fragmentTransaction.show(mFragments[2]).commit();
                    Messagefragment messagefragment=(Messagefragment) mFragments[2];
                    messagefragment.hideTabBadgeView();
                    break;  
  
                case R.id.rbFour:  
                	fragmentTransaction.show(mFragments[3]).commit();  
                	break;  
                	
                default:  
                    break;  
                }  
            }  
        });  
    }  
  
}  

