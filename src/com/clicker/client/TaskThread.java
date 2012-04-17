package com.clicker.client;

import java.io.BufferedReader;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskThread.
 */
public class TaskThread {
    
    
    
    /** The t. */
    private Timer t;
    
    /** The clicker client. */
    private ClickerClient clickerClient;
    
    /** The m dialog handler. */
    private Handler mDialogHandler;
    
    /** The inr. */
    private BufferedReader inr;
    
    /** The app. */
    private ClickerClientApp app;
    
    /** The thread. */
    private Thread thread;
    
    /**
     * Instantiates a new task thread.
     *
     * @param ccl the ccl
     * @param mDialog the m dialog
     * @param inread the inread
     */
    public TaskThread(ClickerClient ccl, Handler mDialog, BufferedReader inread) {
        clickerClient = ccl;
        app = (ClickerClientApp) clickerClient.getApplication();
        mDialogHandler = mDialog;
        inr = inread;
        Log.d(ClickerConstants.TAG, "creating task thread");
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
    
    /**
     * Cancel timer.
     */
    public void cancelTimer() {
        t.cancel();
        thread.stop();
        Log.d(ClickerConstants.TAG, "cancelling timer");
    }
}
