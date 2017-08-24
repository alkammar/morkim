package lib.morkim.mfw.app;

import java.util.List;

import lib.morkim.mfw.usecase.UseCaseRequest;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseDependencies;
import lib.morkim.mfw.usecase.UseCaseListener;

public interface UseCaseManager {

	void subscribeToUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener<? extends UseCaseResult> listener);

	void unsubscribeFromUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener listener);

	List<UseCaseListener<? extends UseCaseResult>> getUseCaseSubscriptions(Class<? extends UseCase> aClass);

	void addToUndoStack(UseCase useCase, UseCaseRequest request);

	<u extends UseCase> UseCase popUseCaseStack(UseCaseDependencies dependencies);

	void clearUndoStack();

	void addToStickyUseCases(UseCase useCase);

	UseCaseResult getStickyResult(Class<? extends UseCase> cls);

	<Req extends UseCaseRequest, Res extends UseCaseResult> void runOnUi(Runnable runnable);

	void removeSticky(Class<? extends UseCase> useCaseClass);

	void clearSticky();
}
