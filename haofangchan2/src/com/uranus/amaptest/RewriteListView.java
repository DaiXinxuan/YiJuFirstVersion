package com.uranus.amaptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class RewriteListView extends ListView {

	public RewriteListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RewriteListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RewriteListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// 注意构造器不要用错了

	public boolean onInterceptTouchEvent(MotionEvent ev) {

		return false;
	}

}
