package com.clicker.client;

import android.app.Activity;

public abstract class AbstractQuestionActivity extends Activity {
	protected abstract void processValues();
	protected abstract void setView();
	protected abstract void setHandler();
	protected abstract void createViews();
	public abstract void removeView(int index);
	public abstract void closeActivity();
	public abstract void resendLast();
}