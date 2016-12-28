package lib.morkim.mfw.app;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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
import lib.morkim.mfw.ui.EmptyController;
import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.UpdateListener;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.util.GenericsUtils;

/**
 * Holds application configuration. You should create here your Repository, Model ... etc.
 * Basically what you create here are the parts that Morkim framework will use to create
 * your application.
 * When you extend this class you should add it in the manifest file in
 * {@code android:name} property under {@code application} tag.
 */
public abstract class MorkimApp<M extends Model, R extends MorkimRepository> extends Application {

	private Repository repo;

	private Analytics analytics;

    private Map<UUID, Controller> controllers;

	private UseCaseManager useCaseManager;

	private M model;

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

		useCaseManager = createUseCaseManager();

		model = createModel();
		if (model == null) 
			throw new Error(String.format("createModel() method in %s must return a non-null implementation", this.getClass()));

		taskScheduler = new TaskScheduler(createScheduledTaskFactory());

		try {
			model.load();
		} catch (GatewayRetrieveException e) {
			e.printStackTrace();
		}
	}

	private UseCaseManager createUseCaseManager() {
		return new UseCaseManagerImpl();
	}

	/**
	 * Gets the Controller associated with a given Viewable.
	 * Will create the controller if it is not already created.
	 * @param viewable Viewable to fetch Controller for
	 * @return Controller associated with passed viewable
	 */
    public <u extends UpdateListener, c extends Controller, p extends Presenter> c createUiComponents(Viewable<u, c, p> viewable) {

        c controller = (c) controllers.get(viewable.getInstanceId());
        controller = (controller == null) ? constructController(viewable) : controller;
	    controller.onAttachApp(this);

	    p presenter = constructPresenter(viewable);

	    viewable.onAttachPresenter(presenter);
	    viewable.onAttachController(controller);
	    controller.onAttachViewable(viewable);
	    presenter.onAttachController(controller);

	    viewable.onBindViews();
	    controller.onInitializeViews();

		controllers.put(viewable.getInstanceId(), controller);

		return controller;
    }

	private <c extends Controller> c constructController(Viewable<?, c, ?> viewable) {

		c controller = constructComponent(viewable, 1);//Controller.class);
		return controller != null ? controller : (c) new EmptyController();
	}

	private <p extends Presenter> p constructPresenter(Viewable<?, ?, p> viewable) {

		p presenter = constructComponent(viewable, 2);//Presenter.class);
		return presenter != null ? presenter : (p) new EmptyPresenter();
	}

	@Nullable
	private <comp> comp constructComponent(Viewable viewable, int index) {//Class<?> component) {

		Class<comp> concreteClass;

		Class<?> viewableClass = viewable.getClass();
		Type genericSuperclass;

		Type [] resolvedTypes = GenericsUtils.resolveActualTypeArgs((Class<? extends Viewable>) viewableClass, Viewable.class);

		TypeVariable<?>[] typeParameters = viewable.getClass().getTypeParameters();

		concreteClass = GenericsUtils.getRawType(resolvedTypes[index]);//GenericsUtils.getDefaultComponentClass(component, typeParameters);

//		while (concreteClass == null && viewableClass != null) {
//			genericSuperclass = viewableClass.getGenericSuperclass();
//
//			if (genericSuperclass instanceof ParameterizedType) {
//				Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
//
//				concreteClass = GenericsUtils.findComponentConcreteClass(component, actualTypeArguments);
//			}
//
//			viewableClass = viewableClass.getSuperclass();
//		}

		try {
			if (concreteClass != null) {
				Constructor<comp> constructor = concreteClass.getDeclaredConstructor();
				constructor.setAccessible(true);
				return constructor.newInstance();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Log.e("MorkimApp", e.getCause().getMessage());
			for (StackTraceElement element : e.getCause().getStackTrace())
				Log.e("MorkimApp", "\tat " + element);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Destroys the {@link Controller} associated with the passed Viewable
	 * @param viewable Viewable to fetch Controller for
	 */
    public void destroyController(Viewable viewable) {
	    Controller controller = controllers.get(viewable.getInstanceId());
	    if (controller != null) {
		    controller.onDestroy();
		    controllers.remove(viewable.getInstanceId());
	    }
    }

	/**
	 * Create specific application factories. The factories created here are not
	 * used by Morkim framework. You can just this for creating the
	 * factories at an early initialization state of the application.
	 */
	protected abstract void createFactories();

	/**
	 * Creates the data repository for your application. This repository has the
	 * knowledge of all needed data {@link Gateway}s to be created on request
	 * from the application. See more details in the {@link MorkimRepository}
	 * interface on how to create data gateways.
	 * 
	 * @return Repository interface
	 */
	protected abstract R createRepo();

	/**
	 * Creates the application data model container. {@link Model} should contain
	 * all your business entity hierarchy that needs to be alive regardless
	 * of Android components states or entities that might be needed among
	 * more than one Android component
	 * 
	 * @return Application data model
	 */
	protected abstract M createModel();

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

	public Repository getRepo() {
		return repo;
	}

	public void setRepos(Repository repos) {
		this.repo = repos;
	}

	public Analytics getAnalytics() {
		return analytics;
	}

	public M getModel() {
		return model;
	}

	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	protected Analytics createAnalytics() {
		return new DummyAnalytics(this);
	}

	public MorkimApp getContext() {
		return this;
	}

	public <P extends Presenter> String getCountryCode() {
		return getResources().getConfiguration().locale.getCountry();
	}

	public UseCaseManager getUseCaseManager() {
		return useCaseManager;
	}
}
