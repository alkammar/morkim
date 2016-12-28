package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.app.UseCaseManager;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;

public interface UseCaseDependencies<A extends MorkimApp, M extends Model> {

	A getContext();

	M getModel();

	Repository getRepository();

	UseCaseManager getUseCaseManager();
}
