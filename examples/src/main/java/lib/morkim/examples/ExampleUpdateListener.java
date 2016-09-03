package lib.morkim.examples;

import lib.morkim.mfw.ui.UpdateListener;

public interface ExampleUpdateListener extends UpdateListener {

	void initializeTextView();

	void updateTextView();

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position);
}
