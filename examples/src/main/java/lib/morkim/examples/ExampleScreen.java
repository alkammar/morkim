package lib.morkim.examples;

import lib.morkim.mfw.ui.Screen;

public class ExampleScreen extends Screen<ExampleController, ExamplePresenter> {

    @Override
    protected int layoutId() {
        return R.layout.screen_example;
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
