package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

/**
 * Created by Kammar on 2/16/2016.
 */
public abstract class MorkimFragment<C extends Controller, P extends Presenter> extends PreferenceFragment implements Viewable<C, P> {

    private C controller;
    private P presenter;
    private UUID id;

    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

        presenter = (P) getMorkimContext().acquirePresenter(this);
        controller = (C) getMorkimContext().acquireController(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(VIEWABLE_ID, id.toString());
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
    public MorkimApp getMorkimContext() {
        return (MorkimApp) getActivity().getApplication();
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
    public C getController() {
        return controller;
    }

    @Override
    public P getPresenter() {
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
