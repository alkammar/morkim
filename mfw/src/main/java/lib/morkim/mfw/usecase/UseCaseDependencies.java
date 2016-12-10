package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public interface UseCaseDependencies<A extends MorkimApp, M extends Model> {

	A getContext();

	M getModel();
}
