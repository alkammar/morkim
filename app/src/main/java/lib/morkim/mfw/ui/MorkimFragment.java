package lib.morkim.mfw.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class MorkimFragment<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener, C extends Controller, P extends Presenter>
        extends Fragment
        implements Viewable<A, M, V, C, P> {

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

        getMorkimContext().acquireController(this);
    }

    @Override
    public void attachController(C controller) {

        presenter = createPresenter();

        this.controller = controller;
        this.controller.attachViewable(this);
        this.presenter.setController(this.controller);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(VIEWABLE_ID, id.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        controller.bindViews();
    }

    @Override
    public void onStop() {
        super.onStop();

        controller.unbindViews();
    }

    @Override
    public A getMorkimContext() {
        //noinspection unchecked
        return (A) getActivity().getApplication();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public void keepScreenOn(boolean keepOn) {
        ((Screen) getActivity()).keepScreenOn(keepOn);
    }

    @Override
    public UUID getInstanceId() {
        return id;
    }
}
