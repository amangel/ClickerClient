package com.clicker.client;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.clicker.client.widgets.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CustomizableQuestion extends AbstractQuestionActivity {

	protected final static String 	TAG 	= "SOCKET";
	
	//tags for widget creation
	protected final static String 	BUTTON 	  = "B";
	protected final static String 	SLIDER 	  = "SLIDE";
	protected final static String 	TOGGLE 	  = "TOG";
	protected final static String 	JEO 	  = "JEO";
	protected final static String 	COMBO 	  = "COMBO";
	protected final static String	STARS	  = "STARS";
	protected final static String	TEXTBOX   = "TEXTBOX";
	protected final static String 	TEXTQ	  = "TEXTQ";
	protected final static String	TEXTVIEW  = "TEXTVIEW";
	protected final static String   TVBUTTON  = "TVBUTTON";
	protected final static String   QRTEXT    = "QRTEXT";
	protected final static String   IMAGE     = "IMAGE";
	protected final static String   BUTTONSET = "BSET";
	
	//flag statics
	protected final static String MANUAL_SUBMIT_FLAG = "P";
	protected final static String VISIBLE_WIDGET_FLAG = "v";
	protected final static String EVERYONE_GROUP_FLAG = "e";

	protected final static String EVERYONE_GROUP_STRING = "Everyone";
	
	
	protected final static String SEMI_COLON_SEPARATOR = "`/;";
	protected final static String COMMA_SEPARATOR      = "`/,";
	protected final static String COLON_SEPARATOR      = "`/:";
	protected final static String TILDE_SEPARATOR      = "`/~";
	protected final static String CARET_SEPARATOR      = "`/^";
	
	
	protected static final int 		NO_ANSWER 	= -1;
	protected static final int   QR_TEXT_SCAN   = 17;
	
	protected static final int SEEKBAR_CHANGED = 2010;

	protected ClickerClient clickerClient;
	protected ClickerClientApp app;
	protected LinearLayout layout;
	protected String[] parameters;// list of all the widgets to make
	protected LayoutInflater inflater;
	protected InputMethodManager inputMgr;

	protected ArrayList<QuestionWidgetInterface> valueCarryingWidgets;
	protected ArrayList<LinearLayout>			 viewLayouts;
	protected ArrayList<QuestionWidgetInterface> discreteButtons;
	
	protected QuestionTextBox 	qrScanEntry;

	//received from other intent
	protected String values;
	protected String questionNumber;
	protected String color;
	protected String flags;
	
	protected int colorCode;

	protected boolean isInPushMode;
	protected boolean manualSubmit;
	protected Handler activityHandler;
	//protected Bundle b;
	protected Timer t;
	protected int comboBoxItemChange; //to track when a combo box has an item selected
	//android 'feature' has the combo box select an item on creation, which submits
	//its value otherwise
	
	protected ViewGroup.LayoutParams layoutParamsFillParentWrapContent;
	
	//listeners
	protected View.OnClickListener buttonOnClickListener;
	protected View.OnClickListener toggleButtonOnClickListener;
	protected View.OnClickListener jeoButtonOnClickListener;
	protected View.OnClickListener textBoxClickListener;
	protected RatingBar.OnRatingBarChangeListener ratingBarChangeListener;
	protected OnSeekBarChangeListener seekbarOnChangeListener;
	protected OnItemSelectedListener comboItemSelectedListener;
	protected TextWatcher textBoxTextWatcher;
	protected NumberKeyListener keyListener;

	private boolean everyoneGroupFlagSet;

	
	public CustomizableQuestion() {

		valueCarryingWidgets = new ArrayList<QuestionWidgetInterface>();
		discreteButtons = new ArrayList<QuestionWidgetInterface>();
		viewLayouts = new ArrayList<LinearLayout>();
		t = new Timer();
		comboBoxItemChange = 0;
		
	}
	
	protected void processFlags(){
		String[] flagTokens = flags.split(COMMA_SEPARATOR);
		for (String token : flagTokens){
			if (token.equals(MANUAL_SUBMIT_FLAG)){
				if (isInPushMode){
					isInPushMode = false;
					manualSubmit = true;
				}
			} else if(token.equals(EVERYONE_GROUP_FLAG)){
				everyoneGroupFlagSet = true;
			}
		}
	}
	
	protected void processValues() {
		parameters = values.split(COMMA_SEPARATOR);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ClickerClientApp) getApplication();
		clickerClient = app.getClickerClient();

		isInPushMode = app.isQuestionInPushMode();
		manualSubmit = false;
		everyoneGroupFlagSet = false;
		
		Bundle b = this.getIntent().getExtras();
		values = b.getString("values");
		questionNumber = b.getString("questionNumber");
		flags = b.getString("flags");
		color = b.getString("color");

		processFlags();
		processValues();
		

		inflater = (LayoutInflater) clickerClient.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutParamsFillParentWrapContent = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	
		setColor();
		setView();
		createListeners();
		createViews();
		setHandler();
		
		
	}

	protected void setColor(){
		if (color.equals("default")){
			colorCode = Color.parseColor(app.getDefaultColor());
		} else {
			try{
				colorCode = Color.parseColor(color);
			} catch(Exception e){
				Log.d(TAG,"Error parsing color: "+e.getMessage());
				Log.d(TAG,"Color given: "+color);
				colorCode = Color.parseColor(app.getDefaultColor());
			}
		}
	}
	
	protected void setView() {
		setContentView(R.layout.question_layout);
		layout = (LinearLayout) findViewById(R.id.question_linearlayout);
		ScrollView sv = (ScrollView) findViewById(R.id.question_scrollView);
		sv.setBackgroundColor(colorCode);
	}

	protected void setHandler() {
		activityHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case ClickerConstants.CLOSE_DIALOG:
					//Log.d(TAG,"recieved close inside customizablequestion: "+msg.getWhen());
					closeActivity();
					break;
				case ClickerConstants.REMOVE_MC_OPTION:
					removeView(msg.arg1);
					break;
				case SEEKBAR_CHANGED:
					QuestionSlider qs = (QuestionSlider)findViewById(msg.arg1);
					TextView tv = (TextView)findViewById(msg.arg1-25);
					tv.setText(qs.getWidgetValue());
				}
			}
		};
	}

	protected void createListeners(){
		buttonOnClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				QuestionButton b = (QuestionButton) v;
				if (b.isChecked()) {
			        b.getBackground().setColorFilter(0xFFFF4444,	
			                 PorterDuff.Mode.MULTIPLY);
					for (QuestionWidgetInterface widget : discreteButtons) {
						if (!widget.equals(b)) {
							widget.turnOff();
				             widget.getBackground().setColorFilter(null);
				             widget.invalidate();
						}
					}
				} else {
					b.getBackground().setColorFilter(null);
				}
				sendValues();
			}
		};
		
		toggleButtonOnClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				sendValues();
			}
		};
		
		textBoxClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				inputMgr.toggleSoftInput(0, 0);
			}
		};
		
		ratingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				sendValues();
			}
		};
		
		jeoButtonOnClickListener = new View.OnClickListener() {
			public void onClick(View v) {
				QuestionButton b = (QuestionButton) v;
				if (b.isChecked()) {
					b.toggleWidget();
					b.setVisibility(View.INVISIBLE);
					sendValues();
				}
			}
		};
		
		seekbarOnChangeListener = new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Message msg = Message.obtain(activityHandler, 
						SEEKBAR_CHANGED, seekBar.getId(), progress);
				activityHandler.sendMessage(msg);
			}
			public void onStartTrackingTouch(SeekBar arg0) {}
			
			public void onStopTrackingTouch(SeekBar arg0) {
				sendValues();
			}};
		
		comboItemSelectedListener = new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(comboBoxItemChange>0){
					sendValues();
				} else {
					comboBoxItemChange++;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		};
		
		keyListener = new NumberKeyListener(){
			   @Override
			   protected char[] getAcceptedChars() {
			      char[] numberChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			    		  'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			    		  't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4',
			    		  '5', '6', '7', '8', '9', '0', '!', '@', '#', '$', '%',
			    		  '^', '&', '*', '(', ')', '-', '_', '=', '+', '`', '~',
			    		  '<', '.', '>', '/', '?', ':', '\'', '"', '\\', '|', '\n',
			    		  '\b', '\t', ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			    		  'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			    		  'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
			      return numberChars;
			   }

			@Override
			public int getInputType() {
				return 0;
			}
			};
		
		textBoxTextWatcher = new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//Log.d(TAG,"on text changed called...");
				t.cancel();
				t.purge();
				t = new Timer();
				t.schedule(new TimerTask() {
					public void run() {
						activityHandler.post(new Runnable() {
							public void run() {
								sendValues();
							}
						});
					}
				}, 2500);
			}
		};
		//more listeners here
	}
	
	protected LinearLayout createLinearLayout(){
		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		return ll;
	}
	
	protected TextView createTextView(String label){
		TextView labelTV = new TextView(this);
		labelTV.setText(label);
		labelTV.setLayoutParams(layoutParamsFillParentWrapContent);
		return labelTV;
	}
	
	protected QuestionButton createQuestionButton(String text, 
			String defaultValue, int i){
		QuestionButton b = new QuestionButton(this);
		b.setLayoutParams(layoutParamsFillParentWrapContent);
		b.setText(text);
		b.setWidgetValue(defaultValue);
		b.setBackgroundResource(android.R.drawable.btn_default);
		b.setOnClickListener(buttonOnClickListener);
		b.setId(i);//id modified by questionbutton class
		return b;
	}
	
	protected QuestionSlider createQuestionSlider(int min, int max, 
			String defaultValue, int id){
		QuestionSlider qs = new QuestionSlider(this, min, max);//max
		qs.setWidgetValue(defaultValue);
		qs.setLayoutParams(layoutParamsFillParentWrapContent);
		qs.setOnSeekBarChangeListener(seekbarOnChangeListener);
		qs.setId(id);
		return qs;
	}
	protected void createViews() {
		for (int i = 0; i < parameters.length; i++) {
			String[] widgetValues = parameters[i].split(COLON_SEPARATOR);
			//Log.d(TAG,"Trying: "+parameters[i]);

			// create a linear layout for the widget to reside in
			LinearLayout ll = createLinearLayout();
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setPadding(0, 2, 0, 2);
			ll.setId(i + 50);

			// switch based on the type of widget described
			if (widgetValues[0].equals(BUTTON)) {
				QuestionButton b = createQuestionButton(
						widgetValues[1], widgetValues[2], i*171);
				discreteButtons.add(b);
				valueCarryingWidgets.add(b);
				
				ll.addView(b);
				viewLayouts.add(ll);
				layout.addView(ll);
			} //end of BUTTON
			else if (widgetValues[0].equals(SLIDER) && widgetValues.length >= 5){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);
				
				QuestionSlider qs = createQuestionSlider( 
						Integer.parseInt(widgetValues[2]), //min
						Integer.parseInt(widgetValues[3]), //max
						widgetValues[4], i*271);// default/id
				valueCarryingWidgets.add(qs);
				ll.addView(qs);
				
				TextView valueTV = createTextView(widgetValues[4]);
				valueTV.setId(qs.getId()-25);
				ll.addView(valueTV);
				
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of SLIDER
			else if (widgetValues[0].equals(TOGGLE) && widgetValues.length >= 3){
				QuestionToggleButton b = new QuestionToggleButton(this);
				b.setLayoutParams(layoutParamsFillParentWrapContent);
				b.setText(widgetValues[1]);
				b.setWidgetValue(widgetValues[2]);
				valueCarryingWidgets.add(b);
				b.setOnClickListener(toggleButtonOnClickListener);
				b.setId(i*371);//id modified by questionbutton class
				
				ll.addView(b);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of TOGGLE
			else if (widgetValues[0].equals(JEO) && widgetValues.length >= 2){
				QuestionJeoButton b = new QuestionJeoButton(this);
				b.setLayoutParams(layoutParamsFillParentWrapContent);
				b.setText(widgetValues[1]);
				b.setBackgroundResource(android.R.drawable.btn_default);
				valueCarryingWidgets.add(b);
				b.setOnClickListener(jeoButtonOnClickListener);
				b.setId(i*471);//id modified by questionbutton class
				
				ll.addView(b);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of JEO
			else if (widgetValues[0].equals(COMBO) && widgetValues.length >= 3){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);
				
				QuestionComboBox qcb = new QuestionComboBox(this);
				qcb.setLayoutParams(layoutParamsFillParentWrapContent);
				String[] options = widgetValues[2].split(TILDE_SEPARATOR);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, options);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				qcb.setAdapter(adapter);
				qcb.setId(i*571);
				valueCarryingWidgets.add(qcb);
				qcb.setOnItemSelectedListener(comboItemSelectedListener);
				
				ll.addView(qcb);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of COMBO
			else if (widgetValues[0].equals(TEXTBOX) && widgetValues.length >= 2){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);
				
				QuestionTextBox qtb = new QuestionTextBox(this);
				qtb.setLayoutParams(layoutParamsFillParentWrapContent);
				String setText = "";
				if(widgetValues.length > 2){
					setText = widgetValues[2];
				}
				qtb.setText(setText);
				qtb.addTextChangedListener(textBoxTextWatcher);
				qtb.setId(i*671);
				qtb.setKeyListener(keyListener);
				qtb.setOnClickListener(textBoxClickListener);
				valueCarryingWidgets.add(qtb);
				//listener
				
				ll.addView(qtb);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of TEXTBOX
			else if (widgetValues[0].equals(TEXTQ) && widgetValues.length >= 3){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);			
				
				QuestionTextBox qtb = new QuestionTextBox(this);
				qtb.setLayoutParams(layoutParamsFillParentWrapContent);
				qtb.setText(widgetValues[2]);
				//qtb.addTextChangedListener(textBoxTextWatcher);
				qtb.setId(i*771);
				valueCarryingWidgets.add(qtb);
				ll.addView(qtb);
				
				Button b = new Button(this);
				b.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				b.setText("Submit");
				b.setBackgroundResource(android.R.drawable.btn_default_small);
				b.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						sendValues();
					}});
				ll.addView(b);
				
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of TEXTQ
			else if (widgetValues[0].equals(TEXTVIEW) && widgetValues.length >= 2){
				QuestionTextView qtv = new QuestionTextView(this);
				qtv.setId(i*871);
				qtv.setText(widgetValues[1]);
				//valueCarryingWidgets.add(qtv);
				ll.addView(qtv);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end of TEXTVIEW
			else if (widgetValues[0].equals(TVBUTTON) && widgetValues.length >= 2){
				QuestionTextView qtv = new QuestionTextView(this);
				qtv.setId(i*971);
				qtv.setText(widgetValues[1]);
				//valueCarryingWidgets.add(qtv);
				ll.addView(qtv);
				
				Button b = new Button(this);
				b.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				b.setText("Continue");
				b.setBackgroundResource(android.R.drawable.btn_default_small);
				b.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if(isInPushMode || manualSubmit){
							//Log.d(TAG,"push mode/manual submit");
							isInPushMode = true;
							sendValues();
							isInPushMode = false;
						} else {
							Log.d(TAG, "pull mode submit");
							pullModeSendValues();
						}
					}});
				ll.addView(b);				
				
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end TVBUTTON
			else if (widgetValues[0].equals(STARS) && widgetValues.length >= 5){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);
				
				QuestionStars qs = new QuestionStars(this, widgetValues[2], //(String)int max
						widgetValues[3], widgetValues[4]);	//(Str)int starCount,(Str)float stepSize
				qs.setId(i*1071);
				qs.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
				
				qs.setOnRatingBarChangeListener(ratingBarChangeListener);
				
				ll.addView(qs);
				valueCarryingWidgets.add(qs);
				viewLayouts.add(ll);
				layout.addView(ll);
			}// end STARS
			else if (widgetValues[0].equals(QRTEXT)){
				TextView labelTV = createTextView(widgetValues[1]);
				ll.addView(labelTV);
				
				QuestionTextBox qtb = new QuestionTextBox(this);
				qtb.setLayoutParams(layoutParamsFillParentWrapContent);
				qtb.setText(widgetValues[2]);
				qtb.addTextChangedListener(textBoxTextWatcher);
				qtb.setId(i*1171);
				qtb.setKeyListener(keyListener);
				qtb.setOnClickListener(textBoxClickListener);
				valueCarryingWidgets.add(qtb);
				ll.addView(qtb);
				qrScanEntry = qtb;
				
				Button b = new Button(this);
				b.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				b.setText("Scan");
				b.setBackgroundResource(android.R.drawable.btn_default_small);
				b.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								"com.google.zxing.client.android.SCAN");
						intent.putExtra(
								"com.google.zxing.client.android.SCAN.SCAN_MODE",
								"QR_CODE_MODE");
						startActivityForResult(intent, QR_TEXT_SCAN);
					}});
				ll.addView(b);	
				viewLayouts.add(ll);
				layout.addView(ll);
				
				
			}//end QRTEXT
			else if(widgetValues[0].equals(IMAGE)){
				//TODO: add in imageview
				/*Drawable d = null;
				try {
					d = drawable_from_url("http://cdn1.staztic.com/logos/red-kitten-wallpaper-10.png", "");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//bExit.setBackgroundDrawable(d);*/
			}//end IMAGE
			else { //final else, to add a notification that the parameter was not accepted
				Toast.makeText(this, "Invalid widget declaration, object not added", Toast.LENGTH_LONG).show();
				Log.d(TAG,"invalid widget sent. command: "+parameters[i]);
			}
		}// end of parameter parsing

		
	}

	private Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
	    return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == QR_TEXT_SCAN) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
				qrScanEntry.setText(contents);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Toast.makeText(this, "Scan failed. Action cancelled.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.submit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.submit_menu:
			if(isInPushMode || manualSubmit){
				boolean temp = isInPushMode;
				isInPushMode = true;
				sendValues();
				isInPushMode = temp;
			} else {
				pullModeSendValues();
			}
			return true;
		}
		return false;
	}
	
	protected void pullModeSendValues(){
		app.connectToPullServer();
		PrintWriter pw = app.getPullServerPrintWriter();
		
		Log.d(TAG,app.getUserName() + SEMI_COLON_SEPARATOR + app.getMACAddress() +
				SEMI_COLON_SEPARATOR + app.getAdminName() + SEMI_COLON_SEPARATOR 
				+ questionNumber + SEMI_COLON_SEPARATOR + getValues());
		
		pw.println(app.getUserName() + SEMI_COLON_SEPARATOR + app.getMACAddress() +
				SEMI_COLON_SEPARATOR + app.getAdminName() + SEMI_COLON_SEPARATOR
				+ questionNumber + SEMI_COLON_SEPARATOR + getValues());
		pw.flush();
		app.runTask(app.getPullServerBufferedReader());
	}
	
	protected void sendValues(){
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
	
	public void removeView(int i) {
		if (i < viewLayouts.size()) {
			viewLayouts.get(i).setVisibility(View.INVISIBLE);
		}
	}

	public void closeActivity() {
		//sendValues();
		finish();
	}

	public void resendLast() {
		sendValues();
	}

	@Override
	public void onResume() {
		super.onResume();
		app.setSubHandler(activityHandler);
		app.setQuestionActive(true);
		app.setCurrentActivity(this);
	}

	private String getValues() {
		String str = "";
		if(valueCarryingWidgets.size() > 0){
			for (QuestionWidgetInterface widget : valueCarryingWidgets) {
				str += widget.getWidgetValue() + COMMA_SEPARATOR;
			}
		} else { //if there are no widgets with values to retrieve
			str += " ";
		}
		int length = 0;
		if (str.length() > 1){
			length = str.length() - 3;
		} else {
			length = 1;
		}
		//Log.d(TAG, "getValues: "+str.substring(0, length));
		return str.substring(0, length);
	}
}