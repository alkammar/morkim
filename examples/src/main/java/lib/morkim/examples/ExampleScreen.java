package lib.morkim.examples;

import android.os.Bundle;

import java.util.Observable;

import lib.morkim.mfw.ui.Screen;

public class ExampleScreen extends Screen<ExampleController, ExamplePresenter> {

    @Override
    protected int layoutId() {
        return R.layout.screeen_example;
    }

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
