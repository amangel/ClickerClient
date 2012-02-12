package com.clicker.client.widgets;

import com.clicker.client.CustomizableQuestion;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionTextBox extends EditText implements QuestionWidgetInterface{
	private CustomizableQuestion cq;
	
	public QuestionTextBox(Context context){
		super(context);
		cq = (CustomizableQuestion)context;
	}

	@Override
	public String getWidgetValue() {
		String str = this.getText().toString();
		if (str.equals("")){
			str = " ";
		}
		str = str.replace("\n", " ");
		return str;
	}

	@Override
	public void setWidgetValue(String s) {
		this.setText(s, TextView.BufferType.EDITABLE);
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
	public void setText(String text) {
		setWidgetValue(text);
		
	}

	@Override
	public void setId(int id){
		super.setId(id+150);
	}
}
