package lib.morkim.examples;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.ScreenController;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleController extends ScreenController<ExamplePresenter, Model, MorkimApp<Model, ?>> {

    private int count;

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
    }

    @Override
    public void onBindViews() {

        Button textButton = (Button) activity.findViewById(R.id.btn_example_button);
        textButton.setOnClickListener(buttonClickListener);

        Button taskButton = (Button) activity.findViewById(R.id.btn_example_button_task);
        taskButton.setOnClickListener(taskButtonClickListener);

        View textView = activity.findViewById(R.id.tv_example_text_view);
        viewUpdaterArray.put(R.id.tv_example_text_view, new ViewUpdater(textView, textViewUpdateListener));
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        notifyView(R.id.tv_example_text_view);
    }

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
                public void onTaskComplete(ExampleResult result) {}

                @Override
                public void onTaskCancel() {}

            }).execute(TaskRequest.EMPTY);
        }
    };

    private ViewUpdateListener textViewUpdateListener = new ViewUpdateListener<TextView>() {
        @Override
        public void onUpdate(TextView view) {
            view.setText(presenter.getTextViewText());
        }
    };

    public int getCount() {
        return count;
    }
}
