package com.clicker.client.widgets;

import android.content.Context;

import com.clicker.client.ClickerClientApp;
import com.clicker.client.CustomizableQuestion;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionJeoButton.
 */
public class QuestionJeoButton extends QuestionButton {
    
    /** The app. */
    private ClickerClientApp app;
    
    /**
     * Instantiates a new question jeo button.
     *
     * @param context the context
     */
    public QuestionJeoButton(Context context){
        super(context);
        CustomizableQuestion cq = (CustomizableQuestion)context;
        app = (ClickerClientApp)cq.getApplication();
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionButton#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        return app.getUserName();
    }
}
