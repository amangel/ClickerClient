package com.clicker.client.widgets;

import android.content.Context;
import android.widget.RatingBar;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionStars.
 */
public class QuestionStars extends RatingBar implements QuestionWidgetInterface  {
    
    /**
     * Instantiates a new question stars.
     *
     * @param context the context
     * @param max the max
     * @param starCount the star count
     * @param stepSize the step size
     */
    public QuestionStars(Context context, String max, String starCount, String stepSize){
        super(context);
        setMax(Integer.parseInt(max));
        setNumStars(Integer.parseInt(starCount));
        setStepSize(Float.parseFloat(stepSize));
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        return ""+getRating();
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
        setRating(Float.parseFloat(s));
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
        //do nothing
    }
}
