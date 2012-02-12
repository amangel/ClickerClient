package com.clicker.client;

import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.widget.Button;

public class ClickGestureListener extends GestureDetector.SimpleOnGestureListener {
	public static final String TAG = "SOCKET";
	
	public static final int VIBRATE_MSG = 1000;
	
	public static final int LEFT_DOWN_MSG = 100;
	public static final int RIGHT_DOWN_MSG = 150;

	public static final int LEFT_UP_MSG = 200;
	public static final int RIGHT_UP_MSG = 250;

	public static final int LEFT_CLICK_MSG = 300;
	public static final int RIGHT_CLICK_MSG = 350;
	
	
	private boolean did;
	private Handler handler;

	public ClickGestureListener(Handler h){
		handler = h;
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent ev) {
		//Log.d(TAG, "onSingleTapUp "+ ev.toString());
		//Log.d(TAG,"single tap pointer count "+ev.getPointerCount());
		handler.sendEmptyMessage(LEFT_CLICK_MSG);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent ev) {
		//Log.d(TAG, "onShowPress "+ ev.toString());
	}

	@Override
	public void onLongPress(MotionEvent ev) {
		//Log.d(TAG, "onLongPress "+ ev.toString() +" "+ev.getPointerCount() );
		//Log.d(TAG,"current time: "+System.currentTimeMillis());
		//Log.d(TAG,"event time: "+ev.getEventTime());
		handler.sendEmptyMessage(VIBRATE_MSG);

		handler.sendEmptyMessage(LEFT_DOWN_MSG);
		//TODO:right button down
	}
	

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		//Log.d(TAG, "onScroll "+ e1.toString());
		return false;

	}

	@Override
	public boolean onDown(MotionEvent ev) {
		//Log.d(TAG, "onDownd "+ ev.toString());
		//Log.d(TAG,"time: "+ev.getEventTime());
		return false;

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		//Log.d(TAG, "onFling: d "+ e1.toString());
		//Log.d(TAG, "onFling: e2 "+ e2.toString());
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e){
		//Log.d(TAG,"on double tap!");
		//double click
		return false;
	}
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent e){
		//Log.d(TAG,"ondoubletapevent");
		return false;
	}
	
}
