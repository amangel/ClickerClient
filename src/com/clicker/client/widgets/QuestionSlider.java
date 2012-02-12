package com.clicker.client.widgets;

import android.content.Context;
import android.widget.SeekBar;

public class QuestionSlider extends SeekBar implements QuestionWidgetInterface {

	private int min;
	private int max;
	
	public QuestionSlider(Context context, int min, int max){
		super(context);
		this.min = min;
		this.max = max;
		this.setMax(max-min);
	}

	@Override
	public String getWidgetValue() {
		return ""+(this.getProgress()+min);
	}

	@Override
	public void setWidgetValue(String s) {
		int i = Integer.parseInt(s);
		int curr = this.getProgress();
		int diff = i - curr - min;
		this.incrementProgressBy(diff);
	}

	@Override
	public boolean isToggleable() {		return false;}

	@Override
	public void toggleWidget() {		}

	@Override
	public void turnOff() {	}

	@Override
	public void turnOn() {		}

	@Override
	public void setText(String text) {	}

	@Override
	public void setId(int id){		super.setId(id+150);}
}
