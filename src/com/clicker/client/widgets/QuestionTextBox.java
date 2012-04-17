package com.clicker.client.widgets;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.clicker.client.CustomizableQuestion;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionTextBox.
 */
public class QuestionTextBox extends EditText implements QuestionWidgetInterface{
    
    /** The cq. */
    private CustomizableQuestion cq;
    
    /**
     * Instantiates a new question text box.
     *
     * @param context the context
     */
    public QuestionTextBox(Context context){
        super(context);
        cq = (CustomizableQuestion)context;
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        String str = this.getText().toString();
        if (str.equals("")){
            str = " ";
        }
        str = str.replace("\n", " ");
        return str;
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setWidgetValue(java.lang.String)
     */
    @Override
    public void setWidgetValue(String s) {
        this.setText(s, TextView.BufferType.EDITABLE);
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
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOff()
     */
    @Override
    public void turnOff() {
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOn()
     */
    @Override
    public void turnOn() {
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        setWidgetValue(text);
        
    }
    
    /* (non-Javadoc)
     * @see android.view.View#setId(int)
     */
    @Override
    public void setId(int id){
        super.setId(id+150);
    }
}
