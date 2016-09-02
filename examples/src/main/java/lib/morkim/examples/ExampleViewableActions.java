package lib.morkim.examples;

import lib.morkim.mfw.ui.ViewableActions;
import lib.morkim.mfw.ui.lists.ListAdapter;

public class ExampleViewableActions extends ViewableActions {

	private ListAdapter adapter = new EmptyListAdapter();

	public ExampleViewableActions(ListAdapter adapter) {
		if (adapter != null)
		this.adapter = adapter;
	}

	ListAdapter getAdapter() {
		return adapter;
	}

	void initializeTextView() {

	}

	void doSomeAction() {

	}

	public void initializeList() {

	}

	public void updateTextView() {

	}
}
