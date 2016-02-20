package lib.morkim.examples;

import android.util.Log;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.Viewable;

/**
 * Created by Kammar on 2/19/2016.
 */
public class ExamplePresenter extends Presenter<ExampleController, Model, MorkimApp<Model, ?>> {

    public ExamplePresenter(Viewable viewable) {
        super(viewable);
    }

    public void doSomething() {
        Log.i("ExamplePresenter", "doing something");
    }

    public void doSomethingElse() {
        Log.i("ExamplePresenter", "doing something else");
    }
}