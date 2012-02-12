package com.clicker.client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PushServerWaitingActivity extends Activity{

	private static final String TAG = "SOCKET";
	
	private Handler activityHandler;
	private boolean canBackOut;
	private TextView groupLabel;
	
	private ClickerClientApp app;
	private TaskThread tt;
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
					//Log.d(TAG,"received close inside pushserverwaiting: "+msg.getWhen());
					
					finish();
					break;
				case ClickerConstants.REDRAW:
					redrawScreen();
				}
			}
		};
	}

	private void redrawScreen(){
		groupLabel.setText(app.getGroup());
	}
	
	protected void setColor(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.waiting_background);
		String color = app.getDefaultColor();
		try{
			layout.setBackgroundColor(Color.parseColor(color));	
		} catch (Exception e){
			Log.d(TAG,"Error in setting the background color of the waiting screen.");
			Log.d(TAG,"color attempted was: "+color);
		}
	}
	
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
	
	@Override
	protected void onResume() {
		super.onResume();
		//Log.d(TAG,"in push server, on resume");
		app.setQuestionActive(false);
		app.setSubHandler(activityHandler);
		app.setCurrentActivity(this);
		if(app.getCurrentlyDisconnected()){
			finish();
		}
	}

}
