package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.UUID;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;

/**
 * Created by Kammar on 2/16/2016.
 */
public abstract class MorkimFragment extends Fragment implements Viewable {

    private Controller controller;
    private Presenter presenter;
    private UUID id;

    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (savedInstanceState == null) ? UUID.randomUUID() : (UUID) savedInstanceState.get(VIEWABLE_ID);

        presenter = ((MorkimApp) getMorkimContext()).acquirePresenter(this);
        controller = ((MorkimApp) getMorkimContext()).acquireController(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        controller.addObserver(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        controller.deleteObserver(this);
    }

    @Override
    public AppContext getMorkimContext() {
        return (AppContext) getActivity().getApplication();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public Screen getScreen() {
        return (Screen) getActivity();
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showShortMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void keepScreenOn(boolean keepOn) {
        ((Screen) getActivity()).keepScreenOn(keepOn);
    }

    @Override
    public void finish() {

    }

    @Override
    public UUID getInstanceId() {
        return id;
    }
}
