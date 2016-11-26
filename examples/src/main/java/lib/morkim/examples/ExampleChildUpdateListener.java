package lib.morkim.examples;

import lib.morkim.mfw.processor.PendingEventAnnotation;
import lib.morkim.mfw.ui.UpdateListener;

@PendingEventAnnotation
public interface ExampleChildUpdateListener extends UpdateListener {

	void initializeTextView();

	void updateTextView();

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position, String param);
}
