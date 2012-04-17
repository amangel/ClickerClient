package com.clicker.client;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.clicker.client.widgets.QuestionButton;
import com.clicker.client.widgets.QuestionComboBox;
import com.clicker.client.widgets.QuestionJeoButton;
import com.clicker.client.widgets.QuestionSlider;
import com.clicker.client.widgets.QuestionStars;
import com.clicker.client.widgets.QuestionTextBox;
import com.clicker.client.widgets.QuestionTextView;
import com.clicker.client.widgets.QuestionToggleButton;
import com.clicker.client.widgets.QuestionWidgetInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomizableQuestion.
 */
public class CustomizableQuestion extends AbstractQuestionActivity {
    
    private static final String SCAN = "Scan";

    private static final String CONTINUE = "Continue";

    private static final String SUBMIT = "Submit";

    private static final int COLOR_FILTER_FOR_SELECTED_BUTTON = 0xFFFF4444;

    private static final String DEFAULT = "default";

    private static final String QUESTION_NUMBER = "questionNumber";

    private static final String COLOR = "color";

    private static final String FLAGS = "flags";

    private static final String VALUES = "values";

    /** The Constant TAG. */
    protected final static String 	TAG 	= "SOCKET";
    
    //tags for widget creation
    /** The Constant BUTTON. */
    protected final static String 	BUTTON 	  = "B";
    
    /** The Constant SLIDER. */
    protected final static String 	SLIDER 	  = "SLIDE";
    
    /** The Constant TOGGLE. */
    protected final static String 	TOGGLE 	  = "TOG";
    
    /** The Constant JEO. */
    protected final static String 	JEO 	  = "JEO";
    
    /** The Constant COMBO. */
    protected final static String 	COMBO 	  = "COMBO";
    
    /** The Constant STARS. */
    protected final static String	STARS	  = "STARS";
    
    /** The Constant TEXTBOX. */
    protected final static String	TEXTBOX   = "TEXTBOX";
    
    /** The Constant TEXTQ. */
    protected final static String 	TEXTQ	  = "TEXTQ";
    
    /** The Constant TEXTVIEW. */
    protected final static String	TEXTVIEW  = "TEXTVIEW";
    
    /** The Constant TVBUTTON. */
    protected final static String   TVBUTTON  = "TVBUTTON";
    
    /** The Constant QRTEXT. */
    protected final static String   QRTEXT    = "QRTEXT";
    
    /** The Constant IMAGE. */
    protected final static String   IMAGE     = "IMAGE";
    
    /** The Constant BUTTONSET. */
    protected final static String   BUTTONSET = "BSET";
    
    //flag statics
    /** The Constant MANUAL_SUBMIT_FLAG. */
    protected final static String MANUAL_SUBMIT_FLAG = "P";
    
    /** The Constant VISIBLE_WIDGET_FLAG. */
    protected final static String VISIBLE_WIDGET_FLAG = "v";
    
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
    
    
    /** The Constant NO_ANSWER. */
    protected static final int 		NO_ANSWER 	= -1;
    
    /** The Constant QR_TEXT_SCAN. */
    protected static final int   QR_TEXT_SCAN   = 17;
    
    /** The Constant SEEKBAR_CHANGED. */
    protected static final int SEEKBAR_CHANGED = 2010;
    
    /** The clicker client. */
    protected ClickerClient clickerClient;
    
    /** The app. */
    protected ClickerClientApp app;
    
    /** The layout. */
    protected LinearLayout layout;
    
    /** The parameters. */
    protected String[] parameters;// list of all the widgets to make
    
    /** The inflater. */
    protected LayoutInflater inflater;
    
    /** The input mgr. */
    protected InputMethodManager inputMgr;
    
    /** The value carrying widgets. */
    protected ArrayList<QuestionWidgetInterface> valueCarryingWidgets;
    
    /** The view layouts. */
    protected ArrayList<LinearLayout>			 viewLayouts;
    
    /** The discrete buttons. */
    protected ArrayList<QuestionWidgetInterface> discreteButtons;
    
    /** The qr scan entry. */
    protected QuestionTextBox 	qrScanEntry;
    
    //received from other intent
    /** The values. */
    protected String values;
    
    /** The question number. */
    protected String questionNumber;
    
    /** The color. */
    protected String color;
    
    /** The flags. */
    protected String flags;
    
    /** The color code. */
    protected int colorCode;
    
    /** The is in push mode. */
    protected boolean isInPushMode;
    
    /** The manual submit. */
    protected boolean manualSubmit;
    
    /** The activity handler. */
    protected Handler activityHandler;
    //protected Bundle b;
    /** The t. */
    protected Timer t;
    
    /** The combo box item change. */
    protected int comboBoxItemChange; //to track when a combo box has an item selected
    //android 'feature' has the combo box select an item on creation, which submits
    //its value otherwise
    
    /** The layout params fill parent wrap content. */
    protected ViewGroup.LayoutParams layoutParamsFillParentWrapContent;
    
    //listeners
    /** The button on click listener. */
    protected View.OnClickListener buttonOnClickListener;
    
    /** The toggle button on click listener. */
    protected View.OnClickListener toggleButtonOnClickListener;
    
    /** The jeo button on click listener. */
    protected View.OnClickListener jeoButtonOnClickListener;
    
    /** The text box click listener. */
    protected View.OnClickListener textBoxClickListener;
    
    /** The rating bar change listener. */
    protected RatingBar.OnRatingBarChangeListener ratingBarChangeListener;
    
    /** The seekbar on change listener. */
    protected OnSeekBarChangeListener seekbarOnChangeListener;
    
    /** The combo item selected listener. */
    protected OnItemSelectedListener comboItemSelectedListener;
    
    /** The text box text watcher. */
    protected TextWatcher textBoxTextWatcher;
    
    /** The key listener. */
    protected NumberKeyListener keyListener;
    
    /** The everyone group flag set. */
    private boolean everyoneGroupFlagSet;
    
    
    /**
     * Instantiates a new customizable question.
     */
    public CustomizableQuestion() {
        
        valueCarryingWidgets = new ArrayList<QuestionWidgetInterface>();
        discreteButtons = new ArrayList<QuestionWidgetInterface>();
        viewLayouts = new ArrayList<LinearLayout>();
        t = new Timer();
        comboBoxItemChange = 0;
        
    }
    
    /**
     * Process flags.
     */
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
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#processValues()
     */
    protected void processValues() {
        parameters = values.split(COMMA_SEPARATOR);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ClickerClientApp) getApplication();
        clickerClient = app.getClickerClient();
        
        isInPushMode = app.isQuestionInPushMode();
        manualSubmit = false;
        everyoneGroupFlagSet = false;
        
        Bundle b = this.getIntent().getExtras();
        values = b.getString(VALUES);
        questionNumber = b.getString(QUESTION_NUMBER);
        flags = b.getString(FLAGS);
        color = b.getString(COLOR);
        
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
    
    /**
     * Sets the color.
     */
    protected void setColor(){
        if (color.equals(DEFAULT)){
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
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#setView()
     */
    protected void setView() {
        setContentView(R.layout.question_layout);
        layout = (LinearLayout) findViewById(R.id.question_linearlayout);
        ScrollView sv = (ScrollView) findViewById(R.id.question_scrollView);
        sv.setBackgroundColor(colorCode);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#setHandler()
     */
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
    
    /**
     * Creates the listeners.
     */
    protected void createListeners(){
        buttonOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                QuestionButton b = (QuestionButton) v;
                if (b.isChecked()) {
                    b.getBackground().setColorFilter(COLOR_FILTER_FOR_SELECTED_BUTTON,	
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
    
    /**
     * Creates the linear layout.
     *
     * @return the linear layout
     */
    protected LinearLayout createLinearLayout(){
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return ll;
    }
    
    /**
     * Creates the text view.
     *
     * @param label the label
     * @return the text view
     */
    protected TextView createTextView(String label){
        TextView labelTV = new TextView(this);
        labelTV.setText(label);
        labelTV.setLayoutParams(layoutParamsFillParentWrapContent);
        return labelTV;
    }
    
    /**
     * Creates the question button.
     *
     * @param text the text
     * @param defaultValue the default value
     * @param i the i
     * @return the question button
     */
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
    
    /**
     * Creates the question slider.
     *
     * @param min the min
     * @param max the max
     * @param defaultValue the default value
     * @param id the id
     * @return the question slider
     */
    protected QuestionSlider createQuestionSlider(int min, int max, 
            String defaultValue, int id){
        QuestionSlider qs = new QuestionSlider(this, min, max);//max
        qs.setWidgetValue(defaultValue);
        qs.setLayoutParams(layoutParamsFillParentWrapContent);
        qs.setOnSeekBarChangeListener(seekbarOnChangeListener);
        qs.setId(id);
        return qs;
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#createViews()
     */
    protected void createViews() {
        for (int i = 0; i < parameters.length; i++) {
            String[] widgetValues = parameters[i].split(COLON_SEPARATOR);
            //Log.d(TAG,"Trying: "+parameters[i]);
            
            // create a linear layout for the widget to reside in
            LinearLayout innerLayout = createLinearLayout();
            innerLayout.setOrientation(LinearLayout.VERTICAL);
            innerLayout.setPadding(0, 2, 0, 2);
            innerLayout.setId(i + 50);
            
            // switch based on the type of widget described
            if (widgetValues[0].equals(BUTTON)) {
                QuestionButton button = createQuestionButton(
                        widgetValues[1], widgetValues[2], i*171);
                discreteButtons.add(button);
                valueCarryingWidgets.add(button);
                
                innerLayout.addView(button);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            } //end of BUTTON
            else if (widgetValues[0].equals(SLIDER) && widgetValues.length >= 5){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);
                
                QuestionSlider slider = createQuestionSlider( 
                        Integer.parseInt(widgetValues[2]), //min
                        Integer.parseInt(widgetValues[3]), //max
                        widgetValues[4], i*271);// default/id
                valueCarryingWidgets.add(slider);
                innerLayout.addView(slider);
                
                TextView valueTV = createTextView(widgetValues[4]);
                valueTV.setId(slider.getId()-25);
                innerLayout.addView(valueTV);
                
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of SLIDER
            else if (widgetValues[0].equals(TOGGLE) && widgetValues.length >= 3){
                QuestionToggleButton button = new QuestionToggleButton(this);
                button.setLayoutParams(layoutParamsFillParentWrapContent);
                button.setText(widgetValues[1]);
                button.setWidgetValue(widgetValues[2]);
                valueCarryingWidgets.add(button);
                button.setOnClickListener(toggleButtonOnClickListener);
                button.setId(i*371);//id modified by questionbutton class
                
                innerLayout.addView(button);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of TOGGLE
            else if (widgetValues[0].equals(JEO) && widgetValues.length >= 2){
                QuestionJeoButton button = new QuestionJeoButton(this);
                button.setLayoutParams(layoutParamsFillParentWrapContent);
                button.setText(widgetValues[1]);
                button.setBackgroundResource(android.R.drawable.btn_default);
                valueCarryingWidgets.add(button);
                button.setOnClickListener(jeoButtonOnClickListener);
                button.setId(i*471);//id modified by questionbutton class
                
                innerLayout.addView(button);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of JEO
            else if (widgetValues[0].equals(COMBO) && widgetValues.length >= 3){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);
                
                QuestionComboBox comboBox = new QuestionComboBox(this);
                comboBox.setLayoutParams(layoutParamsFillParentWrapContent);
                String[] options = widgetValues[2].split(TILDE_SEPARATOR);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, options);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                comboBox.setAdapter(adapter);
                comboBox.setId(i*571);
                valueCarryingWidgets.add(comboBox);
                comboBox.setOnItemSelectedListener(comboItemSelectedListener);
                
                innerLayout.addView(comboBox);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of COMBO
            else if (widgetValues[0].equals(TEXTBOX) && widgetValues.length >= 2){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);
                
                QuestionTextBox textBox = new QuestionTextBox(this);
                textBox.setLayoutParams(layoutParamsFillParentWrapContent);
                String setText = "";
                if(widgetValues.length > 2){
                    setText = widgetValues[2];
                }
                textBox.setText(setText);
                textBox.addTextChangedListener(textBoxTextWatcher);
                textBox.setId(i*671);
                textBox.setKeyListener(keyListener);
                textBox.setOnClickListener(textBoxClickListener);
                valueCarryingWidgets.add(textBox);
                //listener
                
                innerLayout.addView(textBox);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of TEXTBOX
            else if (widgetValues[0].equals(TEXTQ) && widgetValues.length >= 3){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);			
                
                QuestionTextBox textBox = new QuestionTextBox(this);
                textBox.setLayoutParams(layoutParamsFillParentWrapContent);
                textBox.setText(widgetValues[2]);
                //qtb.addTextChangedListener(textBoxTextWatcher);
                textBox.setId(i*771);
                valueCarryingWidgets.add(textBox);
                innerLayout.addView(textBox);
                
                Button button = new Button(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setText(SUBMIT);
                button.setBackgroundResource(android.R.drawable.btn_default_small);
                button.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        sendValues();
                    }});
                innerLayout.addView(button);
                
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of TEXTQ
            else if (widgetValues[0].equals(TEXTVIEW) && widgetValues.length >= 2){
                QuestionTextView textView = new QuestionTextView(this);
                textView.setId(i*871);
                textView.setText(widgetValues[1]);
                //valueCarryingWidgets.add(qtv);
                innerLayout.addView(textView);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end of TEXTVIEW
            else if (widgetValues[0].equals(TVBUTTON) && widgetValues.length >= 2){
                QuestionTextView textView = new QuestionTextView(this);
                textView.setId(i*971);
                textView.setText(widgetValues[1]);
                //valueCarryingWidgets.add(qtv);
                innerLayout.addView(textView);
                
                Button button = new Button(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setText(CONTINUE);
                button.setBackgroundResource(android.R.drawable.btn_default_small);
                button.setOnClickListener(new OnClickListener(){
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
                innerLayout.addView(button);				
                
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end TVBUTTON
            else if (widgetValues[0].equals(STARS) && widgetValues.length >= 5){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);
                
                QuestionStars stars = new QuestionStars(this, widgetValues[2], //(String)int max
                        widgetValues[3], widgetValues[4]);	//(Str)int starCount,(Str)float stepSize
                stars.setId(i*1071);
                stars.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                
                stars.setOnRatingBarChangeListener(ratingBarChangeListener);
                
                innerLayout.addView(stars);
                valueCarryingWidgets.add(stars);
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
            }// end STARS
            else if (widgetValues[0].equals(QRTEXT)){
                TextView labelTV = createTextView(widgetValues[1]);
                innerLayout.addView(labelTV);
                
                QuestionTextBox textBox = new QuestionTextBox(this);
                textBox.setLayoutParams(layoutParamsFillParentWrapContent);
                textBox.setText(widgetValues[2]);
                textBox.addTextChangedListener(textBoxTextWatcher);
                textBox.setId(i*1171);
                textBox.setKeyListener(keyListener);
                textBox.setOnClickListener(textBoxClickListener);
                valueCarryingWidgets.add(textBox);
                innerLayout.addView(textBox);
                qrScanEntry = textBox;
                
                Button button = new Button(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setText(SCAN);
                button.setBackgroundResource(android.R.drawable.btn_default_small);
                button.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                "com.google.zxing.client.android.SCAN");
                        intent.putExtra(
                                "com.google.zxing.client.android.SCAN.SCAN_MODE",
                                "QR_CODE_MODE");
                        startActivityForResult(intent, QR_TEXT_SCAN);
                    }});
                innerLayout.addView(button);	
                viewLayouts.add(innerLayout);
                layout.addView(innerLayout);
                
                
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
    
//    /**
//     * Drawable_from_url.
//     *
//     * @param url the url
//     * @param src_name the src_name
//     * @return the drawable
//     * @throws MalformedURLException the malformed url exception
//     * @throws IOException Signals that an I/O exception has occurred.
//     */
//    private Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
//        return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
//    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
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
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.submit_menu, menu);
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
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
    
    /**
     * Pull mode send values.
     */
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
    
    /**
     * Send values.
     */
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
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#removeView(int)
     */
    public void removeView(int i) {
        if (i < viewLayouts.size()) {
            viewLayouts.get(i).setVisibility(View.INVISIBLE);
        }
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#closeActivity()
     */
    public void closeActivity() {
        //sendValues();
        finish();
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.AbstractQuestionActivity#resendLast()
     */
    public void resendLast() {
        sendValues();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        app.setSubHandler(activityHandler);
        app.setQuestionActive(true);
        app.setCurrentActivity(this);
    }
    
    /**
     * Gets the values.
     *
     * @return the values
     */
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