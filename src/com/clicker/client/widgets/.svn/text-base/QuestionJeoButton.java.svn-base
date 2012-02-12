package com.clicker.client.widgets;

import com.clicker.client.ClickerClientApp;
import com.clicker.client.CustomizableQuestion;

import android.content.Context;

public class QuestionJeoButton extends QuestionButton {
	
	private ClickerClientApp app;
	
	public QuestionJeoButton(Context context){
		super(context);
		CustomizableQuestion cq = (CustomizableQuestion)context;
		app = (ClickerClientApp)cq.getApplication();
	}
	
	@Override
	public String getWidgetValue() {
		return app.getUserName();
	}
}
