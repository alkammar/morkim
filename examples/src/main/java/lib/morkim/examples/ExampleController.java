package lib.morkim.examples;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.ScreenController;
import lib.morkim.mfw.ui.lists.ItemClickSupport;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleController extends ScreenController<MorkimApp<Model, ?>, Model, ExampleUpdateActions> {

    private int count;

    private List<ExampleEntity> entities;

    public ExampleController(MorkimApp<Model, ?> morkimApp) {
        super(morkimApp);

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
    protected void onInitViews() {
        super.onInitViews();

        getUpdateActions().initializeTextView();

        getUpdateActions().initializeList();
        getUpdateActions().initializeListData();
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            getUpdateActions().updateTextView();
        }
    };

    View.OnClickListener taskButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {

            new ExampleTask(getAppContext(), new MorkimTaskListener<ExampleResult>() {
                @Override
                public void onTaskStart(MorkimTask useCase) {}

                @Override
                public void onTaskUpdate(ExampleResult result) {
                    count = result.count;
                    getUpdateActions().updateTextView();
                }

                @Override
                public void onTaskComplete(ExampleResult result) {

                    for (ExampleEntity entity : entities) {
                        entity.index += 100;
                        getUpdateActions().updateListItem(entities.indexOf(entity));
                    }
                }

                @Override
                public void onTaskCancel() {}

            }).execute(TaskRequest.EMPTY);
        }
    };

    ItemClickSupport.OnItemClickListener listItemClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            entities.get(position).index++;
            getUpdateActions().updateListItem(position);
        }
    };

    public int getCount() {
        return count;
    }

    public List<ExampleEntity> getEntities() {
        return entities;
    }

    @Override
    protected Class<ExampleUpdateActions> getViewableUpdateClass() {
        return ExampleUpdateActions.class;
    }
}
