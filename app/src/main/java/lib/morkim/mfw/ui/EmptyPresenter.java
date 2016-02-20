package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public class EmptyPresenter extends Presenter<EmptyController, Model, MorkimApp<Model, ?>> {

	public EmptyPresenter(Viewable viewable) {
		super(viewable);

		EmptyController sdf = getController();
	}

}
