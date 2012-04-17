package com.clicker.client.widgets;

import android.content.Context;
import android.widget.Spinner;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionComboBox.
 */
public class QuestionComboBox extends Spinner implements QuestionWidgetInterface{
    
    /**
     * Instantiates a new question combo box.
     *
     * @param context the context
     */
    public QuestionComboBox(Context context){
        super(context);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        return (String)this.getSelectedItem();
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setWidgetValue(java.lang.String)
     */
    @Override
    public void setWidgetValue(String s) {
        this.setSelection(Integer.parseInt(s));
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
     * @see android.view.View#setId(int)
     */
    @Override
    public void setId(int id) {
        super.setId(id+150);
    }
    
    /* (non-Javadoc)
     * @see android.view.View#setVisibility(int)
     */
    @Override
    public void setVisibility(int vis) {
        // TODO Auto-generated method stub
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
    }
    
}
