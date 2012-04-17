package com.clicker.client.widgets;

import android.content.Context;
import android.widget.ToggleButton;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionButton.
 */
public class QuestionButton extends ToggleButton implements QuestionWidgetInterface {
    
    /**
     * Instantiates a new question button.
     *
     * @param context the context
     */
    public QuestionButton(Context context){
        super(context);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        if (this.isChecked()){
            return this.getText().toString();
        } else {
            return " ";
        }
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setWidgetValue(java.lang.String)
     */
    @Override
    public void setWidgetValue(String s) {
        if (s.equals("0")){
            this.setChecked(false);
        } else if (s.equals("1")){
            this.setChecked(true);
        }
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#isToggleable()
     */
    @Override
    public boolean isToggleable() {
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#toggleWidget()
     */
    @Override
    public void toggleWidget() {
        this.toggle();
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOff()
     */
    @Override
    public void turnOff() {
        this.setChecked(false);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setText(java.lang.String)
     */
    @Override
    public void setText(String str){
        super.setText(str);
        this.setTextOn(str);
        this.setTextOff(str);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOn()
     */
    @Override
    public void turnOn() {
        this.setChecked(true);
    }
    
    /* (non-Javadoc)
     * @see android.view.View#setId(int)
     */
    @Override
    public void setId(int id){
        super.setId(id+150);
    }
    
}
