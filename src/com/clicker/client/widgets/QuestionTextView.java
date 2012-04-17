package com.clicker.client.widgets;

import android.content.Context;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionTextView.
 */
public class QuestionTextView extends TextView implements QuestionWidgetInterface {
    
    /**
     * Instantiates a new question text view.
     *
     * @param context the context
     */
    public QuestionTextView(Context context) {
        super(context);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        return " ";
    }
    
    /* (non-Javadoc)
     * @see android.view.View#setId(int)
     */
    @Override
    public void setId(int id){
        super.setId(id+150);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setWidgetValue(java.lang.String)
     */
    @Override
    public void setWidgetValue(String s) {
        //do nothing
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#isToggleable()
     */
    @Override
    public boolean isToggleable() {
        return false;
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#toggleWidget()
     */
    @Override
    public void toggleWidget() {
        //do nothing
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOff()
     */
    @Override
    public void turnOff() {
        //do nothing
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOn()
     */
    @Override
    public void turnOn() {
        //do nothing
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        super.setText(text);	
    }
    
}
