package lib.morkim.mfw.app;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.TaskFactory;
import lib.morkim.mfw.task.TaskScheduler;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.Navigation;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.Viewable;

/**
 * Holds application configuration. You should create here your concrete
 * factories, repository, Model ... etc. Basically what you create here are the
 * parts that Morkim framework will use to create your application. When you
 * extends this class you should add it in the manifest file in
 * {@code android:name} property under {@code application} tag.
 */
public abstract class MorkimApp extends Application implements AppContext,
		RepoAccess {

	private Repository repo;

	private Analytics analytics;

	private Navigation navigation;

    private Map<UUID, Controller> controllers;
    private Map<UUID, Presenter> presenters;

	private Model model;
	private TaskScheduler taskScheduler;


    @Override
	public void onCreate() {
		super.onCreate();

		createFactories();

		repo = createRepo();
		if (repo == null) 
			throw new Error(String.format("createRepo() method in %s must return a non-null implementation", this.getClass()));

		analytics = createAnalytics();
		analytics.initialize();

        controllers = new HashMap<>();
        presenters = new HashMap<>();

		model = createModel();
		if (model == null) 
			throw new Error(String.format("createModel() method in %s must return a non-null implementation", this.getClass()));

		taskScheduler = new TaskScheduler(createScheduledTaskFactory());

		navigation = createNavigation();

		try {
			model.load();
		} catch (GatewayRetrieveException e) {
			e.printStackTrace();
		}
	}

    public Controller acquireController(Viewable viewable) {

        Controller controller = controllers.get(viewable.getInstanceId());
        controller = (controller == null) ? viewable.createController() : controller;
		controllers.put(viewable.getInstanceId(), controller);

		return controller;
    }

    public Presenter acquirePresenter(Viewable viewable) {

        Presenter presenter = presenters.get(viewable.getInstanceId());
        presenter = (presenter == null) ? viewable.createPresenter() : presenter;
		presenters.put(viewable.getInstanceId(), presenter);

		return presenter;
    }

    public void destroyController(Viewable viewable) {
        controllers.remove(viewable.getInstanceId());
    }

    public void destroyPresenter(Viewable viewable) {
        presenters.remove(viewable.getInstanceId());
    }

	/**
	 * Create specific application factories. The factories created here are not
	 * used by Morkim framework. You can just you this for creating the
	 * factories at an early initialization state of the application.
	 */
	protected abstract void createFactories();

	/**
	 * Create the data repository for your application. This repository has the
	 * knowledge of all needed data {@link Gateway} to be created on request
	 * from the application. See more details in the {@link MorkimRepository}
	 * interface on how to create data gateways.
	 * 
	 * @return Repository interface
	 */
	protected abstract MorkimRepository createRepo();

	/**
	 * Create the application data model container {@link Model} should contain
	 * all your business entity hierarchy.
	 * 
	 * @return Application data model
	 */
	protected abstract Model createModel();

	/**
	 * Create scheduled tasks factory. The tasks are background threads that run
	 * at specific intervals and you can schedule/unschedule them at any point
	 * in your application life time. You can also register and unregister to
	 * updates from those tasks. For more info take a look at
	 * {@link ScheduledTask} abstract class.
	 * 
	 * @return Scheduled tasks factory
	 */
	protected abstract TaskFactory createScheduledTaskFactory();

	public Navigation acquireNavigation() {
		return navigation;
	}

	@Override
	public Repository getRepos() {
		return repo;
	}

	@Override
	public void setRepos(Repository repos) {
		this.repo = repos;
	}

	@Override
	public Analytics getAnalytics() {
		return analytics;
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	protected Analytics createAnalytics() {
		return new DummyAnalytics(this);
	}

	protected abstract Navigation createNavigation();

	@Override
	public MorkimApp getContext() {
		return this;
	}

	public String getCountryCode() {
		return getResources().getConfiguration().locale.getCountry();
	}
}
