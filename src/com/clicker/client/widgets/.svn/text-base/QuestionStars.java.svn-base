package com.clicker.client.widgets;

import android.content.Context;
import android.widget.RatingBar;

public class QuestionStars extends RatingBar implements QuestionWidgetInterface  {

	public QuestionStars(Context context, String max, String starCount, String stepSize){
		super(context);
		setMax(Integer.parseInt(max));
		setNumStars(Integer.parseInt(starCount));
		setStepSize(Float.parseFloat(stepSize));
	}

	@Override
	public String getWidgetValue() {
		return ""+getRating();
	}

	@Override
	public void setId(int id){
		super.setId(id+150);
	}	
	
	@Override
	public void setWidgetValue(String s) {
		setRating(Float.parseFloat(s));
	}

	@Override
	public boolean isToggleable() {
		return false;
	}

	@Override
	public void toggleWidget() {
	//do nothing	
	}

	@Override
	public void turnOff() {
	//do nothing	
	}

	@Override
	public void turnOn() {
	//do nothing
	}

	@Override
	public void setText(String text) {
	//do nothing
	}
}
