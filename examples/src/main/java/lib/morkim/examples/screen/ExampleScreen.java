package lib.morkim.examples.screen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import lib.morkim.examples.R;
import lib.morkim.examples.screen.fragment.ExampleFragment;
import lib.morkim.mfw.ui.AppCompatScreen;
import lib.morkim.mfw.ui.lists.ItemClickSupport;

public class ExampleScreen extends AppCompatScreen<ExampleScreenController, ExamplePresenter>
        implements ExampleUpdateListener {

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

        adapter = new ExampleAdapter(controller, presenter);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, new ExampleFragment())
                .addToBackStack(null)
                .commit();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_non_generic, new ExampleNonGenericFragment())
                .addToBackStack(null)
                .commit();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_non_abstract_generic, new ExampleNonAbstractGenericFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAssignListeners() {

        Button textButton = (Button) findViewById(R.id.btn_example_button);
        textButton.setOnClickListener(controller.buttonClickListener);

        Button taskButton = (Button) findViewById(R.id.btn_example_button_task);
        taskButton.setOnClickListener(controller.taskButtonClickListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_example_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(controller.listItemClickListener);
    }

    @Override
    public void initializeTextView() {

    }

    @Override
    public void doSomeAction() {

    }

    @Override
    public void initializeList() {

        if (recyclerView.getAdapter() == null)
            recyclerView.setAdapter(adapter);
    }

    @Override
    public void initializeListData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateListItem(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void updateSomethingWhenViewableNotAvailable() {
        recyclerView.setBackgroundColor(0xffff0000);
    }

    @Override
    public void updateTextView() {
        textView.setText(presenter.getTextViewText());
    }

    @Override
    public ExampleUpdateListener getUpdateListener() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();

        controller.onScreenStopped();
    }
}
