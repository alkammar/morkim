package lib.morkim.examples.screen.fragment;

import lib.morkim.mfw.mvp.PendingViewableUpdate;
import lib.morkim.mfw.ui.UpdateListener;

@PendingViewableUpdate
public interface ExampleFragmentUpdateListener extends UpdateListener {

	void doFragmentAction();
}
