package lib.morkim.mfw.ui;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MorkimGestureDetector extends SimpleOnGestureListener {
	public static final String TAG = "GestureDetector";

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		gestureListener.onTap(e.getX(), e.getY());

		return super.onSingleTapUp(e);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
			return false;
		if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			gestureListener.onScrollRight();
			return true;
		} else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			gestureListener.onScrollLeft();
			return true;
		}

		return false;
	}

	public interface MorkimGestureListener {
		public void onScrollRight();

		public void onScrollLeft();

		public void onTap(float x, float y);
	}

	private MorkimGestureListener gestureListener;

	public void setGestureListener(MorkimGestureListener listener) {
		gestureListener = listener;
	}
}
