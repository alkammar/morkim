package lib.morkim.mfw.ui;

import java.util.Map;
import java.util.Observer;

import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.content.Intent;

public interface Viewable extends ViewModelListener {
	
	/**
	 * Positive button dialog event
	 */
	public static final int EVENT_DIALOG_POSITIVE = 20;
	/**
	 * Negative button dialog event
	 */
	public static final int EVENT_DIALOG_NEGATIVE = 21;

	/**
	 * Show dialog command
	 */
	public static final int COMMAND_SHOW_DIALOG = 100;
	
	public void startActivity(Intent intent);
	public void finish();
	public void bindUiElements();
	
	public void assignListeners(Map<String, ViewListener> listeners);
	public void configureUiElements();
	public void setNavigation(Navigation navigation);
	
	public String getStringResource(int resource);
	
	public UseCaseStateListener getUseCaseListener();
	public Observer getTaskListener();
}