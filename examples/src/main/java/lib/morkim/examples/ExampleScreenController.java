package lib.morkim.examples;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.ui.lists.ItemClickSupport;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;

class ExampleScreenController extends ExampleBaseController<ExampleUpdateListener> implements ExampleParentListener {

    private int count;

    private List<ExampleEntity> entities;

    ExampleScreenController(ExampleApp morkimApp) {
        super(morkimApp);

        try {
            ExampleEntity entity = new ExampleEntity();
            entity.save(getAppContext().getRepo());
            entity = getAppContext().getRepo().get(ExampleEntity.class).retrieve();
            List<ExampleEntity> entities = getAppContext().getRepo().get(ExampleEntity.class).retrieveAll();
        } catch (GatewayRetrieveException | GatewayPersistException e) {
            e.printStackTrace();
        }

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

        getUpdateListener().initializeTextView();

        getUpdateListener().initializeList();
        getUpdateListener().initializeListData();
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            getUpdateListener().updateTextView();
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
                    getUpdateListener().updateTextView();
                }

                @Override
                public void onTaskComplete(ExampleResult result) {

                    for (ExampleEntity entity : entities) {
                        entity.index += 100;
                        getUpdateListener().updateListItem(entities.indexOf(entity));
                    }
                }

                @Override
                public void onTaskCancel() {}

            }).execute();
        }
    };

    ItemClickSupport.OnItemClickListener listItemClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            entities.get(position).index++;
            getUpdateListener().updateListItem(position);
        }
    };

    int getCount() {
        return count;
    }

    List<ExampleEntity> getEntities() {
        return entities;
    }

    @Override
    public void onDoSomethingWhenButtonClicked() {

        for (ExampleEntity entity : entities) {
            entity.index -= 100;
            getUpdateListener().updateListItem(entities.indexOf(entity));
        }
    }
}
