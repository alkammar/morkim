package lib.morkim.examples;

import lib.morkim.mfw.ui.UpdateActions;

public interface ExampleUpdateActions extends UpdateActions {

	void initializeTextView();

	void updateTextView();

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position);
}
