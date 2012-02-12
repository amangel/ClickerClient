package com.clicker.client.widgets;

import android.content.Context;
import android.widget.ToggleButton;

public class QuestionButton extends ToggleButton implements QuestionWidgetInterface {

	public QuestionButton(Context context){
		super(context);
	}
	
	@Override
	public String getWidgetValue() {
		if (this.isChecked()){
			return this.getText().toString();
		} else {
			return " ";
		}
	}
	@Override
	public void setWidgetValue(String s) {
		if (s.equals("0")){
			this.setChecked(false);
		} else if (s.equals("1")){
			this.setChecked(true);
		}
	}
	@Override
	public boolean isToggleable() {
		return true;
	}
	@Override
	public void toggleWidget() {
		this.toggle();
	}

	@Override
	public void turnOff() {
		this.setChecked(false);
	}

	@Override
	public void setText(String str){
		super.setText(str);
		this.setTextOn(str);
		this.setTextOff(str);
	}
	
	@Override
	public void turnOn() {
		this.setChecked(true);
	}

	@Override
	public void setId(int id){
		super.setId(id+150);
	}
	
}
