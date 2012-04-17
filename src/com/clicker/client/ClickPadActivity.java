package com.clicker.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickPadActivity.
 */
public class ClickPadActivity extends AbstractQuestionActivity{
    
    private static final String COLOR = "color";

    private static final String FLAGS = "flags";

    private static final String QUESTION_NUMBER = "questionNumber";

    private static final String VALUES = "values";

    /** The Constant EVERYONE_GROUP_FLAG. */
    protected final static String EVERYONE_GROUP_FLAG = "e";
    
    /** The Constant EVERYONE_GROUP_STRING. */
    protected final static String EVERYONE_GROUP_STRING = "Everyone";
    
    /** The Constant SEMI_COLON_SEPARATOR. */
    protected final static String SEMI_COLON_SEPARATOR = "`/;";
    
    /** The Constant COMMA_SEPARATOR. */
    protected final static String COMMA_SEPARATOR      = "`/,";
    
    /** The Constant COLON_SEPARATOR. */
    protected final static String COLON_SEPARATOR      = "`/:";
    
    /** The Constant TILDE_SEPARATOR. */
    protected final static String TILDE_SEPARATOR      = "`/~";
    
    /** The Constant CARET_SEPARATOR. */
    protected final static String CARET_SEPARATOR      = "`/^";
    
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
    
    /** The MOUS e_ movemen t_ event. */
    private final byte MOUSE_MOVEMENT_EVENT = 0;
    
    /** The MOUS e_ butto n_ event. */
    private final byte MOUSE_BUTTON_EVENT = 1;
    
    /** The KEYBOAR d_ event. */
    private final byte KEYBOARD_EVENT = 2;
    
    /** The LEF t_ mous e_ click. */
    private final byte LEFT_MOUSE_CLICK = 0;
    
    /** The RIGH t_ mous e_ click. */
    private final byte RIGHT_MOUSE_CLICK = 1;
    
    /** The LEF t_ mous e_ down. */
    private final byte LEFT_MOUSE_DOWN = 2;
    
    /** The LEF t_ mous e_ up. */
    private final byte LEFT_MOUSE_UP = 3;
    
    /** The RIGH t_ mous e_ down. */
    private final byte RIGHT_MOUSE_DOWN = 4;
    
    /** The RIGH t_ mous e_ up. */
    private final byte RIGHT_MOUSE_UP = 5;
    
    /** The UNUSED. */
    private final byte UNUSED = 0;
    
    /** The SENSITIVITY. */
    private final float SENSITIVITY = 1.6f;
    
    /** The et advanced text. */
    private EditText etAdvancedText;
    
    /** The changed. */
    private String changed;
    
    /** The x coord. */
    private float xCoord;
    
    /** The y coord. */
    private float yCoord;
    //private DatagramSocket ds;
    //private byte[] b;
    //private DatagramPacket dp;
    /** The c. */
    private ClickerClient c;
    
    /** The message handler. */
    private Handler messageHandler;
    
    /** The left down. */
    private boolean leftDown;
    
    /** The right down. */
    private boolean rightDown;
    
    /** The m gesture detector. */
    private GestureDetector mGestureDetector;
    //private LinearLayout back;
    /** The back. */
    private RelativeLayout back;
    
    /** The ip. */
    private InetAddress ip;
    
    /** The port. */
    private int port;
    //TODO restore clickpad to working order, last working copy: revision 84
    /** The app. */
    private ClickerClientApp app;
    
    /** The clicker client. */
    private ClickerClient clickerClient;
    
    /** The values. */
    private String values;
    
    /** The question number. */
    private String questionNumber;
    
    /** The flags. */
    private String flags;
    
    /** The color. */
    private String color;
    
    /** The everyone group flag set. */
    private boolean everyoneGroupFlagSet;

    private Handler activityHandler;
    
    /**
     * Instantiates a new click pad activity.
     */
    public ClickPadActivity() {
        //super((ClickerClient) context, 0, ",");
        
        //Log.d(TAG, "clickpad started");
        leftDown = false;
        rightDown = false;
        messageHandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case VIBRATE_MSG:
                        //Log.d(TAG,"vibrate");
                        feedback();
                        break;
                    case LEFT_DOWN_MSG:
                        //Log.d(TAG,"left down");
                        leftButtonDown();
                        break;
                    case RIGHT_DOWN_MSG:
                        //Log.d(TAG,"right down");
                        rightButtonDown();
                        break;
                    case LEFT_UP_MSG:
                        //Log.d(TAG,"left up");
                        leftButtonUp();
                        break;
                    case RIGHT_UP_MSG:
                        //Log.d(TAG,"right up");
                        rightButtonUp();
                        break;
                    case LEFT_CLICK_MSG:
                        //Log.d(TAG,"left click");
                        leftClick();
                        break;
                    case RIGHT_CLICK_MSG:
                        //Log.d(TAG,"right click");
                        rightClick();
                        break;
                }
            }
        };
        mGestureDetector = new GestureDetector( new ClickGestureListener(messageHandler));
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        app = (ClickerClientApp)getApplication();
        clickerClient =app.getClickerClient(); 
        
        //Button keyboard = (Button)findViewById(R.id.keyboard_click_button);
        
        Bundle b = this.getIntent().getExtras();
        values = b.getString(VALUES);
        questionNumber = b.getString(QUESTION_NUMBER);
        flags = b.getString(FLAGS);
        color = b.getString(COLOR);
        
        processFlags();
        //keyboard.setOnTouchListener(this);
        
        //back = (LinearLayout)findViewById(R.id.click_pad_linear_layout);		
        values= ""; //TODO get values
        setHandler();
        //		Button.OnClickListener keyboardClickListener = new Button.OnClickListener() {
        //			public void onClick(View v){
        //				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //				//imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        //				//imm.toggleSoftInput(0, 0);
        //				imm.toggleSoftInputFromWindow(v.getWindowToken(),InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        //			}
        //		};
        //keyboard.setOnClickListener(keyboardClickListener);
        
        //		initAdvancedText();
        
        //		try {
        //
        //			ds = new DatagramSocket();
        //			ds.connect(ip, port);
        //		} catch (IOException e) {
        //			Log.d(TAG, "error creating datagram socket");
        //			e.printStackTrace();
        //		}
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#processValues()
     */
    protected void processValues(){
        String[] val = values.split(",");
        try {
            ip = InetAddress.getByName(val[0]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        port = Integer.parseInt(val[1]);
    }
    
    /**
     * Process flags.
     */
    protected void processFlags(){
        String[] flagTokens = flags.split(COMMA_SEPARATOR);
        for (String token : flagTokens){
            if(token.equals(EVERYONE_GROUP_FLAG)){
                everyoneGroupFlagSet = true;
            }
        }
    }
    
    /**
     * Sets the flags.
     */
    protected void setFlags(){
        
    }
    
    /**
     * Switch flags.
     *
     * @param flags the flags
     */
    protected void switchFlags(int flags){
        
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#removeView(int)
     */
    public void removeView(int i){
        
    }
    
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#resendLast()
     */
    public void resendLast(){
        
    }
    
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#setView()
     */
    protected void setView(){
        this.setContentView(R.layout.click_pad_layout);
        back = (RelativeLayout)findViewById(R.id.click_pad_relative_layout);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#closeActivity()
     */
    public void closeActivity(){
        finish();
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#setHandler()
     */
    protected void setHandler(){
        activityHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch(msg.what){
                    case ClickerConstants.CLOSE_DIALOG:
                        closeActivity();
                        break;
                }
            }
        };
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(TAG,"on touch!");
        if(mGestureDetector.onTouchEvent(event)){
            return true;
        }
        
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                rightClick();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                rightButtonUp();
                break;
        }
        return true;
    }	
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#createViews()
     */
    protected void createViews() {
        // do nothing
    }
    
    /**
     * Touch_start.
     *
     * @param x the x
     * @param y the y
     */
    private void touch_start(float x, float y) {
        xCoord = x;
        yCoord = y;
    }
    
    /**
     * Touch_move.
     *
     * @param x the x
     * @param y the y
     */
    private void touch_move(float x, float y) {
        sendEvent(MOUSE_MOVEMENT_EVENT, (byte)((x - xCoord) * SENSITIVITY), (byte)((y - yCoord) * SENSITIVITY));
        /*b = new byte[] { MOUSE_MOVEMENT_EVENT, (byte) ((x - xCoord) * 1.6),
				(byte) ((y - yCoord) * 1.6) };
		dp = new DatagramPacket(b, 3);
		try {
			ds.send(dp);
		} catch (IOException e) {
			Log.d(TAG, "ds: " + ds + " dp: " + dp);
			e.printStackTrace();
		}*/
        xCoord = x;
        yCoord = y;
    }
    
    /**
     * Touch_up.
     */
    private void touch_up() {
        xCoord = 0.0f;
        yCoord = 0.0f;
        if (leftDown){
            leftButtonUp();
        }
        if (rightDown){
            rightButtonUp();
        }
    }
    
    //	@Override
    //	public void onStop() {
    //		super.onStop();
    //		ds.close();
    //		Log.d(TAG, "onStop!");
    //	}
    
    /**
     * Feedback.
     */
    private void feedback(){
        //back.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }
    
    /**
     * Left click.
     */
    private void leftClick(){
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, LEFT_MOUSE_CLICK);
    }
    
    /**
     * Right click.
     */
    private void rightClick(){
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, RIGHT_MOUSE_CLICK);
    }
    
    /**
     * Left button down.
     */
    private void leftButtonDown(){
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, LEFT_MOUSE_DOWN);
        leftDown = true;
    }
    
    /**
     * Left button up.
     */
    private void leftButtonUp(){
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, LEFT_MOUSE_UP);
        leftDown = false;
    }
    
    /**
     * Right button down.
     */
    private void rightButtonDown(){
        //Log.d(TAG,"right button down!");
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, RIGHT_MOUSE_DOWN);
        rightDown = true;
    }
    
    /**
     * Right button up.
     */
    private void rightButtonUp(){
        sendEvent(MOUSE_BUTTON_EVENT, UNUSED, RIGHT_MOUSE_UP);
        rightDown = false;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        app.setSubHandler(activityHandler);
        app.setCurrentActivity(this);
    }
    
    /**
     * Inits the advanced text.
     */
    private void initAdvancedText() {
        EditText et = (EditText) this.findViewById(R.id.etAdvancedText);
        this.etAdvancedText = et;
        et.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);//make sure keyboard doesnt go fullscreen in landscape mode
        
        changed = "a  ";
        etAdvancedText.setText(changed);
        
        // listener
        et.setOnKeyListener(new View.OnKeyListener(){
            
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                //Log.d(TAG, "RD '" + event.getCharacters() + "' "
                //		+ keyCode);
                changed = "a  ";
                etAdvancedText.setText(changed);
                return false;
            }
            
        });
        et.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before,
                    int count)
            {
                if (s.toString().equals(changed))
                {
                    etAdvancedText.requestFocus();
                    etAdvancedText.setSelection(3);
                    return;
                }
                changed = null;
                
                // onAdvancedTextChanged(s, start, count);
                //Log.d(TAG,
                //		"rd '" + s.toString().substring(start, start + count)
                //				+ "' " + start + "|" + count + "count: "+count+" start:"+start+" before: "+before+".");
                String change = s.toString().substring(start, start + count);
                
                String text = etAdvancedText.getText().toString();
                if (text.length() == 2){
                    //Log.d(TAG,"backspace? text="+text+".");
                    sendKeys("\b");
                } else {
                    //Log.d(TAG,"not backspace:"+text+". Length:"+text.length());
                    sendKeys(Character.toString(text.charAt(3)));
                }
                changed = "a  ";
                etAdvancedText.setText(changed);
            }
            
            public void afterTextChanged(Editable s)
            {
                
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                
            }
        });
    }
    
    /*private void sendKey(int i){
		//Log.d(TAG,"sendKey(int): "+i);
		char ch;
		if(i == 67){
			ch = "\b".charAt(0);
			sendEvent(KEYBOARD_EVENT, UNUSED, (byte)ch);
		}
	}*/
    
    /**
     * Send keys.
     *
     * @param s the s
     */
    private void sendKeys(String s){
        //Log.d(TAG,"sendKey(string): "+s);
        char c = s.charAt(0);
        sendEvent(KEYBOARD_EVENT, UNUSED, (byte)c);
    }
    
    /**
     * Send event.
     *
     * @param deviceCode the device code
     * @param x the x
     * @param y the y
     */
    private void sendEvent(byte deviceCode, byte x, byte y){
        //		b = new byte[] { deviceCode, x, y};
        //		dp = new DatagramPacket(b, 3);
        //		try {
        //			ds.send(dp);
        //		} catch (IOException e) {
        //			Log.d(TAG, "ds: " + ds + " dp: " + dp);
        //			e.printStackTrace();
        //		}
        String group = "";
        if(everyoneGroupFlagSet){
            group = EVERYONE_GROUP_STRING;
        } else {
            group = app.getGroup();
        }
        
        String val = deviceCode+COLON_SEPARATOR+x+COLON_SEPARATOR+y;
        Log.d(ClickerConstants.TAG,"sending: "+val);
        app.getPushPrintWriter().println(group+SEMI_COLON_SEPARATOR+questionNumber +
                SEMI_COLON_SEPARATOR + val);
        app.getPushPrintWriter().flush();
    }
    /*
     * 	protected void sendValues(){
		String group = "";
		if(everyoneGroupFlagSet){
			group = EVERYONE_GROUP_STRING;
		} else {
			group = app.getGroup();
		}
		if(isInPushMode){
			app.getPushPrintWriter().println(group+SEMI_COLON_SEPARATOR + questionNumber 
					+ SEMI_COLON_SEPARATOR + getValues());
			app.getPushPrintWriter().flush();
		}
	}
     */
    
}

