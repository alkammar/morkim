package lib.morkim.examples.screen;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.app.UseCaseManager;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;


public class BaseExampleUseCaseDependenciesImpl<A extends MorkimApp<M, ?>, M extends Model> {

    protected A context;

    public BaseExampleUseCaseDependenciesImpl(A context) {
        this.context = context;
    }

    public A getContext() {
        return context;
    }

    public M getModel() {
        return context.getModel();
    }

    public Repository getRepository() {
        return context.getRepo();
    }

    public UseCaseManager getUseCaseManager() {
        return context.getUseCaseManager();
    }
}
