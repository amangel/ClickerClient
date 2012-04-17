package com.clicker.client.widgets;

import android.content.Context;
import android.widget.SeekBar;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionSlider.
 */
public class QuestionSlider extends SeekBar implements QuestionWidgetInterface {
    
    /** The min. */
    private int min;
    
    /** The max. */
    private int max;
    
    /**
     * Instantiates a new question slider.
     *
     * @param context the context
     * @param min the min
     * @param max the max
     */
    public QuestionSlider(Context context, int min, int max){
        super(context);
        this.min = min;
        this.max = max;
        this.setMax(max-min);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#getWidgetValue()
     */
    @Override
    public String getWidgetValue() {
        return ""+(this.getProgress()+min);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setWidgetValue(java.lang.String)
     */
    @Override
    public void setWidgetValue(String s) {
        int i = Integer.parseInt(s);
        int curr = this.getProgress();
        int diff = i - curr - min;
        this.incrementProgressBy(diff);
    }
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#isToggleable()
     */
    @Override
    public boolean isToggleable() {		return false;}
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#toggleWidget()
     */
    @Override
    public void toggleWidget() {		}
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOff()
     */
    @Override
    public void turnOff() {	}
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#turnOn()
     */
    @Override
    public void turnOn() {		}
    
    /* (non-Javadoc)
     * @see com.clicker.client.widgets.QuestionWidgetInterface#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {	}
    
    /* (non-Javadoc)
     * @see android.view.View#setId(int)
     */
    @Override
    public void setId(int id){		super.setId(id+150);}
}
