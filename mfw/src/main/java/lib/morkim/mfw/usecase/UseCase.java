package lib.morkim.mfw.usecase;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.app.UseCaseManager;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.util.GenericsUtils;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class UseCase<A extends MorkimApp<M, ?>, M extends Model, Req extends UseCaseRequest, Res extends UseCaseResult> {

    @TaskDependency
    protected A appContext;
    @TaskDependency
    protected M model;
    @TaskDependency
    protected Repository repo;
    @TaskDependency
    protected UseCaseManager useCaseManager;

    protected boolean isUndoing;

    protected UseCaseListener<Res> listener = new UseCaseListener<Res>() {
        @Override
        public void onTaskStart(UseCase task) {
        }

        @Override
        public void onTaskUpdate(Res result) {
        }

        @Override
        public void onTaskComplete(Res result) {
        }

        @Override
        public void onUndone(Res result) {

        }

        @Override
        public void onTaskCancel() {
        }

        @Override
        public boolean onTaskError(Res errorResult) {
            return false;
        }

//		@Override
//		public boolean onTaskError(int error, String message) {
//			return false;
//		}

        @Override
        public void onTaskAborted() {

        }
    };

//	protected List<UseCaseListener<? extends UseCaseResult>> subscribedListeners;

    private Req request;
    private Res stickyResult;

    public UseCase(A morkimApp, UseCaseListener<Res> listener) {

        this.appContext = morkimApp;
        this.model = appContext.getModel();
        this.repo = appContext.getRepo();

        if (listener != null)
            this.listener = listener;
    }

    public UseCase() {

    }

    public void executeSticky() {

        UseCaseResult stickyResult = useCaseManager.getStickyResult(this.getClass());
        if (stickyResult != null) {
            if (!(stickyResult instanceof UseCasePendingResult)) {
                updateListenerStart();
                updateProgress((Res) stickyResult);
            }
        } else
            execute();
    }

    public void executeSticky(Req request) {

        UseCaseResult stickyResult = useCaseManager.getStickyResult(this.getClass());
        if (stickyResult != null) {
            if (!(stickyResult instanceof UseCasePendingResult)) {
                updateListenerStart();
                updateProgress((Res) stickyResult);
            }
        } else
            execute(request);
    }

    public void execute() {
        executeSync();
    }

    public void execute(Req request) {
        executeSync(request);
    }

    public void executeSync() {
        executeSync(null);
    }

    public void executeSync(Req request) {

        updateListenerStart();

        if (isSticky()) useCaseManager.addToStickyUseCases(this);

        setRequest(request);
        final Res result = onExecute(request);
    }

    private void updateListenersOnUiThread(final Res result) {
        useCaseManager.runOnUi(new Runnable() {
            @Override
            public void run() {
                updateListener(result);
            }
        });
    }

    public void undo() {
//		subscribedListeners = useCaseManager.getUseCaseSubscriptions(this.getClass());
        onUndo(getRequest());
    }

    public void undoSync() {
        undo();
    }

    public void cancel() {

    }

    protected void updateProgress(Res result) {
        updateListener(result);
    }

    protected abstract Res onExecute(Req request);

    protected abstract Res onUndo(Req request);

    protected void updateListener(Res result) {

        if (result != null) {
            if (result.completionPercent != 100) {
                updateListenerUpdate(result);
            } else if (!isUndoing) {
                updateListenerComplete(result);

                if (isSticky())
                    setStickyResult(result);

                if (canUndo())
                    useCaseManager.addToUndoStack(this, getRequest());
            } else {
                updateListenerUndo(result);

                isUndoing = false;
            }
        } else {
            if (!isUndoing)
                updateListenerComplete(null);
            else {
                updateListenerUndo(null);
                isUndoing = false;
            }
        }
    }

    private void updateListenerStart() {
        listener.onTaskStart(this);

        for (UseCaseListener subscribedListener : useCaseManager.getUseCaseSubscriptions(this.getClass()))
            if (!subscribedListener.equals(UseCase.this.listener))
                subscribedListener.onTaskStart(this);
    }

    private void updateListenerUpdate(Res result) {
        listener.onTaskUpdate(result);

        for (UseCaseListener subscribedListener : useCaseManager.getUseCaseSubscriptions(this.getClass()))
            if (!subscribedListener.equals(UseCase.this.listener))
                subscribedListener.onTaskUpdate(result);
    }

    private void updateListenerComplete(Res result) {
        listener.onTaskComplete(result);

        for (UseCaseListener subscribedListener : useCaseManager.getUseCaseSubscriptions(this.getClass()))
            if (!subscribedListener.equals(UseCase.this.listener))
                subscribedListener.onTaskComplete(result);
    }

    private void updateListenerUndo(Res result) {
        listener.onUndone(result);

        for (UseCaseListener subscribedListener : useCaseManager.getUseCaseSubscriptions(this.getClass()))
            if (!subscribedListener.equals(UseCase.this.listener))
                subscribedListener.onUndone(result);
    }

    protected void updateError(Res errorResult) {

        boolean isHandled = false;

        for (UseCaseListener subscribedListener : useCaseManager.getUseCaseSubscriptions(this.getClass()))
            if (!subscribedListener.equals(UseCase.this.listener)) {
                isHandled = subscribedListener.onTaskError(errorResult);
                if (isHandled) break;
            }

        if (!isHandled)
            listener.onTaskError(errorResult);

        useCaseManager.removeSticky(this.getClass());
    }

    protected void onPostExecute() throws GatewayPersistException {
    }

    public void setAppContext(A appContext) {
        this.appContext = appContext;
    }

    protected Req getRequest() {
        return request;
    }

    public UseCase<A, M, Req, Res> setRequest(Req request) {
        this.request = request;

        return this;
    }

    public UseCaseListener<Res> getListener() {
        return listener;
    }

    public UseCase<A, M, Req, Res> setListener(UseCaseListener<Res> listener) {
        this.listener = listener;

        return this;
    }

    protected boolean canUndo() {
        return false;
    }

    protected boolean isSticky() {
        return false;
    }

    public void setStickyResult(Res stickyResult) {
        this.stickyResult = stickyResult;
    }

    public Res getStickyResult() {
        return stickyResult;
    }

    protected Res createResult() {

        Type[] args = GenericsUtils.resolveActualTypeArgs(this.getClass(), UseCase.class);

        Class<?> concreteClass = GenericsUtils.getRawType(args[3]);
        Constructor constructor = null;
        try {
            constructor = concreteClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Res) constructor.newInstance();
        } catch (IllegalAccessException e) {
            throw new Error("Unable to access member in " + concreteClass.getSimpleName());
        } catch (InstantiationException e) {
            throw new Error("Unable to access default constructor " + concreteClass.getSimpleName());
        } catch (NoSuchMethodException e) {
            throw new Error("Unable to find default constructor " + concreteClass.getSimpleName() + "()");
        } catch (InvocationTargetException e) {
            String message = e.getCause().getMessage();

            for (StackTraceElement element : e.getCause().getStackTrace())
                message += "\n\tat " + element;

            throw new Error(message);
        }
    }
}
