package com.clicker.client;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class PushServerWaitingActivity.
 */
public class PushServerWaitingActivity extends Activity{
    
    
    /** The activity handler. */
    private Handler activityHandler;
    
    /** The can back out. */
    private boolean canBackOut;
    
    /** The group label. */
    private TextView groupLabel;
    
    /** The app. */
    private ClickerClientApp app;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        canBackOut = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_server_waiting_screen);
        
        app = (ClickerClientApp)getApplication();
        setColor();
        
        groupLabel = (TextView) findViewById(R.id.waiting_group_textview);
        groupLabel.setGravity(android.view.Gravity.CENTER);
        groupLabel.setText(app.getGroup());
        Button bDisconnect = (Button) findViewById(R.id.disconnect_button);
        Button.OnClickListener mDisconnect = new Button.OnClickListener() {
            public void onClick(View v) {
                if (app.getPushSocket() != null) {
                    if (app.getPushSocket().isConnected()) {
                        app.disconnectServer();
                        finish();
                    }
                }
            }
        };
        bDisconnect.setOnClickListener(mDisconnect);
        
        activityHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch(msg.what){
                    case ClickerConstants.CLOSE_PUSH:
                        //Log.d(ClickerConstants.TAG,"received close inside pushserverwaiting: "+msg.getWhen());
                        
                        finish();
                        break;
                    case ClickerConstants.REDRAW:
                        redrawScreen();
                }
            }
        };
    }
    
    /**
     * Redraw screen.
     */
    private void redrawScreen(){
        groupLabel.setText(app.getGroup());
    }
    
    /**
     * Sets the color.
     */
    protected void setColor(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.waiting_background);
        String color = app.getDefaultColor();
        try{
            layout.setBackgroundColor(Color.parseColor(color));	
        } catch (Exception e){
            Log.d(ClickerConstants.TAG,"Error in setting the background color of the waiting screen.");
            Log.d(ClickerConstants.TAG,"color attempted was: "+color);
        }
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    public void onBackPressed(){
        if(canBackOut){
            app.disconnectServer();
            finish();
        }
        Toast.makeText(this, "Press back again within 3 seconds to exit.", Toast.LENGTH_SHORT).show();
        canBackOut = true;
        Timer t = new Timer();
        
        t.schedule(new TimerTask() {
            public void run() {
                canBackOut=false;
            };
        } ,3000
                );
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(ClickerConstants.TAG,"in push server, on resume");
        app.setQuestionActive(false);
        app.setSubHandler(activityHandler);
        app.setCurrentActivity(this);
        if(app.getCurrentlyDisconnected()){
            finish();
        }
    }
    
}
