package lib.morkim.examples;

import android.os.Bundle;

import java.util.Observable;

import lib.morkim.mfw.ui.Screen;

/**
 * Created by Kammar on 2/19/2016.
 */
public class ExampleScreen extends Screen<ExampleController, ExamplePresenter> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.doSomethingElse();
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    @Override
    public ExampleController createController() {
        return new ExampleController(this);
    }

    @Override
    public ExamplePresenter createPresenter() {
        return new ExamplePresenter(this);
    }
}
