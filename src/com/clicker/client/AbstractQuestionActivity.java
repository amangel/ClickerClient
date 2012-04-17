package com.clicker.client;

import android.app.Activity;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractQuestionActivity.
 */
public abstract class AbstractQuestionActivity extends Activity {
    
    /**
     * Process values.
     */
    protected abstract void processValues();
    
    /**
     * Sets the view.
     */
    protected abstract void setView();
    
    /**
     * Sets the handler.
     */
    protected abstract void setHandler();
    
    /**
     * Creates the views.
     */
    protected abstract void createViews();
    
    /**
     * Removes the view.
     *
     * @param index the index
     */
    public abstract void removeView(int index);
    
    /**
     * Close activity.
     */
    public abstract void closeActivity();
    
    /**
     * Resend last.
     */
    public abstract void resendLast();
}