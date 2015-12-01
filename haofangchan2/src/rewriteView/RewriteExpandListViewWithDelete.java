package rewriteView;

import testandmanage.LogUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.haofangchan2.R;

public class RewriteExpandListViewWithDelete extends ExpandableListView {

	private static String TAG = "HistoryExpandableListView";
	private float beginX = -1;
	private float beginY = -1;
	private float endX = -1;
	private float endY = -1;
	private float slideX = -1;
	private float slideY = -1;
	private Boolean isShowDelButton;
	private ExpandableListAdapter mAdapter;
	RewriteExpandListViewWithDelete parent;
	View v;
	int groupPosition = -1;
	int childPosition = -1;
	long id = -1;
	private Boolean isMove = false;
	private boolean slide = false;
	private ChildItemClickListener childItemClickListener;
	// 自定义的滑动删除监听
	private ExpandableListViewListener expandableListViewListener;

	public RewriteExpandListViewWithDelete(Context context) {
		super(context);

	}

	public RewriteExpandListViewWithDelete(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RewriteExpandListViewWithDelete(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获得第一个点的x坐标
			beginX = ev.getX(0);
			beginY = ev.getY(0);
			Log.v(TAG, "MotionEvent.ACTION_DOWN");

			break;

		case MotionEvent.ACTION_MOVE:

			// 得到最后一个点的坐标
			endX = ev.getX(ev.getPointerCount() - 1);
			endY = ev.getY(ev.getPointerCount() - 1);
			slideX = endX - beginX;
			slideY = Math.abs(endY - beginY);
			// 可以滑动删除的条件：横向滑动大于50，竖直差小于30
			if (slideX > 50.0 && slideY < 20) {
				slide = true;
				isShowDelButton = false;
			} else if (slideX < -50 && slideY < 20) {
				isShowDelButton = true;
				slide = true;
			} else if (slideX < 10 && slideY < 10) {
				slide = false;
				isMove = false;
			} else if (slideX > -10 && slideY < 10) {
				slide = false;
				isMove = false;
			} else {
				slide = true;
				isShowDelButton = false;
			}
			Log.v(TAG, "MotionEvent.ACTION_MOVE");
			break;

		case MotionEvent.ACTION_UP:
			if (slide && expandableListViewListener != null) {

//				expandableListViewListener.onChildClick(parent, v,
//						groupPosition, childPosition, id, beginX, beginY, endX,
//						endY, slideX, slideY);

				int firstVisiblePosition = getFirstVisiblePosition();
				int index = childPosition;
				// -1000表明当前点击的是组，不是子项
				if (childPosition != -1000) {
					View view;
					// 注意展开列表不同组getChildAt的值不同，还要根据是否展开来计算
					if (groupPosition == 0) {
						view = this
								.getChildAt(index - firstVisiblePosition + 1);
					} else {
						if (this.isGroupExpanded(0)) {
							view = this.getChildAt(index - firstVisiblePosition
									+ 2 + mAdapter.getChildrenCount(0));
						} else {
							view = this.getChildAt(index - firstVisiblePosition
									+ 2);
						}
					}
					// 拿到点击项中的按钮，设置为可见，并将其第几组第几项当作tag传入，方便适配器getView拿到对应数据做处理
					LogUtil.d("获得当前点击的Item", "" + view);
					Button button = (Button) view
							.findViewById(R.id.myfriendDeleteItemButton);
					LogUtil.d("获得当前点击的Item", "" + button);
					if (isShowDelButton) {
						button.setVisibility(View.VISIBLE);
						Positions group = new Positions();
						group.groupPos = groupPosition;
						group.childPos = childPosition;
						button.setTag(group);
						isMove = true;
					} else {
						button.setVisibility(View.GONE);
						isMove = false;
					}

				}

			} else if (!slide && childItemClickListener != null) {
				// 未滑动则是一般点击事件
				if (childPosition >= 0 && (!isMove)) {
					childItemClickListener.itemclick(parent, v, groupPosition,
							childPosition);
				}
			}
			// 重置所有值
			reset();
			Log.v(TAG, "MotionEvent.ACTION_UP");
			break;
		}

		return super.onTouchEvent(ev);
	}

	public void reset() {
		slide = false;
		beginX = -1;
		beginY = -1;
		endX = -1;
		endY = -1;
		slideX = -1;
		slideY = -1;
		groupPosition = -1;
		childPosition = -1;
		isMove = false;
		id = -1;
	}

	// 自定义接口
	public interface ChildItemClickListener {
		public void itemclick(RewriteExpandListViewWithDelete parent, View v,
				int groupPosition, int childPosition);
	}

	public void setChildItemClickListener(ChildItemClickListener c) {
		childItemClickListener = c;
	}

	// 自定义的接口
	public interface ExpandableListViewListener {
		// public void showDelete(float beginX, float beginY, float endX, float
		// endY, float slideX, float slideY);
		boolean onChildClick(RewriteExpandListViewWithDelete parent, View v,
				int groupPosition, int childPosition, long id, float beginX,
				float beginY, float endX, float endY, float slideX, float slideY);
	}

	public void setExpandableListViewDeleteListener(ExpandableListViewListener f) {
		expandableListViewListener = f;
	}

	@Override
	public long getSelectedPosition() {
		// TODO Auto-generated method stub
		return super.getSelectedPosition();
	}

	// 通过位置获得当前expandlistView的子项
	private Positions getPositionsByPosition(ExpandableListAdapter adapter,
			int position) {
		Positions result = new Positions();
		if (position >= 0) {
			int p = position + 1;
			for (int group = 0; group < adapter.getGroupCount(); group++) {
				// 减去组
				if (p - 1 <= 0) {
					result.groupPos = group;
					result.childPos = -1000;
					break;
				} else {
					p = p - 1;
				}
				// 减去组成员
				int childrenCount = isGroupExpanded(group) ? adapter
						.getChildrenCount(group) : 0;
				if (p - childrenCount <= 0) {
					result.groupPos = group;
					result.childPos = p - 1;
					break;
				} else {
					p = p - childrenCount;
				}
			}
		}
		return result;
	}

	/**
	 * 用于保存group和child的position的容器类
	 */
	public class Positions {
		public int groupPos = -1;
		public int childPos = -1;

		public boolean isGroup() {
			return groupPos != -1 && childPos == -1;
		}

		public boolean isChild() {
			return groupPos != -1 && childPos != -1;
		}

		@Override
		public String toString() {
			return "(" + groupPos + ", " + childPos + ")";
		}
	}

	// 2.什么时候获得x和y呢，当然要在ExpandableListView2的onInterceptTouchEvent中了
	// onInterceptTouchEvent的作用是intercept
	// touchEvent，就是让父布局来决定，是否截断touchEvent向下传递。
	//
	// 以我们这个问题来说，父布局是不需要截断事件的，我们只在里面记录事件的x和y就可以了，所以代码是这样的：

	// 一点击，则将点击位置的组和项记录下来，主要通过pointToPosition和getPositionByPosition方法
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 取得group/child position

		if (ev.getAction() == MotionEvent.ACTION_DOWN
				&& getExpandableListAdapter() != null) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			// 位置保存下来
			int item = pointToPosition(x, y);
			LogUtil.d("点击位置项：：", "" + item);

			mAdapter = (ExpandableListAdapter) getExpandableListAdapter();
			Positions group = getPositionsByPosition(mAdapter, item);
			groupPosition = group.groupPos;
			childPosition = group.childPos;
			LogUtil.d("点击位置项具体：：", "" + group);
		}
		return super.onInterceptTouchEvent(ev);
	}

}
