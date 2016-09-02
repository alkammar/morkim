package lib.morkim.examples;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Screen;
import lib.morkim.mfw.ui.lists.ItemClickSupport;

public class ExampleScreen extends Screen<ExampleApp, Model, ExampleViewableActions, ExampleController, ExamplePresenter> {

    private TextView textView;
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.screen_example;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = (TextView) findViewById(R.id.tv_example_text_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_example_list);
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

    @Override
    public ExampleViewableActions createActions() {

        adapter = new ExampleAdapter(presenter);

        return new ExampleViewableActions(adapter) {

            @Override
            public void initializeList() {

                if (recyclerView.getAdapter() == null)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void updateTextView() {
                textView.setText(presenter.getTextViewText());
            }
        };
    }

    @Override
    public ExampleController createController() {
        return new ExampleController(getMorkimContext());
    }

    @Override
    public ExamplePresenter createPresenter() {
        return new ExamplePresenter();
    }
}
