package lib.morkim.mfw.app;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.UpdateListener;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskResult;

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

	private M model;

	private Map<Class<? extends MorkimTask>, List<MorkimTaskListener<? extends TaskResult>>> useCasesListeners;

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

		model = createModel();
		if (model == null) 
			throw new Error(String.format("createModel() method in %s must return a non-null implementation", this.getClass()));

	    useCasesListeners = new HashMap<>();

		taskScheduler = new TaskScheduler(createScheduledTaskFactory());

		try {
			model.load();
		} catch (GatewayRetrieveException e) {
			e.printStackTrace();
		}
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

	    p presenter = createPresenter(viewable);

	    viewable.onAttachPresenter(presenter);
	    viewable.onAttachController(controller);
	    controller.onAttachViewable(viewable);
	    presenter.onAttachController(controller);

	    viewable.onBindViews();
	    controller.onInitializeViews();

		controllers.put(viewable.getInstanceId(), controller);

		return controller;
    }

	// TODO need to handle case where superclass is not generic
	private <c extends Controller> c constructController(Viewable<?, c, ?> viewable) {

		Class<c> controllerClass = null;
		Class<?> viewableClass = viewable.getClass();
		Type genericSuperclass;

		do {
			genericSuperclass = viewableClass.getGenericSuperclass();

			if (genericSuperclass instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

				for (Type type : actualTypeArguments) {

					Class<?> cls;

					cls = type instanceof ParameterizedType ?
							(Class<?>) ((ParameterizedType) type).getRawType() :
							(Class<?>) type;

					if (Controller.class.isAssignableFrom(cls)) {
						controllerClass = (Class<c>) type;
						break;
					}
				}
			}

			viewableClass = viewableClass.getSuperclass();

		} while (controllerClass == null);

		try {
//			Constructor<c> constructor = (Constructor<c>) controllerClass.getDeclaredConstructors()[0];
//			constructor.setAccessible(true);
			return controllerClass.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
//		catch (InvocationTargetException e) {
//			Log.e("MorkimApp", e.getCause().getMessage());
//			for (StackTraceElement element : e.getCause().getStackTrace())
//				Log.e("MorkimApp", "\tat " + element);
//			e.printStackTrace();
//		}

		return null;
	}

	public <p extends Presenter> p createPresenter(Viewable<?, ?, p> viewable) {

		Type genericSuperclass = viewable.getClass().getGenericSuperclass();

		Class<p> presenterClass;

		if (genericSuperclass instanceof ParameterizedType)
			presenterClass = (Class<p>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[2];
		else
			presenterClass = (Class<p>) EmptyPresenter.class;

		try {
			Constructor<p> constructor = presenterClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Log.e("MorkimApp", "constructPresenter " + e.getCause().getMessage());
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

	public void subscribeToUseCase(Class<? extends MorkimTask> task, MorkimTaskListener<? extends TaskResult> listener) {

		synchronized (this) {
			List<MorkimTaskListener<? extends TaskResult>> useCaseListeners = useCasesListeners.get(task);

			if (useCaseListeners == null) {
				useCaseListeners = new ArrayList<>();
				useCasesListeners.put(task, useCaseListeners);
			}

			useCaseListeners.add(listener);
		}
	}

	public void unsubscribeFromUseCase(Class<? extends MorkimTask> task, MorkimTaskListener listener) {

		synchronized (this) {
			List<MorkimTaskListener<? extends TaskResult>> useCaseListeners = useCasesListeners.get(task);

			if (useCaseListeners != null) {
				useCaseListeners.remove(listener);

				if (useCaseListeners.isEmpty())
					useCasesListeners.remove(task);
			}
		}
	}

	public List<MorkimTaskListener<? extends TaskResult>> getUseCaseSubscriptions(Class<? extends MorkimTask> aClass) {
		synchronized (this) {
			List<MorkimTaskListener<? extends TaskResult>> useCasekListeners = useCasesListeners.get(aClass);
			return useCasekListeners != null ? useCasekListeners : new ArrayList<MorkimTaskListener<? extends TaskResult>>();
		}
	}
}
