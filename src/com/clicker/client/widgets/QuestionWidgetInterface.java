package com.clicker.client.widgets;

import android.graphics.drawable.Drawable;

// TODO: Auto-generated Javadoc
/**
 * The Interface QuestionWidgetInterface.
 */
public interface QuestionWidgetInterface {
    
    /**
     * Gets the widget value.
     *
     * @return the widget value
     */
    public String 	getWidgetValue();
    
    /**
     * Sets the widget value.
     *
     * @param s the new widget value
     */
    public void 	setWidgetValue(String s);
    
    /**
     * Checks if is toggleable.
     *
     * @return true, if is toggleable
     */
    public boolean	isToggleable();
    
    /**
     * Toggle widget.
     */
    public void 	toggleWidget();
    
    /**
     * Turn off.
     */
    public void 	turnOff();
    
    /**
     * Turn on.
     */
    public void 	turnOn();
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void 	setId(int id);
    
    /**
     * Sets the visibility.
     *
     * @param vis the new visibility
     */
    public void 	setVisibility(int vis);
    
    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void 	setText(String text);
    
    /**
     * Gets the background.
     *
     * @return the background
     */
    public Drawable	getBackground();
    
    /**
     * Invalidate.
     */
    public void 	invalidate();
}
