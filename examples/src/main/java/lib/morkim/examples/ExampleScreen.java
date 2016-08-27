package lib.morkim.examples;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import lib.morkim.mfw.ui.Screen;
import lib.morkim.mfw.ui.lists.ItemClickSupport;

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
        return new ExamplePresenter();
    }

    @Override
    public void onBindViews() {

        Button textButton = (Button) findViewById(R.id.btn_example_button);
        textButton.setOnClickListener(controller.buttonClickListener);

        Button taskButton = (Button) findViewById(R.id.btn_example_button_task);
        taskButton.setOnClickListener(controller.taskButtonClickListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_example_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(controller.listItemClickListener);
    }
}
