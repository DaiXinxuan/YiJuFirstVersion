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
	// �Զ���Ļ���ɾ������
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
			// ��õ�һ�����x����
			beginX = ev.getX(0);
			beginY = ev.getY(0);
			Log.v(TAG, "MotionEvent.ACTION_DOWN");

			break;

		case MotionEvent.ACTION_MOVE:

			// �õ����һ���������
			endX = ev.getX(ev.getPointerCount() - 1);
			endY = ev.getY(ev.getPointerCount() - 1);
			slideX = endX - beginX;
			slideY = Math.abs(endY - beginY);
			// ���Ի���ɾ�������������򻬶�����50����ֱ��С��30
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
				// -1000������ǰ��������飬��������
				if (childPosition != -1000) {
					View view;
					// ע��չ���б�ͬ��getChildAt��ֵ��ͬ����Ҫ�����Ƿ�չ��������
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
					// �õ�������еİ�ť������Ϊ�ɼ���������ڼ���ڼ����tag���룬����������getView�õ���Ӧ����������
					LogUtil.d("��õ�ǰ�����Item", "" + view);
					Button button = (Button) view
							.findViewById(R.id.myfriendDeleteItemButton);
					LogUtil.d("��õ�ǰ�����Item", "" + button);
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
				// δ��������һ�����¼�
				if (childPosition >= 0 && (!isMove)) {
					childItemClickListener.itemclick(parent, v, groupPosition,
							childPosition);
				}
			}
			// ��������ֵ
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

	// �Զ���ӿ�
	public interface ChildItemClickListener {
		public void itemclick(RewriteExpandListViewWithDelete parent, View v,
				int groupPosition, int childPosition);
	}

	public void setChildItemClickListener(ChildItemClickListener c) {
		childItemClickListener = c;
	}

	// �Զ���Ľӿ�
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

	// ͨ��λ�û�õ�ǰexpandlistView������
	private Positions getPositionsByPosition(ExpandableListAdapter adapter,
			int position) {
		Positions result = new Positions();
		if (position >= 0) {
			int p = position + 1;
			for (int group = 0; group < adapter.getGroupCount(); group++) {
				// ��ȥ��
				if (p - 1 <= 0) {
					result.groupPos = group;
					result.childPos = -1000;
					break;
				} else {
					p = p - 1;
				}
				// ��ȥ���Ա
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
	 * ���ڱ���group��child��position��������
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

	// 2.ʲôʱ����x��y�أ���ȻҪ��ExpandableListView2��onInterceptTouchEvent����
	// onInterceptTouchEvent��������intercept
	// touchEvent�������ø��������������Ƿ�ض�touchEvent���´��ݡ�
	//
	// ���������������˵���������ǲ���Ҫ�ض��¼��ģ�����ֻ�������¼�¼���x��y�Ϳ����ˣ����Դ����������ģ�

	// һ������򽫵��λ�õ�������¼��������Ҫͨ��pointToPosition��getPositionByPosition����
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ȡ��group/child position

		if (ev.getAction() == MotionEvent.ACTION_DOWN
				&& getExpandableListAdapter() != null) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			// λ�ñ�������
			int item = pointToPosition(x, y);
			LogUtil.d("���λ�����", "" + item);

			mAdapter = (ExpandableListAdapter) getExpandableListAdapter();
			Positions group = getPositionsByPosition(mAdapter, item);
			groupPosition = group.groupPos;
			childPosition = group.childPos;
			LogUtil.d("���λ������壺��", "" + group);
		}
		return super.onInterceptTouchEvent(ev);
	}

}
