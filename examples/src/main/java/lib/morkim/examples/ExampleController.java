package lib.morkim.examples;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.ScreenController;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.ui.lists.ItemClickSupport;
import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleController extends ScreenController<ExamplePresenter, Model, MorkimApp<Model, ?>>
        implements ListAdapter.UpdateListener<ExampleAdapter.ExampleItemHolder> {

    private int count;

    private List<ExampleEntity> entities;

    public ExampleController(Viewable<MorkimApp<Model, ?>, ?, ExamplePresenter> viewable) {
        super(viewable);

//        try {
//            ExampleEntity entity = getAppContext().getRepos().get(ExampleEntity.class).retrieve();
//            List<ExampleEntity> entities = getAppContext().getRepos().get(ExampleEntity.class).retrieveAll();
//            entity.save(getAppContext().getRepos().get(ExampleEntity.class));
//        } catch (GatewayRetrieveException e) {
//            e.printStackTrace();
//        } catch (GatewayPersistException e) {
//            e.printStackTrace();
//        }

        entities = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ExampleEntity entity = new ExampleEntity();
            entity.index = i;
            entities.add(entity);
        }
    }

    @Override
    public void onBindViews() {

        bindUpdateListener(R.id.tv_example_text_view, textViewUpdateListener);
        bindUpdateListener(R.id.rv_example_list, listUpdateListener);

        Button textButton = (Button) activity.findViewById(R.id.btn_example_button);
        textButton.setOnClickListener(buttonClickListener);

        Button taskButton = (Button) activity.findViewById(R.id.btn_example_button_task);
        taskButton.setOnClickListener(taskButtonClickListener);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_example_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(listItemClickListener);
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        notifyView(R.id.tv_example_text_view);
        notifyView(R.id.rv_example_list);
    }

    @Override
    public int onUpdateListSize() {
        return presenter.getListSize();
    }

    @Override
    public void onUpdateListItem(ExampleAdapter.ExampleItemHolder holder, int position) {
        holder.textView.setText(presenter.getItemNumber(position));
    }

    private ViewUpdateListener textViewUpdateListener = new ViewUpdateListener<TextView>() {
        @Override
        public void onUpdate(TextView view) {
            view.setText(presenter.getTextViewText());
        }
    };

    private ViewUpdateListener listUpdateListener = new ViewUpdateListener<RecyclerView>() {
        @Override
        public void onUpdate(RecyclerView view) {

            ListAdapter adapter = presenter.getAdapter();

            if (view.getAdapter() == null)
                view.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            notifyView(R.id.tv_example_text_view);
        }
    };

    private View.OnClickListener taskButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {

            new ExampleTask(getAppContext(), new MorkimTaskListener<ExampleResult>() {
                @Override
                public void onTaskStart(MorkimTask useCase) {}

                @Override
                public void onTaskUpdate(ExampleResult result) {
                    count = result.count;
                    notifyView(R.id.tv_example_text_view);
                }

                @Override
                public void onTaskComplete(ExampleResult result) {

                    for (ExampleEntity entity : entities) {
                        entity.index += 100;
                        presenter.getAdapter().notifyItemChanged(entities.indexOf(entity));
                    }
                }

                @Override
                public void onTaskCancel() {}

            }).execute(TaskRequest.EMPTY);
        }
    };

    private ItemClickSupport.OnItemClickListener listItemClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            entities.get(position).index++;
            presenter.getAdapter().notifyItemChanged(position);
        }
    };

    public int getCount() {
        return count;
    }

    public List<ExampleEntity> getEntities() {
        return entities;
    }
}
