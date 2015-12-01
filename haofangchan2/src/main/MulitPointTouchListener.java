package main;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MulitPointTouchListener implements OnTouchListener {

	private static final String TAG = "Touch";
	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	int recLen = 0;
	boolean isclick = true;
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	Timer timer = new Timer();
	myTask task;
	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	HouseinfoActivity houseinfoActivity;
	ConsultantActivity consultantActivity;

	public MulitPointTouchListener(HouseinfoActivity houseinfoActivity) {
		// TODO Auto-generated constructor stub
		this.houseinfoActivity = houseinfoActivity;
	}

	public MulitPointTouchListener(ConsultantActivity consultantActivity) {
		// TODO Auto-generated constructor stub
		this.consultantActivity = consultantActivity;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		ImageView view = (ImageView) v;
		// Log.e("view_width",
		// view.getImageMatrix()..toString()+"*"+v.getWidth());
		// Dump touch event to log
		dumpEvent(event);

		// Handle touch events here...
		((ImageView) v).setScaleType(ScaleType.MATRIX);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:

			matrix.set(view.getImageMatrix());
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			// Log.d(TAG, "mode=DRAG");
			mode = DRAG;
			isclick = true;
			recLen = 0;
			timer = new Timer();
			task = new myTask();
			timer.schedule(task, 0, 250);
			// Log.d(TAG, "mode=NONE");
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			// Log.d(TAG, "oldDist=" + oldDist);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				// Log.d(TAG, "mode=ZOOM");
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isclick == true) {
				if (houseinfoActivity != null)
					houseinfoActivity.setgone();
				else
					consultantActivity.setgone();
				task.cancel();
			}
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			// Log.e("view.getWidth", view.getWidth() + "");
			// Log.e("view.getHeight", view.getHeight() + "");

			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				// ...
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				// Log.d(TAG, "newDist=" + newDist);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}

		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		// Log.d(TAG, sb.toString());
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	class myTask extends TimerTask {
		public void run() {
			// TODO Auto-generated method stub
			recLen++;
			if (recLen > 1) {
				isclick = false;
				timer.cancel();
			}
		}
	};
}