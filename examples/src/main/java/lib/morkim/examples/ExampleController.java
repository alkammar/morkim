package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.Viewable;

/**
 * Created by Kammar on 2/19/2016.
 */
public class ExampleController extends Controller<ExamplePresenter, Model, MorkimApp<Model, ?>> {

    public ExampleController(Viewable viewable) {
        super(viewable);

        getPresenter().doSomething();
    }
}
