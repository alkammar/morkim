package lib.morkim.examples.screen;

import lib.morkim.mfw.ui.UpdateListener;

public interface ExampleBaseUpdateListener extends UpdateListener {

	void initializeTextView();

	void updateTextView();
}
