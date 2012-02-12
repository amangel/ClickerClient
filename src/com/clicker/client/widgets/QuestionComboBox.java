package com.clicker.client.widgets;

import android.content.Context;
import android.widget.Spinner;

public class QuestionComboBox extends Spinner implements QuestionWidgetInterface{

	public QuestionComboBox(Context context){
		super(context);
	}
	
	@Override
	public String getWidgetValue() {
		return (String)this.getSelectedItem();
	}

	@Override
	public void setWidgetValue(String s) {
		this.setSelection(Integer.parseInt(s));
	}

	@Override
	public boolean isToggleable() {
		return false;
	}

	@Override
	public void toggleWidget() {
	}

	@Override
	public void turnOff() {
	}

	@Override
	public void turnOn() {
	}

	@Override
	public void setId(int id) {
		super.setId(id+150);
	}

	@Override
	public void setVisibility(int vis) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setText(String text) {
	}

}
