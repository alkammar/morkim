package lib.morkim.mfw.app;

import java.util.HashMap;

import lib.morkim.mfw.adapters.Controller;
import lib.morkim.mfw.adapters.EmptyController;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.TaskFactory;
import lib.morkim.mfw.task.TaskScheduler;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.Navigation;
import lib.morkim.mfw.usecase.UseCaseAbstractFactory;
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
	private UseCaseAbstractFactory useCaseAbstractFactory;

	private Analytics analytics;

	private Navigation navigation;
	protected HashMap<String, Controller> controllers = new HashMap<String, Controller>();

	private Model model;
	private TaskScheduler taskScheduler;

	@Override
	public void onCreate() {
		super.onCreate();

		createFactories();

		repo = createRepo();

		analytics = createAnalytics();
		analytics.initialize();

		model = createModel();

		taskScheduler = new TaskScheduler(createScheduledTaskFactory());

		navigation = createNavigation();

		try {
			model.load();
		} catch (GatewayRetrieveException e) {
			e.printStackTrace();
		}

		useCaseAbstractFactory = createUseCaseAbstractFactory();
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
	 * from the application. See more details in the {@link Repository}
	 * interface on how to create data gateways.
	 * 
	 * @return Repository interface
	 */
	protected abstract Repository createRepo();

	/**
	 * Create the application data model container {@link Model} should contain
	 * all your business entity hierarchy.
	 * 
	 * @return
	 */
	protected abstract Model createModel();

	/**
	 * Create scheduled tasks factory. The tasks are background threads that run
	 * at specific intervals and you can schedule/unschedule them at any point
	 * in your application life time. You can also register and unregister to
	 * updates from those tasks. For more info take a look at {@link ScheduledTask} abstract class.
	 * 
	 * @return Scheduled tasks factory
	 */
	protected abstract TaskFactory createScheduledTaskFactory();

	/**
	 * Acquire the controller with coupled to this screen if it already exists,
	 * otherwise it will create this controller if the screen is mapped to an
	 * existing controller class
	 * 
	 * @param view
	 *            Screen coupled with the desired controller
	 * @return The mapped controller or null of no mapping exists
	 */
	public Controller acquireController(MView view) {

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

	protected abstract Analytics createAnalytics();

	protected abstract Navigation createNavigation();

	protected abstract Controller createController(MView view);

	protected abstract UseCaseAbstractFactory createUseCaseAbstractFactory();

	public UseCaseAbstractFactory getUseCaseAbstractFactory() {
		return useCaseAbstractFactory;
	}

	public void destroyController(Controller controller) {
		controllers.remove(controller);
		controller.destroy();
	}

	@Override
	public MorkimApp getContext() {
		return this;
	}

	public String getCountryCode() {
		return getResources().getConfiguration().locale.getCountry();
	}
}
