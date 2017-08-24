package lib.morkim.examples.screen;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.examples.ExampleBaseController;
import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.model.ExampleEntity;
import lib.morkim.examples.screen.fragment.ExampleParentListener;
import lib.morkim.examples.usecase.ExampleResult;
import lib.morkim.examples.usecase.ExampleTask;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.ui.lists.ItemClickSupport;
import lib.morkim.mfw.usecase.UseCaseListener;
import lib.morkim.mfw.usecase.OnTaskUpdateListener;
import lib.morkim.mfw.usecase.UseCaseCreator;
import lib.morkim.mfw.usecase.UseCaseSubscription;

class ExampleScreenController
        extends ExampleBaseController<ExampleUpdateListener>
        implements ExampleParentListener {

    private int count;

    private List<ExampleEntity> entities;

    @Override
    public void onAttachApp(ExampleApp morkimApp) {
        super.onAttachApp(morkimApp);

        try {
            ExampleEntity entity = new ExampleEntity();
            entity.save(morkimApp.getRepo());
            entity = morkimApp.getRepo().get(ExampleEntity.class).retrieve();
            List<ExampleEntity> entities = morkimApp.getRepo().get(ExampleEntity.class).retrieveAll();
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
    protected void onShowViewable() {
        super.onShowViewable();

        getLiveViewable().initializeTextView();

        getLiveViewable().initializeList();
        getLiveViewable().initializeListData();
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            getLiveViewable().updateTextView();
        }
    };

    View.OnClickListener taskButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            new UseCaseCreator<ExampleTask>()
                    .create(ExampleTask.class)
                    .with(new ExampleUseCaseDependenciesImpl(getAppContext()))
                    .execute();
        }
    };

    @UseCaseSubscription(ExampleTask.class)
    private UseCaseListener<ExampleResult> exampleTaskListener = new OnTaskUpdateListener<ExampleResult>() {

        @Override
        public void onTaskUpdate(ExampleResult result) {
            count = result.count;
            getLiveViewable().updateTextView();
        }

        @Override
        public void onTaskComplete(ExampleResult result) {

            for (ExampleEntity entity : entities) {
                entity.index += 100;
                getLiveViewable().updateListItem(entities.indexOf(entity));
            }
        }

        @Override
        public void onUndone(ExampleResult result) {

            for (ExampleEntity entity : entities) {
                entity.index -= 100;
                getLiveViewable().updateListItem(entities.indexOf(entity));
            }
        }

        @Override
        public boolean onTaskError(ExampleResult errorResult) {
            return false;
        }

        @Override
        public void onTaskAborted() {

        }
    };

    ItemClickSupport.OnItemClickListener listItemClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

            entities.get(position).index++;
            getLiveViewable().updateListItem(position);

            getAppContext().getUseCaseManager()
                    .popUseCaseStack(new ExampleUseCaseDependenciesImpl(getAppContext()))
                    .undo();
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
            getLiveViewable().updateListItem(entities.indexOf(entity));
        }
    }

    void onScreenStopped() {
        getLiveViewable().updateSomethingWhenViewableNotAvailable();
    }

}
