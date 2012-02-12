package com.clicker.client.widgets;

import android.graphics.drawable.Drawable;

public interface QuestionWidgetInterface {
	public String 	getWidgetValue();
	public void 	setWidgetValue(String s);
	public boolean	isToggleable();
	public void 	toggleWidget();
	public void 	turnOff();
	public void 	turnOn();
	public void 	setId(int id);
	public void 	setVisibility(int vis);
	public void 	setText(String text);
	public Drawable	getBackground();
	public void 	invalidate();
}
