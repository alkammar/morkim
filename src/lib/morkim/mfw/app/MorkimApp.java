package lib.morkim.mfw.app;

import java.util.HashMap;

import lib.morkim.mfw.adapters.Controller;
import lib.morkim.mfw.adapters.EmptyController;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.TaskFactory;
import lib.morkim.mfw.task.TaskScheduler;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.Navigation;
import lib.morkim.mfw.usecase.UseCaseAbstractFactory;
import android.app.Application;

public abstract class MorkimApp extends Application implements AppContext, RepoAccess {

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

	protected abstract void createFactories();
	protected abstract Repository createRepo();

	protected abstract Model createModel();
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
			if (controller == null) controller = new EmptyController(getContext(), view);
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
