package com.clicker.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * The Class ClickerClientApp.
 */
public class ClickerClientApp extends Application{
    
    private static final String CONNECTED = "connected";

    private static final String DEFAULT = "default";

    private static final String COLOR = "color";

    private static final String VALUES = "values";

    private static final String FLAGS = "flags";

    private static final String QUESTION_NUMBER = "questionNumber";

    private static final String DEFAULT_COLOR = "#FF445566";

    /** The Constant SEMI_COLON_SEPARATOR. */
    protected static final String SEMI_COLON_SEPARATOR = "`/;";
    
    /** The HEARTBEA t_ count. */
    protected final int HEARTBEAT_COUNT = 120; //runtask runs every 125 ms. 120*125 = 15000ms
    
    /** The Constant DISMISS_DIALOG. */
    protected static final int DISMISS_DIALOG = 10125;
    
    /** The heartbeat current count. */
    private int heartbeatCurrentCount;
    
    /** The waiting for heartbeat to return. */
    private boolean waitingForHeartbeatToReturn;
    
    /** The currently disconnected. */
    private boolean currentlyDisconnected;
    
    /** The push socket. */
    private Socket pushSocket;
    
    /** The pull socket. */
    private Socket pullSocket;
    
    /** The pull server ip. */
    private InetAddress pullServerIp;
    
    /** The pull server port. */
    private int pullServerPort;
    
    /** The task thread. */
    private TaskThread taskThread;
    
    /** The push print writer. */
    private PrintWriter pushPrintWriter;
    
    /** The clicker client. */
    private ClickerClient clickerClient;
    
    /** The m dialog handler. */
    private Handler mDialogHandler;
    
    /** The sub handler. */
    private Handler subHandler;
    
    /** The user name. */
    private String userName;
    
    /** The user mac. */
    private String userMAC;
    
    /** The admin name. */
    private String adminName;
    
    /** The is question push mode. */
    private boolean isQuestionPushMode;
    
    /** The question active. */
    private boolean questionActive;
    
    /** The active socket. */
    private Socket activeSocket;
    
    /** The default color. */
    private String defaultColor;
    
    /** The group. */
    private String group;
    
    /** The reconnect attempts. */
    private int reconnectAttempts = 5;
    
    /** The connect timeout. */
    private int connectTimeout = 5000;
    
    /** The connect string. */
    private String connectString;
    
    /** The push ip. */
    private InetAddress pushIp;
    
    /** The push port. */
    private int pushPort;
    
    /** The current activity. */
    private Activity currentActivity;
    
    /** The dialog. */
    private ProgressDialog dialog;
    
    /** The reconnection thread. */
    private Thread reconnectionThread;
    
    /** The disconnected count. */
    private int disconnectedCount;
    
    
    /**
     * Instantiates a new clicker client app.
     */
    public ClickerClientApp(){
        connectString = "";
        heartbeatCurrentCount = 0;
        waitingForHeartbeatToReturn = false;
        currentlyDisconnected = true;
        pushSocket = new Socket();
        pullSocket = new Socket();
        activeSocket = new Socket();
        createHandler();
        adminName = "";
        group = "";
        defaultColor = DEFAULT_COLOR;
        clickerClient = null;
        subHandler = new Handler();
        userName = "";
        isQuestionPushMode = false;
        questionActive = false;
        disconnectedCount = 0;
        //Log.d("SOCKET", "application created");
    }
    
    /**
     * Sets the current activity.
     *
     * @param a the new current activity
     */
    public void setCurrentActivity(Activity a){
        currentActivity = a;
    }
    
    /**
     * Sets the connect string.
     *
     * @param s the new connect string
     */
    public void setConnectString(String s){
        connectString = s;
    }
    
    /**
     * Gets the connect string.
     *
     * @return the connect string
     */
    public String getConnectString(){
        return connectString;
    }
    
    /**
     * Sets the currently disconnected.
     *
     * @param bool the new currently disconnected
     */
    public void setCurrentlyDisconnected(boolean bool){
        currentlyDisconnected = bool;
    }
    
    /**
     * Gets the currently disconnected.
     *
     * @return the currently disconnected
     */
    public boolean getCurrentlyDisconnected(){
        return currentlyDisconnected;
    }
    
    /**
     * Gets the default color.
     *
     * @return the default color
     */
    public String getDefaultColor(){return defaultColor;}
    
    /**
     * Sets the default color.
     *
     * @param color the new default color
     */
    public void setDefaultColor(String color){defaultColor = color;}
    
    /**
     * Gets the group.
     *
     * @return the group
     */
    public String getGroup(){return group;}
    
    /**
     * Sets the group.
     *
     * @param gName the new group
     */
    public void setGroup(String gName){group = gName;}
    
    /**
     * Sets the question active.
     *
     * @param answer the new question active
     */
    public void setQuestionActive(boolean answer){questionActive = answer;}
    
    /**
     * Sets the admin name.
     *
     * @param name the new admin name
     */
    public void setAdminName(String name){adminName = name;}
    
    /**
     * Gets the admin name.
     *
     * @return the admin name
     */
    public String getAdminName(){return adminName;}
    
    /**
     * Sets the active socket.
     *
     * @param s the new active socket
     */
    public void setActiveSocket(Socket s){activeSocket = s;}
    
    /**
     * Gets the active socket.
     *
     * @return the active socket
     */
    public Socket getActiveSocket(){return activeSocket;}
    
    /**
     * Sets the pull server ip.
     *
     * @param IP the new pull server ip
     */
    public void setPullServerIP(InetAddress IP){pullServerIp = IP;}
    
    /**
     * Sets the pull server port.
     *
     * @param port the new pull server port
     */
    public void setPullServerPort(int port){pullServerPort = port;}
    
    /**
     * Connect to pull server.
     */
    public void connectToPullServer(){
        if (getPushSocket()!= null){
            if(getPushSocket().isConnected()){
                disconnectServer();
            }
        }
        try {
            pullSocket = new Socket(pullServerIp, pullServerPort);
            pullSocket.setSoTimeout(5000);
        } catch (IOException e) {
            Log.d(ClickerConstants.TAG,"Error while connecting to pull server in ccApp");
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the pull server buffered reader.
     *
     * @return the pull server buffered reader
     */
    public BufferedReader getPullServerBufferedReader(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(pullSocket.getInputStream()));
        } catch (IOException e) {
            Log.d(ClickerConstants.TAG, "Error while trying to create buffered reader for pull socket");
            e.printStackTrace();
        }
        return br;
    }
    
    /**
     * Gets the pull server print writer.
     *
     * @return the pull server print writer
     */
    public PrintWriter getPullServerPrintWriter(){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(pullSocket.getOutputStream(), true);
        } catch (IOException e) {
            Log.d(ClickerConstants.TAG,"Error while trying to create printwriter for pull socket");
            e.printStackTrace();
        }
        return pw;
    }
    
    /**
     * Sets the question in push mode.
     *
     * @param flag the new question in push mode
     */
    public void setQuestionInPushMode(boolean flag){isQuestionPushMode = flag;}
    
    /**
     * Checks if is question in push mode.
     *
     * @return true, if is question in push mode
     */
    public boolean isQuestionInPushMode(){return isQuestionPushMode;}
    
    /**
     * Sets the user name.
     *
     * @param name the new user name
     */
    public void setUserName(String name){userName = name;}
    
    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName(){return userName;}
    
    /**
     * Sets the mAC address.
     *
     * @param MAC the new mAC address
     */
    public void setMACAddress(String MAC){userMAC = MAC;}
    
    /**
     * Gets the mAC address.
     *
     * @return the mAC address
     */
    public String getMACAddress(){return userMAC;}
    
    /**
     * Close question.
     */
    public void closeQuestion(){
        subHandler.sendEmptyMessage(ClickerConstants.CLOSE_DIALOG);
    }
    
    /**
     * Sets the sub handler.
     *
     * @param h the new sub handler
     */
    public void setSubHandler(Handler h){subHandler = h;}
    
    /**
     * Gets the handler.
     *
     * @return the handler
     */
    public Handler getHandler(){return mDialogHandler;}
    
    /**
     * Gets the clicker client.
     *
     * @return the clicker client
     */
    public ClickerClient getClickerClient() {return clickerClient;}
    
    /**
     * Sets the clicker client.
     *
     * @param clickerClient the new clicker client
     */
    public void setClickerClient(ClickerClient clickerClient) {this.clickerClient = clickerClient;}
    
    /**
     * Sets the push socket.
     *
     * @param socket the new push socket
     */
    public void setPushSocket(Socket socket) {pushSocket = socket;}
    
    /**
     * Gets the push socket.
     *
     * @return the push socket
     */
    public Socket getPushSocket() {return pushSocket;}
    
    /**
     * Sets the pull socket.
     *
     * @param socket the new pull socket
     */
    public void setPullSocket(Socket socket){pullSocket = socket;}
    
    /**
     * Gets the pull socket.
     *
     * @return the pull socket
     */
    public Socket getPullSocket(){return pullSocket;}
    
    /**
     * Sets the push print writer.
     *
     * @param pw the new push print writer
     */
    public void setPushPrintWriter(PrintWriter pw){pushPrintWriter = pw;}
    
    /**
     * Gets the push print writer.
     *
     * @return the push print writer
     */
    public PrintWriter getPushPrintWriter(){return pushPrintWriter;}
    
    /**
     * Sets the task thread.
     *
     * @param tt the new task thread
     */
    public void setTaskThread(TaskThread tt){
        taskThread = tt;
        disconnectedCount = 0;
    }
    
    /**
     * Gets the task thread.
     *
     * @return the task thread
     */
    public TaskThread getTaskThread(){return taskThread;}
    
    /**
     * Open custom.
     *
     * @param questionNumber the question number
     * @param flags the flags
     * @param parameters the parameters
     * @param color the color
     */
    protected void openCustom(String questionNumber, String flags, String parameters, String color){
        Intent intent = new Intent(this, CustomizableQuestion.class);
        intent.putExtra(QUESTION_NUMBER, questionNumber);
        intent.putExtra(FLAGS, flags);
        intent.putExtra(VALUES, parameters);
        intent.putExtra(COLOR, color);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);	
    }
    
    /**
     * Open mouse control.
     *
     * @param questionNumber the question number
     * @param flags the flags
     * @param parameters the parameters
     * @param color the color
     */
    protected void openMouseControl(String questionNumber, String flags, String parameters, String color){
        Intent intent = new Intent(this, ClickPadActivity.class);
        intent.putExtra(QUESTION_NUMBER, questionNumber);
        intent.putExtra(FLAGS, flags);
        intent.putExtra(VALUES, parameters);
        intent.putExtra(COLOR, color);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);	
    }
    
    /**
     * Cancel timer.
     */
    public void cancelTimer(){
        if(isQuestionInPushMode()){
            try{
                taskThread.cancelTimer();
            } catch (NullPointerException e){
                
            }
        }
    }
    
    /**
     * Run task.
     *
     * @param inr the inr
     */
    public void runTask(BufferedReader inr) {
        try {
            if(heartbeatCurrentCount == HEARTBEAT_COUNT){
                if(waitingForHeartbeatToReturn){
                    Toast.makeText(this, "Connection to server lost.", Toast.LENGTH_SHORT).show();
                    Log.d(ClickerConstants.TAG,"connection lost due to heartbeat");
                    gotDisconnected();
                }
                pushPrintWriter.println(ClickerConstants.STILL_CONNECTED_REQUEST);
                pushPrintWriter.flush();
                //Log.d(ClickerConstants.TAG,"sent heartbeat");
                heartbeatCurrentCount = 0;
                waitingForHeartbeatToReturn = true;
            }
            heartbeatCurrentCount++;
            //Log.d(ClickerConstants.TAG,"heartbeatcount: "+heartbeatCurrentCount);
            String str = inr.readLine();
            String col = "";
            if(!currentlyDisconnected){
                if (str == null) {
                    gotDisconnected();
                }
                String[] input = str.split(SEMI_COLON_SEPARATOR);
                Log.d(ClickerConstants.TAG, "runTask received message: "+str);
                
                if(input.length >= 1){
                    if(input[0].equals(ClickerConstants.OPEN_COMMAND)){
                        if(input.length<5){
                            col=DEFAULT;
                        }
                        else {
                            col = input[4];
                        }
                        if(questionActive){
                            subHandler.sendEmptyMessage(ClickerConstants.CLOSE_DIALOG);
                        }
                        //Log.d(ClickerConstants.TAG, "Open requested with: "+str);
                        openCustom(input[1], input[2], input[3], col);
                    } else if(input[0].equals(ClickerConstants.OPEN_CLICKPAD)){
                        if(input.length<5){
                            col=DEFAULT;
                        }
                        else {
                            col = input[4];
                        }
                        if(questionActive){
                            subHandler.sendEmptyMessage(ClickerConstants.CLOSE_DIALOG);
                        }
                        Log.d(ClickerConstants.TAG, "Mouse open requested with: "+str);
                        openMouseControl(input[1], input[2], input[3], col);
                    }
                    else if (input[0].equals(ClickerConstants.CLOSE_COMMAND)){
                        subHandler.sendEmptyMessage(ClickerConstants.CLOSE_DIALOG);
                        //Toast.makeText(this, "received close", Toast.LENGTH_SHORT).show();
                    }
                    else if (input[0].equals(ClickerConstants.SYSTEM_COMMAND)){
                        if(input[1].equals(ClickerConstants.REMOVE_OPTION)){
                            Message mess = Message.obtain(subHandler, 
                                    ClickerConstants.REMOVE_MC_OPTION, 
                                    Integer.parseInt(input[2]), 0); //last 0 to properly
                            //create the message type
                            subHandler.sendMessage(mess);
                        } else if (input[1].equals(ClickerConstants.SET_COLOR)){
                            setDefaultColor(input[2]);
                        } else if (input[1].equals(ClickerConstants.SET_GROUP)){
                            setGroup(input[2]);
                            
                            subHandler.sendEmptyMessage(ClickerConstants.REDRAW);
                            
                        }
                    }
                    else if (input[0].equals(ClickerConstants.INVALID_RESPONSE)){
                        Toast.makeText(this, 
                                "The administrator received an invalid response that it could not process.", 
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (input[0].equals(ClickerConstants.INVALID_ADMIN)){
                        Toast.makeText(this, "You attempted to connect to an invalid Administrator name: "+adminName+"."
                                +"\nThe connection was refused.", Toast.LENGTH_SHORT).show();
                        closeConnections();
                    }
                    else if (input[0].equals(ClickerConstants.INVALID_INFORMATION)){
                        Toast.makeText(getClickerClient(), "The admin received an invalid response that it could not process.", Toast.LENGTH_SHORT).show();
                        
                    }
                    else if (input[0].equals(ClickerConstants.STILL_CONNECTED_RESPONSE)){
                        //Log.d(ClickerConstants.TAG, "Heartbeat response received");
                        waitingForHeartbeatToReturn = false;
                    }
                } else {
                    Toast.makeText(this, "Client received an invalid command that it could not process:\n"+str, Toast.LENGTH_SHORT).show();
                }
            } else {
                //Log.d(ClickerConstants.TAG, "Currently disconnected and trying to runtask...");
            }
        } catch (SocketTimeoutException e) {
            //do nothing
        } catch (IOException e) {
            Log.d(ClickerConstants.TAG,"runtask() IO Exception");
            //e.printStackTrace();
            gotDisconnected();
        } catch (Exception e) {
            Log.d(ClickerConstants.TAG, "runtask() listen exception: "+ e.getMessage());
            //e.printStackTrace();
            gotDisconnected();
        }
        
    }
    
    /**
     * Got disconnected.
     */
    private void gotDisconnected(){
        if(disconnectedCount == 0){
            disconnectedCount++;
            //Toast.makeText(currentActivity, "Attempting to reconnect...", Toast.LENGTH_SHORT).show();
            cancelTimer();
            dialog = ProgressDialog.show(currentActivity, "", "Connection interrupted... Reconnecting.", true);
            if(reconnectionThread != null){
                reconnectionThread.stop();
            }
            reconnectionThread = new Thread(new ReconnectionThread());
            reconnectionThread.start();
        }
    }
    
    /**
     * The Class ReconnectionThread.
     */
    private class ReconnectionThread implements Runnable{
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            //Log.d(ClickerConstants.TAG, "reconnection thread created at: "+System.currentTimeMillis());
            int currentReconnectAttempts = 0;
            boolean reconnected = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) { 
                //e1.printStackTrace();
            }
            while(currentReconnectAttempts < reconnectAttempts){
                //Log.d("SOCKET", "attempting to reconnect");
                try {
                    Socket s = new Socket();
                    InetSocketAddress isa = new InetSocketAddress(pushIp, pushPort);
                    s.connect(isa, 5000);
                    setPushSocket( s );
                    getPushSocket().setSoTimeout(50);
                    getPushSocket().setKeepAlive(true);
                    
                    OutputStream out = getPushSocket().getOutputStream();
                    PrintWriter outw = new PrintWriter(out, true);
                    setPushPrintWriter( outw );
                    InputStream in = getPushSocket().getInputStream();
                    BufferedReader inr = new BufferedReader(new InputStreamReader(in));
                    
                    outw.println(getConnectString());
                    outw.flush();
                    setTaskThread( new TaskThread(getClickerClient(), getHandler(), inr) );
                    currentReconnectAttempts = reconnectAttempts+1;
                    reconnected = true;
                    resetHeartbeatWaiting();
                    currentlyDisconnected = false;
                    disconnectedCount = 0;
                } catch (IOException e) {
                    //e.printStackTrace();
                    currentReconnectAttempts++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e1) {
                        //e1.printStackTrace();
                    }
                    continue;
                }
            }
            
            mDialogHandler.sendEmptyMessage(DISMISS_DIALOG);
            if(!reconnected){
                closeConnections();
            }
            //Log.d(ClickerConstants.TAG, "ending run of reconnection thread, reconnected: "+reconnected);
        }}
    
    /**
     * Close connections.
     */
    private void closeConnections(){
        disconnectServer();
        subHandler.sendEmptyMessage(ClickerConstants.CLOSE_PUSH);
    }
    
    /**
     * Reset heartbeat waiting.
     */
    public void resetHeartbeatWaiting(){
        waitingForHeartbeatToReturn = false;
    }
    
    /**
     * Disconnect server.
     */
    protected void disconnectServer() {
        try {
            cancelTimer();
            closeQuestion();
            getPushSocket().close();
            setQuestionInPushMode(false);
            setActiveSocket(new Socket());
            currentlyDisconnected = true;
        } catch (IOException e) {
            Log.d(ClickerConstants.TAG, "error closing socket: " + e.getMessage());
        }
    }
    
    /**
     * Creates the handler.
     */
    private void createHandler(){
        mDialogHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                Message mess;
                switch (msg.what) {
                    case ClickerConstants.CLOSE_DIALOG: //handler, what, arg1, arg2
                        //mess = Message.obtain(subHandler, ClickerConstants.CLOSE_DIALOG, msg.arg1, msg.arg2);
                        subHandler.sendEmptyMessage(ClickerConstants.CLOSE_DIALOG);
                        break;
                    case ClickerConstants.REMOVE_MC_OPTION: //handler, what, arg1, arg2
                        mess = Message.obtain(subHandler, ClickerConstants.REMOVE_MC_OPTION, msg.arg1, msg.arg2);
                        subHandler.sendMessage(mess);
                        break;
                    case ClickerConstants.RESEND_RESPONSE:
                        break;
                    case ClickerConstants.CONNECT_CHECK:
                        getPushPrintWriter().println(CONNECTED);
                        break;
                    case ClickerConstants.CLOSE_CONNECTION:
                        disconnectServer();
                        break;
                        //				case ClickerConstants.CLICKPAD_DIALOG_OK:
                        //					clickerClient.openClickPad(msg.arg1, msg.arg2);
                        //					break;
                    case ClickerConstants.INVALID:
                        Toast.makeText(getClickerClient(), "The admin received an invalid response that it could not process.", Toast.LENGTH_SHORT).show();
                        break;
                    case DISMISS_DIALOG:
                        dialog.dismiss();
                        break;
                    case ClickerConstants.DUP_ID:
                        Toast.makeText(
                                getClickerClient(),
                                "Duplicate ID detected.\nEnter a different name through the menu option to connect.",
                                Toast.LENGTH_SHORT).show();
                }
            };
        };
    }
    
    /**
     * Sets the connection info.
     *
     * @param ip the ip
     * @param port the port
     */
    public void setConnectionInfo(InetAddress ip, int port) {
        pushIp = ip;
        pushPort = port;
        
    }
}

