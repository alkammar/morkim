package lib.morkim.mfw.app;

import java.util.HashMap;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.TaskFactory;
import lib.morkim.mfw.task.TaskScheduler;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.EmptyController;
import lib.morkim.mfw.ui.Navigation;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.usecase.UseCaseFactory;
import android.app.Application;

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
	protected HashMap<String, Controller> controllers = new HashMap<String, Controller>();

	private Model model;
	private TaskScheduler taskScheduler;

	private UseCaseFactory useCaseFactory;

	@Override
	public void onCreate() {
		super.onCreate();

		createFactories();

		repo = createRepo();
		if (repo == null) 
			throw new Error(String.format("createRepo() method in %s must return a non-null implementation", this.getClass()));

		analytics = createAnalytics();
		analytics.initialize();

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

		useCaseFactory = createUseCaseFactory();
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

	/**
	 * Acquire the controller with coupled to this screen if it already exists,
	 * otherwise it will create this controller if the screen is mapped to an
	 * existing controller class
	 * 
	 * @param viewable
	 *            Screen coupled with the desired controller
	 * @return The mapped controller or null of no mapping exists
	 */
	public Controller acquireController(Viewable view) {

		String name = view.getClass().getName();
		Controller controller = controllers.get(name);

		if (controller == null) {

			controller = createController(view);
			if (controller == null)
				controller = new EmptyController(getContext(), view);
			controllers.put(name, controller);
		}

		return controller;
	}
	
	public abstract Presenter createPresenter(Viewable view);

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

	protected abstract Controller createController(Viewable view);

	/**
	 * Create a factory of {@link UseCaseFactory} factories. At some point you might
	 * need to group related use case which need their own factory. Hence this
	 * is a factory of factories is useful.
	 * 
	 * @return Use case abstract factory
	 */
	protected abstract UseCaseFactory createUseCaseFactory();

	@Override
	public UseCaseFactory getUseCaseFactory() {
		return useCaseFactory;
	}

	public void destroyController(Controller controller) {
		controllers.remove(controller);
	}

	@Override
	public MorkimApp getContext() {
		return this;
	}

	public String getCountryCode() {
		return getResources().getConfiguration().locale.getCountry();
	}
}
