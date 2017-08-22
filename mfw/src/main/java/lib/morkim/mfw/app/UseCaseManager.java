package lib.morkim.mfw.app;

import java.util.List;

import lib.morkim.mfw.usecase.TaskRequest;
import lib.morkim.mfw.usecase.TaskResult;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseDependencies;
import lib.morkim.mfw.usecase.UseCaseListener;

public interface UseCaseManager {

	void subscribeToUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener<? extends TaskResult> listener);

	void unsubscribeFromUseCase(Class<? extends UseCase>[] taskClasses, UseCaseListener listener);

	List<UseCaseListener<? extends TaskResult>> getUseCaseSubscriptions(Class<? extends UseCase> aClass);

	void addToUndoStack(UseCase useCase, TaskRequest request);

	<u extends UseCase> UseCase popUseCaseStack(UseCaseDependencies dependencies);

	void clearUndoStack();

	void addToStickyUseCases(UseCase useCase);

	TaskResult getStickyResult(Class<? extends UseCase> cls);

	<Req extends TaskRequest, Res extends TaskResult> void runOnUi(Runnable runnable);

	void removeSticky(Class<? extends UseCase> useCaseClass);

	void clearSticky();
}
