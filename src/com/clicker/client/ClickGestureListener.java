package com.clicker.client;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving clickGesture events.
 * The class that is interested in processing a clickGesture
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addClickGestureListener<code> method. When
 * the clickGesture event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ClickGestureEvent
 */
public class ClickGestureListener extends GestureDetector.SimpleOnGestureListener {
    
    /** The Constant VIBRATE_MSG. */
    public static final int VIBRATE_MSG = 1000;
    
    /** The Constant LEFT_DOWN_MSG. */
    public static final int LEFT_DOWN_MSG = 100;
    
    /** The Constant RIGHT_DOWN_MSG. */
    public static final int RIGHT_DOWN_MSG = 150;
    
    /** The Constant LEFT_UP_MSG. */
    public static final int LEFT_UP_MSG = 200;
    
    /** The Constant RIGHT_UP_MSG. */
    public static final int RIGHT_UP_MSG = 250;
    
    /** The Constant LEFT_CLICK_MSG. */
    public static final int LEFT_CLICK_MSG = 300;
    
    /** The Constant RIGHT_CLICK_MSG. */
    public static final int RIGHT_CLICK_MSG = 350;
    
    
    /** The did. */
    private boolean did;
    
    /** The handler. */
    private Handler handler;
    
    /**
     * Instantiates a new click gesture listener.
     *
     * @param h the h
     */
    public ClickGestureListener(Handler h){
        handler = h;
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onSingleTapUp(android.view.MotionEvent)
     */
    @Override
    public boolean onSingleTapUp(MotionEvent ev) {
        //Log.d(TAG, "onSingleTapUp "+ ev.toString());
        //Log.d(TAG,"single tap pointer count "+ev.getPointerCount());
        handler.sendEmptyMessage(LEFT_CLICK_MSG);
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onShowPress(android.view.MotionEvent)
     */
    @Override
    public void onShowPress(MotionEvent ev) {
        //Log.d(TAG, "onShowPress "+ ev.toString());
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onLongPress(android.view.MotionEvent)
     */
    @Override
    public void onLongPress(MotionEvent ev) {
        //Log.d(TAG, "onLongPress "+ ev.toString() +" "+ev.getPointerCount() );
        //Log.d(TAG,"current time: "+System.currentTimeMillis());
        //Log.d(TAG,"event time: "+ev.getEventTime());
        handler.sendEmptyMessage(VIBRATE_MSG);
        
        handler.sendEmptyMessage(LEFT_DOWN_MSG);
        //TODO:right button down
    }
    
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        //Log.d(TAG, "onScroll "+ e1.toString());
        return false;
        
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDown(android.view.MotionEvent)
     */
    @Override
    public boolean onDown(MotionEvent ev) {
        //Log.d(TAG, "onDownd "+ ev.toString());
        //Log.d(TAG,"time: "+ev.getEventTime());
        return false;
        
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        //Log.d(TAG, "onFling: d "+ e1.toString());
        //Log.d(TAG, "onFling: e2 "+ e2.toString());
        return false;
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTap(MotionEvent e){
        //Log.d(TAG,"on double tap!");
        //double click
        return false;
    }
    
    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTapEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e){
        //Log.d(TAG,"ondoubletapevent");
        return false;
    }
    
}
