package com.clicker.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TaskThread {

	protected static final String TAG = "SOCKET";
	private Timer t;
	private ClickerClient clickerClient;
	private Handler mDialogHandler;
	private BufferedReader inr;
	private ClickerClientApp app;
	private Thread thread;

	public TaskThread(ClickerClient ccl, Handler mDialog, BufferedReader inread) {
		clickerClient = ccl;
		app = (ClickerClientApp) clickerClient.getApplication();
		mDialogHandler = mDialog;
		inr = inread;
		Log.d(TAG, "creating task thread");
		thread = new Thread(new Runnable() {
			public void run() {

				t = new Timer();
				t.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						mDialogHandler.post(new Runnable() {
							public void run() {
								app.runTask(inr);
							}
						});
					}
				}, 500, 125);
			}
		});
		thread.start();
		
	}

	public void cancelTimer() {
		t.cancel();
		thread.stop();
		Log.d(TAG, "cancelling timer");
	}
}
