package lib.morkim.mfw.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public abstract class MorkimFragment<V extends UpdateListener, C extends Controller, P extends Presenter>
        extends Fragment
        implements Viewable<V, C, P> {

    private UUID id;

    protected C controller;
    protected P presenter;

    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (savedInstanceState == null) ? generateUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

        UiComponentHelper.createUiComponents(this, getActivity().getApplication());
    }

    protected UUID generateUUID() {
        return UUID.randomUUID();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller.onAttachParent(this);
    }

    @Override
    public void onBindViews() {

    }

    @Override
    public void onAttachController(C controller) {
        this.controller = controller;
    }

    @Override
    public void onAttachPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public Bundle getBundledData() {
        return getArguments();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void runOnUi(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    @Override
    public V getUpdateListener() {
        return (V) this;
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
    public void onDestroy() {
        super.onDestroy();

        if (getActivity().isFinishing())
            ((MorkimApp) getActivity().getApplication()).destroyController(this);
    }

    @Override
    public UUID getInstanceId() {
        return id;
    }

    @Override
    public void registerPermissionListener(String s, onPermissionResultListener onPermissionResultListener) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(VIEWABLE_ID, id.toString());
    }

    @Override
    public <T> T getParentListener() {
        return UiComponentHelper.getParentAsListener(this);
    }

    @Override
    public <T> T getChildListener() {
        return (T) controller;
    }

    @Override
    public void finish() {
        getFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }
}
