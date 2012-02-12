package com.clicker.client;

public class ClickerConstants {
	protected static final String OPEN_MC_QUESTION = "OpenMC";
	protected static final String CLOSE_QUESTION = "CloseQuestion";
	protected static final String REQUEST_ID = "RequestID";
	protected static final String OPEN_JEO_QUESTION = "OpenJEO";
	protected static final String REMOVE_OPTION = "Remove";
	protected static final String CHECK_CONNECTION = "CheckConnect";
	protected static final String RESEND_ANSWER = "ResendAnswer";
	protected static final String DUPLICATE_ID = "DuplicateID";
	protected static final String OPEN_CLICKPAD = "OpenClickPad";
	
	protected static final String OPEN_COMMAND = "Open";
	protected static final String CLOSE_COMMAND = "Close";
	protected static final String SYSTEM_COMMAND = "System";
	protected static final String CLICKPAD_OPTION = "ClickPad";
	protected static final String INVALID_RESPONSE = "InvalidResponse";
	protected static final String INVALID_ADMIN = "InvalidAdmin";
	protected static final String INVALID_INFORMATION = "InvalidInformation";
	protected static final String ADD_WIDGET = "ADD";
	protected static final String SET_GROUP = "GROUP";
	protected static final String SET_COLOR = "COLOR";
	protected static final String STILL_CONNECTED_REQUEST = "AreYouStillThere";
	protected static final String STILL_CONNECTED_RESPONSE = "YesImHere";
	

	// codes for messages to message handler to create dialogs
	protected static final int BASIC_DIALOG_OK = 100;
	protected static final int QUESTION_DIALOG_OK = 200;
	protected static final int JEOPARDY_DIALOG_OK = 300;
	protected static final int CLICKPAD_DIALOG_OK = 400;
	protected static final int SLIDER_DIALOG_OK = 500;
	protected static final int CLOSE_DIALOG = 900;
	protected static final int RESEND_RESPONSE = 925;
	protected static final int REMOVE_MC_OPTION = 950;
	protected static final int REQUEST_NAME = 990;
	protected static final int CONNECT_CHECK = 1000;
	protected static final int DUP_ID = 1010;
	protected static final int INVALID = 1050;
	protected static final int REDRAW = 1100;
	protected static final int CLOSE_PUSH = 1150;
	
	protected static final int CLOSE_CONNECTION = 2000;
}
